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
import java.io.File;
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

import javax.annotation.Nonnull;
import javax.swing.SwingWorker;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONObject;
import org.oclc.purl.dsdl.svrl.FailedAssert;
import org.oclc.purl.dsdl.svrl.SchematronOutputType;

import com.helger.schematron.ISchematronResource;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.xslt.SchematronResourceSCH;


/**
 * CBW Reconcile Worker
 * 
 * This class does the actual work of connecting to SNAC and requesting for reconciliation.
 * It also handles the result from the server (JSON) and parses the data into a CSV.
 * 
 * @author Robbie Hott
 *
 */
public class SNACJavaSchematronValidator extends SwingWorker<Void, Void> {

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
	public SNACJavaSchematronValidator (String from, String to, Boolean displayResult) {
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
			validate();
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
	private void validate() throws Exception {
		setProgress(0);
		setProgressText("Reading XML file");
		
		//InputStream schematronStream = getClass().getResourceAsStream("SNAC_EAC-CPF_ValidationProfile.sch");
		//File schematron = new File(SNACJavaSchematronValidator.class.getResource("SNAC_EAC-CPF_ValidationProfile.sch"));
		//File schematron = new File("schematron/SNAC_EAC-CPF_ValidationProfile.sch");
		File xmlFile = new File(fromFile);
		
//		final ISchematronResource aResPure = SchematronResourcePure.fromFile (schematron);
//		  if (!aResPure.isValidSchematron ()) {
//			  FileReader r = new FileReader(schematron);
//			  String text = new String(Files.readAllBytes(schematron.toPath()), StandardCharsets.UTF_8);
//			  System.err.println(text);
//		    progressText =  "Invalid Schematron!";
//		    setProgress(100);
//		    return;
//		  }
		  
		final ISchematronResource aResSCH = SchematronResourceSCH.fromClassPath("SNAC_EAC-CPF_ValidationProfile.sch");
		//final ISchematronResource aResSCH = SchematronResourceSCH.     .fromFile(schematron);		  
		if (!aResSCH.isValidSchematron ()) {
			  //FileReader r = new FileReader(schematron);
			  //String text = new String(Files.readAllBytes(schematron.toPath()), StandardCharsets.UTF_8);
			  //System.err.println(text);
		    progressText =  "Invalid Schematron!";
		    setProgress(100);
		    return;
		  }
		boolean result = aResSCH.getSchematronValidity(new StreamSource(xmlFile)).isValid ();
		
		String validationErrors = "";
		SchematronOutputType sot = aResSCH.applySchematronValidationToSVRL(new StreamSource(xmlFile));
		List<Object> failedAsserts = sot.getActivePatternAndFiredRuleAndFailedAssert();
		for (Object object : failedAsserts) {
		    if (object instanceof FailedAssert) {
		        FailedAssert failedAssert = (FailedAssert) object;
		        validationErrors += failedAssert.getText() + "\r\n";
		        //System.out.println(failedAssert.getTest());
		    }
		}
		

//		FileWriter writer = null;
//		if (toFile != null)
//			writer = new FileWriter(toFile);
//		
//		if (displayResult)
//			SNACJavaParserConstellationWindow.showConstellation("");
//
//		// Close the CSV Writer
//		if (writer != null)
//			writer.close();


		// Update the progress to 100%
		if (result)
			progressText = "Validated Successfully";
		else {
			SNACInfoWindow.showWindow("Validation Errors", validationErrors);
			progressText = "Invalid XML File";
		}
		setProgress(100);


	}
}
