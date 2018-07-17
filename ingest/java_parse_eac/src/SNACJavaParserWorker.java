/**
 * SNAC Reconciliation Example (CBW Java Example)
 *
 * For the full license, see the LICENSE file in the repository root
 *
 * @author Robbie Hott
 * @license http://opensource.org/licenses/BSD-3-Clause BSD 3-Clause
 * @copyright 2017 the Rector and Visitors of the University of Virginia, and
 *            the Regents of the University of California
 */

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * CBW Reconcile Worker
 * 
 * This class does the actual work of connecting to SNAC and requesting for reconciliation.
 * It also handles the result from the server (JSON) and parses the data into a CSV.
 * 
 * @author Robbie Hott
 *
 */
public class SNACJavaParserWorker extends SwingWorker<Void, Void> {

	/**
	 * Filenames to use
	 */
	private String fromFile;
	private String toFile;
	
	private Boolean displayResult;

	/**
	 * Progress of the reconciliation
	 */
	double progress;

	/**
	 * Where the application is currently looking
	 */
	String progressText;

	/**
	 * Constructor
	 * 
	 * Create a new worker using the given from and to filenames.
	 * 
	 * @param from CSV file to read from
	 * @param to CSV file to write to
	 */
	public SNACJavaParserWorker (String from, String to, Boolean displayResult) {
		fromFile = from;
		toFile = to;
		progress = 0.0;
		progressText = "";
		this.displayResult = displayResult;
	}

	/**
	 * Background worker
	 * 
	 * SwingWorker calls this method when it spawns the new worker thread.  This method
	 * then calls the actual reconcile method to perform the reconcilation.
	 */
	public Void doInBackground() {

		try {
			reconcile();
		} catch (Exception e) {
			// Silently ignoring errors
		}
		return null;

	}

	/**
	 * Set the progress text
	 * 
	 * @param text String to use for the progress text
	 */
	private void setProgressText(String text) {
		progressText = text;
	}

	/**
	 * Get progress text
	 * 
	 * Returns the current progress status (what individual in the CSV file the system is currently looking at)
	 * 
	 * @return The progress text
	 */
	public String getProgressText() {
		return progressText;
	}

	/**
	 * Main Reconcile Method
	 * 
	 * This method performs the heart of the client-side reconciliation process.
	 * 
	 * @throws Exception
	 */
	private void reconcile() throws Exception {
		setProgress(0);
		setProgressText("Reading XML file");
		
		String xmlContents = new String(Files.readAllBytes(Paths.get(fromFile)), StandardCharsets.UTF_8);
		

		setProgress(10);
		setProgressText("Encoding XML file");
		
		String base64Encoded = new String(Base64.getEncoder().encode(xmlContents.getBytes()));
		
		setProgress(20);
		setProgressText("Querying SNAC-Alpha");



		// Create the JSON query string for the SNAC RestAPI
		String query = "{"+ 
				"\"command\" : \"parse_eac\"," +
				"\"file\" : { " +
					"\"content\" : \"" + base64Encoded + "\"," +
					"\"mime-type\" : \"text/xml\"" +
				"}" +
			"}";

		//System.err.println(query);
		// Perform connection to SNAC
		HttpURLConnection httpcon = (HttpURLConnection) ((new URL("http://snac-dev.iath.virginia.edu/alpha/rest/").openConnection()));
		httpcon.setDoOutput(true);
		httpcon.setRequestProperty("Content-Type", "application/json");
		httpcon.setRequestMethod("PUT");
		httpcon.connect();
		
		
		setProgress(50);
		setProgressText("Reading response from SNAC-Alpha");

		// Write the query to the RestAPI
		byte[] outputBytes = query.getBytes("UTF-8");
		OutputStream os = httpcon.getOutputStream();
		os.write(outputBytes);
		os.close();
		
		setProgress(60);


		// Read the response from the RestAPI
		String resultStr = null;
		try {
			InputStream in = new BufferedInputStream(httpcon.getInputStream());
			resultStr = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
			in.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			setProgress(99);
			setProgressText("SNAC-Alpha could not parse input: Invalid XML File");
			return;
		}
		
		setProgress(65);
		JSONObject resultObj = null;
		try {
			resultObj = new JSONObject(resultStr);
		} catch (JSONException je) {
			setProgress(99);
			setProgressText("SNAC-Alpha returned with an error");
			System.err.println(resultStr);
			return;
		}

		setProgress(70);
		// Close the connection
		httpcon.disconnect();
		
		
		setProgress(75);
		setProgressText("Parsing response from SNAC");

		// If reconciliation succeeded, then process the results

		// Use a pre-packaged writer to write out the CSV file
		FileWriter writer = null;
		if (toFile != null)
			writer = new FileWriter(toFile);
		
		if (resultObj.has("constellation") && resultObj.has("result")) {		
			setProgress(85);
			setProgressText("Writing JSON file from SNAC");
			
			if (writer != null)
				writer.write(resultObj.getJSONObject("constellation").toString(4));

			if (displayResult)
				SNACInfoWindow.showWindow("Parsed Constellation" ,resultObj.getJSONObject("constellation").toString(4));
		}
		
		// Close the CSV Writer
		if (writer != null)
			writer.close();


		// Update the progress to 100%
		if (resultObj.has("result") && resultObj.getString("result").equals("success"))
			progressText = "Complete";
		else
			progressText = "Complete -- with errors";
		setProgress(100);

		String errors = "";
		if (resultObj.has("unparsed")) {
			for (int j = 0; j < resultObj.getJSONArray("unparsed").length(); j++) {
				String line = resultObj.getJSONArray("unparsed").getString(j).trim();
				if (!line.isEmpty())
					errors += line + "\r\n";
			}
			
			
		}
		
		
		if (!errors.isEmpty())
			SNACInfoWindow.showWindow("Parse Errors", errors);

	}
}
