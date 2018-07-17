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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
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
public class SNACJavaParserConstellationWindow extends javax.swing.JFrame {

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
	private JTextArea parsedDisplay;


	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			// Silently ignoring errors
		}
	}


	/**
	 * Constructor
	 * 
	 * Calls JFrame's constructor and then initializes and displays the GUI
	 */
	public SNACJavaParserConstellationWindow(String constellation) {
		super();
		initGUI(constellation);
	}
	
	public static void showConstellation(String constellation) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SNACJavaParserConstellationWindow inst = new SNACJavaParserConstellationWindow(constellation);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
		
	}

	/**
	 * Initialize GUI
	 * 
	 * Builds the JFrame to display to the user.
	 */
	public void initGUI(String constellation) {
		try {

			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			{
				title = new JPanel();
				getContentPane().add(title, BorderLayout.NORTH);
				title.setSize(900, 40);
				{
					titleLabel = new JLabel("<html><body style='text-align: center; font-size: 20px; margin: 0px; padding: 0px;'>Parsed Constellation</body></html>", SwingConstants.CENTER);
					title.add(titleLabel);
					titleLabel.setPreferredSize(new java.awt.Dimension(900, 40));
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
				parserPanel.setSize(900, 700);
				parserPanel.setPreferredSize(new java.awt.Dimension(900, 700));
				
				parsedDisplay = new JTextArea();
				parsedDisplay.setText(constellation.toString());
				
				
				JScrollPane sp = new JScrollPane(parsedDisplay);
				sp.setPreferredSize(new java.awt.Dimension(900, 700));
				parserPanel.add(sp);


			}
			this.setSize(900, 800);
		} catch (Exception e) {
			// Silently ignoring errors
		}
	}




}
