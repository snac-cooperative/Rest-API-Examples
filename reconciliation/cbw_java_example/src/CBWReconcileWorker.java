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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import org.json.JSONObject;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * CBW Reconcile Worker
 * 
 * This class does the actual work of connecting to SNAC and requesting for reconciliation.
 * It also handles the result from the server (JSON) and parses the data into a CSV.
 * 
 * @author Robbie Hott
 *
 */
public class CBWReconcileWorker extends SwingWorker<Void, Void> {

	/**
	 * Filenames to use
	 */
	private String fromFile;
	private String toFile;

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
	public CBWReconcileWorker (String from, String to) {
		fromFile = from;
		toFile = to;
		progress = 0.0;
		progressText = "";
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
		// Use a pre-packaged reader to read the given CSV file
		CSVReader reader = new CSVReader(new FileReader(fromFile));
		List<String[]> toReconcile = reader.readAll();

		// Number of lines in the CSV file (minus the header)
		int reconcileCount = toReconcile.size() - 1;

		// Use a pre-packaged writer to write out the CSV file
		CSVWriter writer = new CSVWriter(new FileWriter(toFile));

		// Write out header of the CSV
		String[] headers = {
				"CBW Name",
				"CBW ID",
				"Snac Name",
				"Snac ARK",
				"Overall Reconciliation Score",
				"Elastic Full Name Score",
				"Elastic Name-Only Score",
				"Elastic75 Score",
				"Original Length Score",
				"Original Length Difference Score",
				"Entity Type Filter Score",
				"SNAC Degree Score"
		};
		writer.writeNext(headers);

		// Step through the input data lines
		for (int i = 1; i < toReconcile.size(); i++) {
			// Calculate a "percent done" maxing out at 95%
			int percentage = (i * 95) / reconcileCount;

			// Pull the current line of the CSV as array
			String[] data = toReconcile.get(i);

			// grab the name components from the CSV file and create a snac-like name heading
			String nameOnly = data[4].trim() + ", " + data[2].trim() + " " + data[3].trim();
			nameOnly = nameOnly.trim();
			if (nameOnly.endsWith(",")) {
				nameOnly = nameOnly.substring(0, nameOnly.length()-1);
			}

			// Create the given name
			String name = nameOnly;

			// If the input line has a 12th column (dates), then use a regex to grab 4-digit years
			// and add them to the name
			if (data.length >= 13) {
				String pattern = "[0-9][0-9][0-9][0-9]";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(data[12]);

				String date = "";
				while (m.find()) {
					date += data[12].substring(m.start(), m.end()) + "-";
				}
				if (date.length() > 0) {
					date = date.substring(0, date.length()-1);
					name = nameOnly + ", " + date;
				}
			}

			// Update the progress for this run of the reconciliation
			setProgress(percentage);
			setProgressText(name);

			// Create the JSON query string for the SNAC RestAPI
			String query = "{"+ 
					"\"command\" : \"reconcile\"," +
					"\"constellation\" : { " +
						"\"dataType\" : \"Constellation\"," +
						"\"entityType\" : {" +
							"\"term\" : \"person\"" +
						"}," +
						"\"nameEntries\" : [" +
							"{" +
								"\"dataType\" : \"NameEntry\"," +
								"\"original\" : \""+ name +"\"," +
								"\"preferenceScore\" : 1" +
							"}" +
						"]" +
					"}" +
				"}";

			// Perform connection to SNAC
			HttpURLConnection httpcon = (HttpURLConnection) ((new URL("http://snac-web.iath.virginia.edu:81/").openConnection()));
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestMethod("PUT");
			httpcon.connect();

			// Write the query to the RestAPI
			byte[] outputBytes = query.getBytes("UTF-8");
			OutputStream os = httpcon.getOutputStream();
			os.write(outputBytes);
			os.close();

			// Read the response from the RestAPI
			InputStream in = new BufferedInputStream(httpcon.getInputStream());
			String resultStr = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
			JSONObject resultObj = new JSONObject(resultStr);
			in.close();

			// Close the connection
			httpcon.disconnect();

			// If reconciliation succeeded, then process the results
			if (resultObj.has("reconciliation")) {
				for (int j = 0; j < resultObj.getJSONArray("reconciliation").length(); j++) {

					JSONObject result = (JSONObject) resultObj.getJSONArray("reconciliation").get(j);
					// only grab the first 6 results
					if (j > 5) break;

					if (!result.has("vector"))
						continue;
					JSONObject vector = result.getJSONObject("vector");

					// Create the result data to add to the CSV output file
					String[] output = {
							name,
							toReconcile.get(i)[0],
							((JSONObject) result.getJSONObject("identity").getJSONArray("nameEntries").get(0)).getString("original"),
							result.getJSONObject("identity").getString("ark"),
							String.format("%.2f", result.getDouble("strength")),
							vector.has("ElasticOriginalNameEntry") ? JSONObject.doubleToString(vector.getDouble("ElasticOriginalNameEntry")) : "0",
							vector.has("ElasticNameOnly") ? JSONObject.doubleToString(vector.getDouble("ElasticNameOnly")) : "0",
							vector.has("ElasticSeventyFive") ? JSONObject.doubleToString(vector.getDouble("ElasticSeventyFive")) : "0",
							vector.has("OriginalLength") ? JSONObject.doubleToString(vector.getDouble("OriginalLength")) : "0",
							vector.has("OriginalLengthDifference") ? JSONObject.doubleToString(vector.getDouble("OriginalLengthDifference")) : "0",
							vector.has("EntityTypeFilter") ? JSONObject.doubleToString(vector.getDouble("EntityTypeFilter")) : "0",
							vector.has("MultiStage:ElasticNameOnly:SNACDegree") ? JSONObject.doubleToString(vector.getDouble("MultiStage:ElasticNameOnly:SNACDegree")) : "0"
					};

					// Write the line to the CSV file
					writer.writeNext(output);
				}
			}
		}

		// Close the CSV Writer
		writer.close();

		// Close the CSV Reader
		reader.close();

		// Update the progress to 100%
		progressText = "DONE!";
		setProgress(100);

	}
}
