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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * CBW Reconciler UI
 * 
 * This is the GUI interface class that prompts the user to choose CSV files to reconcile against SNAC.
 * 
 * @author Robbie Hott
 *
 */
public class SNACJavaParser extends javax.swing.JFrame {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -8115653654144568030L;

	/**
	 * GUI Variables
	 */
	private JPanel title;
	private JLabel titleLabel;
	private JPanel bodyPanel;
	private JPanel parserPanel;
	private JSeparator jSeparator2;
	private JButton parseWithSNAC;
	private JSeparator jSeparator3;
	private JProgressBar parseProgressBar;
	private JSeparator jSeparator4;
	private JLabel parseProgressLabel;			
	private JSeparator jSeparator1;
	private JLabel fromXMLFileLabel;
	private JLabel fromXMLFileLocationLabel;
	private JButton fromXMLFileButton;
	private JLabel toJSONFileLabel;
	private JLabel toJSONFileLocationLabel;
	private JButton toJSONFileButton;

	/**
	 * Reconciliation File Variables
	 */
	private String fromXMLFile;
	private String toJSONFile;


	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			// Silently ignoring errors
		}
	}

	/**
	 * Main Method
	 * 
	 * Creates an instance of this GUI and starts the example
	 * 
	 * @param args Command-line arguments
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		//Mac Niceness
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "SNAC EAC-CPF Utility");

		Handler fh = new FileHandler("log2.txt");
	    Logger.getLogger("").addHandler(fh);
	    Logger.getLogger("").setLevel(Level.INFO);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SNACJavaParser inst = new SNACJavaParser();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	/**
	 * Constructor
	 * 
	 * Calls JFrame's constructor and then initializes and displays the GUI
	 */
	public SNACJavaParser() {
		super();
		initGUI();
	}

	/**
	 * Initialize GUI
	 * 
	 * Builds the JFrame to display to the user.
	 */
	private void initGUI() {
		try {

			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			{
				title = new JPanel();
				getContentPane().add(title, BorderLayout.NORTH);
				title.setSize(700, 100);
				{
					titleLabel = new JLabel("<html><body style='text-align: center;'><p style='font-size: 25px;'>SNAC EAC-CPF Utility</p><p style='color: #B40404'>Pre-Alpha Test Version</p></body></html>", SwingConstants.CENTER);
					title.add(titleLabel);
					titleLabel.setPreferredSize(new java.awt.Dimension(700, 100));
				}
			}
			//continue building GUI
			bodyPanel = new JPanel();
			FlowLayout bodyPanelLayout = new FlowLayout();
			getContentPane().add(bodyPanel, BorderLayout.CENTER);
			bodyPanel.setLayout(bodyPanelLayout);
			{
				parserPanel = new JPanel();
				bodyPanel.add(parserPanel);
				parserPanel.setSize(700, 200);
				parserPanel.setPreferredSize(new java.awt.Dimension(700, 200));

				// lookup buttons
				{
					fromXMLFileLabel = new JLabel();
					parserPanel.add(fromXMLFileLabel);
					fromXMLFileLabel.setText("EAC-CPF XML File: ");
				}
				{
					fromXMLFileLocationLabel = new JLabel();
					parserPanel.add(fromXMLFileLocationLabel);
					fromXMLFileLocationLabel.setText("<choose>");
				}
				{
					fromXMLFileButton = new JButton();
					parserPanel.add(fromXMLFileButton);
					fromXMLFileButton.setText("Browse");
					fromXMLFileButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							// Pop up a file chooser for the user to pick a CSV file
							JFileChooser chooser2 = new JFileChooser();
							chooser2.setDialogType(JFileChooser.OPEN_DIALOG);
							chooser2.setDialogTitle("Choose an XML File.");
							chooser2.setFileFilter(new FileNameExtensionFilter("EAC-CPF XML Files", "xml"));
							int returnVal = chooser2.showOpenDialog(null);
							if(returnVal == JFileChooser.APPROVE_OPTION) {
								fromXMLFile = chooser2.getSelectedFile().getAbsolutePath();
								fromXMLFileLocationLabel.setText(fromXMLFile);
							}

						}
					});
				}
				{
					jSeparator1 = new JSeparator();
					parserPanel.add(jSeparator1);
					jSeparator1.setPreferredSize(new java.awt.Dimension(700, 6));
				}
				{
					toJSONFileLabel = new JLabel();
					parserPanel.add(toJSONFileLabel);
					toJSONFileLabel.setText("Destination JSON File: ");
				}
				{
					toJSONFileLocationLabel = new JLabel();
					parserPanel.add(toJSONFileLocationLabel);
					toJSONFileLocationLabel.setText("<optional>");
				}
				{
					toJSONFileButton = new JButton();
					parserPanel.add(toJSONFileButton);
					toJSONFileButton.setText("Browse");
					toJSONFileButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							// Pop up a file chooser for the user to find a directory and
							// enter a filename for the destination CSV.
							JFileChooser chooser2 = new JFileChooser();
							chooser2.setDialogTitle("Choose a Destination JSON File (optional).");
							chooser2.setDialogType(JFileChooser.SAVE_DIALOG);
							chooser2.setFileFilter(new FileNameExtensionFilter("JSON Constellation Files","json"));
							int returnVal = chooser2.showSaveDialog(null);
							if(returnVal == JFileChooser.APPROVE_OPTION) {
								toJSONFile = chooser2.getSelectedFile().getAbsolutePath();
								toJSONFileLocationLabel.setText(toJSONFile);
							}

						}
					});
				}

				{
					jSeparator2 = new JSeparator();
					parserPanel.add(jSeparator2);
					jSeparator2.setPreferredSize(new java.awt.Dimension(700, 6));
				}
				{
					parseWithSNAC = new JButton();
					parserPanel.add(parseWithSNAC);
					parseWithSNAC.setText("Schematron Validate");
					parseWithSNAC.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							// If a to and from file are set, then stand up a reconcile worker to
							// do the work and reconcile against snac.  This GUI will be updated on
							// the progress of the worker.
							if (fromXMLFile != null) {
								parseProgressBar.setValue(0);
								parseProgressLabel.setText("Starting...");
								try {
									// do the reconcile loop in the background
									final SNACJavaSchematronValidator rw = new SNACJavaSchematronValidator(fromXMLFile, toJSONFile, true);        
									rw.addPropertyChangeListener(new PropertyChangeListener() {

										@Override
										public void propertyChange(
												PropertyChangeEvent evt) {
											if ("progress" == evt.getPropertyName()) {
												int progress = (Integer) evt.getNewValue();
												parseProgressBar.setValue(progress);
												parseProgressLabel.setText(rw.getProgressText());
											} 
										}
									});
									rw.execute();
								} catch (Exception e) {
									// Silently ignoring errors
								}
							}
						}
					});
				}
				// Reconcile with SNAC button
				{
					parseWithSNAC = new JButton();
					parserPanel.add(parseWithSNAC);
					parseWithSNAC.setText("Parse with SNAC");
					parseWithSNAC.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							// If a to and from file are set, then stand up a reconcile worker to
							// do the work and reconcile against snac.  This GUI will be updated on
							// the progress of the worker.
							if (fromXMLFile != null) {
								try {
									parseProgressBar.setValue(0);
									parseProgressLabel.setText("Starting...");
									// do the reconcile loop in the background
									final SNACJavaParserWorker rw = new SNACJavaParserWorker(fromXMLFile, toJSONFile, true);        
									rw.addPropertyChangeListener(new PropertyChangeListener() {

										@Override
										public void propertyChange(
												PropertyChangeEvent evt) {
											if ("progress" == evt.getPropertyName()) {
												int progress = (Integer) evt.getNewValue();
												parseProgressBar.setValue(progress);
												parseProgressLabel.setText(rw.getProgressText());
											} 
										}
									});
									rw.execute();
								} catch (Exception e) {
									// Silently ignoring errors
								}
							}
						}
					});
				}
				{
					jSeparator3 = new JSeparator();
					parserPanel.add(jSeparator3);
					jSeparator3.setPreferredSize(new java.awt.Dimension(700, 6));
				}
				{
					parseProgressBar = new JProgressBar(0, 100);
					parseProgressBar.setValue(0);
					parseProgressBar.setStringPainted(true);
					parserPanel.add(parseProgressBar);
					parseProgressBar.setPreferredSize(new java.awt.Dimension(600, 20));
				}
				{
					jSeparator4 = new JSeparator();
					parserPanel.add(jSeparator4);
					jSeparator4.setPreferredSize(new java.awt.Dimension(700, 0));
				}
				{
					parseProgressLabel = new JLabel();
					parserPanel.add(parseProgressLabel);
				}


			}
			this.setSize(700, 400);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("SNAC EAC-CPF Utility");
		} catch (Exception e) {
			// Silently ignoring errors
		}
	}




}
