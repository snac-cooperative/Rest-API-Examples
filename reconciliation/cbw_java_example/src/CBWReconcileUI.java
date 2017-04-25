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
public class CBWReconcileUI extends javax.swing.JFrame {

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
	private JPanel reconcilePanel;
	private JSeparator jSeparator2;
	private JButton reconcileSNAC;
	private JSeparator jSeparator3;
	private JProgressBar reconcileProgressBar;
	private JSeparator jSeparator4;
	private JLabel reconcileProgressLabel;			
	private JSeparator jSeparator1;
	private JLabel fromCSVFileLabel;
	private JLabel fromCSVFileLocationLabel;
	private JButton fromCSVFileButton;
	private JLabel toCSVFileLabel;
	private JLabel toCSVFileLocationLabel;
	private JButton toCSVFileButton;

	/**
	 * Reconciliation File Variables
	 */
	private String fromCSVFile;
	private String toCSVFile;


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
	 */
	public static void main(String[] args) {
		//Mac Niceness
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "CBW-SNAC Reconciler");


		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CBWReconcileUI inst = new CBWReconcileUI();
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
	public CBWReconcileUI() {
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
					titleLabel = new JLabel("<html><body style='text-align: center; font-size: 25px;'>CBW-SNAC Reconciler</body></html>", SwingConstants.CENTER);
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
				reconcilePanel = new JPanel();
				bodyPanel.add(reconcilePanel);
				reconcilePanel.setSize(700, 200);
				reconcilePanel.setPreferredSize(new java.awt.Dimension(700, 200));

				// lookup buttons
				{
					fromCSVFileLabel = new JLabel();
					reconcilePanel.add(fromCSVFileLabel);
					fromCSVFileLabel.setText("CBW CSV File: ");
				}
				{
					fromCSVFileLocationLabel = new JLabel();
					reconcilePanel.add(fromCSVFileLocationLabel);
					fromCSVFileLocationLabel.setText("<choose>");
				}
				{
					fromCSVFileButton = new JButton();
					reconcilePanel.add(fromCSVFileButton);
					fromCSVFileButton.setText("Browse");
					fromCSVFileButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							// Pop up a file chooser for the user to pick a CSV file
							JFileChooser chooser2 = new JFileChooser();
							chooser2.setDialogType(JFileChooser.OPEN_DIALOG);
							chooser2.setDialogTitle("Choose a CSV File.");
							chooser2.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
							int returnVal = chooser2.showOpenDialog(null);
							if(returnVal == JFileChooser.APPROVE_OPTION) {
								fromCSVFile = chooser2.getSelectedFile().getAbsolutePath();
								fromCSVFileLocationLabel.setText(fromCSVFile);
							}

						}
					});
				}
				{
					jSeparator1 = new JSeparator();
					reconcilePanel.add(jSeparator1);
					jSeparator1.setPreferredSize(new java.awt.Dimension(700, 6));
				}
				{
					toCSVFileLabel = new JLabel();
					reconcilePanel.add(toCSVFileLabel);
					toCSVFileLabel.setText("Reconciled CSV File: ");
				}
				{
					toCSVFileLocationLabel = new JLabel();
					reconcilePanel.add(toCSVFileLocationLabel);
					toCSVFileLocationLabel.setText("<choose>");
				}
				{
					toCSVFileButton = new JButton();
					reconcilePanel.add(toCSVFileButton);
					toCSVFileButton.setText("Browse");
					toCSVFileButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							// Pop up a file chooser for the user to find a directory and
							// enter a filename for the destination CSV.
							JFileChooser chooser2 = new JFileChooser();
							chooser2.setDialogTitle("Choose a Destination CSV File.");
							chooser2.setDialogType(JFileChooser.SAVE_DIALOG);
							chooser2.setFileFilter(new FileNameExtensionFilter("CSV Files","csv"));
							int returnVal = chooser2.showSaveDialog(null);
							if(returnVal == JFileChooser.APPROVE_OPTION) {
								toCSVFile = chooser2.getSelectedFile().getAbsolutePath();
								toCSVFileLocationLabel.setText(toCSVFile);
							}

						}
					});
				}

				{
					jSeparator2 = new JSeparator();
					reconcilePanel.add(jSeparator2);
					jSeparator2.setPreferredSize(new java.awt.Dimension(700, 6));
				}
				// Reconcile with SNAC button
				{
					reconcileSNAC = new JButton();
					reconcilePanel.add(reconcileSNAC);
					reconcileSNAC.setText("Reconcile Against SNAC");
					reconcileSNAC.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							// If a to and from file are set, then stand up a reconcile worker to
							// do the work and reconcile against snac.  This GUI will be updated on
							// the progress of the worker.
							if (fromCSVFile != null && toCSVFile != null) {
								try {
									// do the reconcile loop in the background
									final CBWReconcileWorker rw = new CBWReconcileWorker(fromCSVFile, toCSVFile);        
									rw.addPropertyChangeListener(new PropertyChangeListener() {

										@Override
										public void propertyChange(
												PropertyChangeEvent evt) {
											if ("progress" == evt.getPropertyName()) {
												int progress = (Integer) evt.getNewValue();
												reconcileProgressBar.setValue(progress);
												reconcileProgressLabel.setText(rw.getProgressText());
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
					reconcilePanel.add(jSeparator3);
					jSeparator3.setPreferredSize(new java.awt.Dimension(700, 6));
				}
				{
					reconcileProgressBar = new JProgressBar(0, 100);
					reconcileProgressBar.setValue(0);
					reconcileProgressBar.setStringPainted(true);
					reconcilePanel.add(reconcileProgressBar);
					reconcileProgressBar.setPreferredSize(new java.awt.Dimension(600, 20));
				}
				{
					jSeparator4 = new JSeparator();
					reconcilePanel.add(jSeparator4);
					jSeparator4.setPreferredSize(new java.awt.Dimension(700, 0));
				}
				{
					reconcileProgressLabel = new JLabel();
					reconcilePanel.add(reconcileProgressLabel);
				}


			}
			this.setSize(700, 400);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			// Silently ignoring errors
		}
	}




}
