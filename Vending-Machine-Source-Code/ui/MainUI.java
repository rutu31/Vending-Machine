
package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

import vm.*;

/**
 * 
 * @author Rutuja
 * <p>
 * <br>	This class creates the Main Window of the user interface of Vending Machine.
 * <br> This main window contains two buttons which contain links to 
 * <br> 1. Administrator Interface & 2. Consumer Interface. 
 */

public class MainUI extends JFrame{

	// Data Members
	private static final long serialVersionUID = 1L;
	
	private JButton consumer, administrator, mainExit;
	private JPanel titlePanel, buttonsPanel, subTitlePanel, blankPanel, mainExitPanel;
	private JLabel titleLabel, subTitleLabel;
	
	private VendingMachine machine;
	private ConsumerUI consumerUI;
	private AdminUI adminUI;
	private LoginUI loginUI;
	
	String outputFile, inputFile;
	
	public static void main(String[] args){
		if (args.length < 2) {				// Format : MainUI <inputfile> <outputFile>
			System.exit(0);
		}
		String inputFile = args[0];
		String outputFile = args[1];
	
		MainUI application = new MainUI(inputFile, outputFile);
		application.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	// MainUI constructor
	/**
	 * @param inputFile Input database file to be parsed
	 * @param outputFile Output database file 
	 */
	public MainUI(String inputFile, String outputFile){
		super("SmartCal Vending Machine");
		super.setLocation(300, 200);

		this.outputFile = outputFile;
		
		// Create an instance of DataParser class to parse the XML database file
		DataParser fileParser = new DataParser();
		machine = fileParser.getVendingMachine(inputFile);	  // Get the vending machine instance
		
		consumerUI = new ConsumerUI(this);	 // Create an instance of Consumer UI
		consumerUI.setSize(800,750);
		consumerUI.setVisible(false);
		consumerUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		adminUI = new AdminUI(this);	// Create an instance of Administrator UI
		adminUI.setSize(800, 750);
		adminUI.setVisible(false);
		adminUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		loginUI = new LoginUI(adminUI);	// Create an instance of Login UI
		loginUI.setSize(400, 100);
		loginUI.setVisible(false);
		loginUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		init();
	}
	
	// Method to create the MainUI Window
	public void init(){
		Container container = getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
			
		// Blank Panel
		blankPanel = new JPanel(new FlowLayout());
		container.add(blankPanel);
		container.add(blankPanel);
		
		// Title Panel
		titlePanel = new JPanel(new FlowLayout());
		titleLabel = new JLabel("SmartCal Vending Machine");
		titlePanel.add(titleLabel);
		container.add(titlePanel);
		
		// SubTitlePanel
		subTitlePanel = new JPanel(new FlowLayout());
		subTitleLabel = new JLabel("Your Identity, please...");
		subTitlePanel.add(subTitleLabel);
		container.add(subTitlePanel);
		
		// Buttons Panel
		buttonsPanel = new JPanel(new FlowLayout());
		consumer = new JButton("Consumer");
		administrator = new JButton("Administrator");
		buttonsPanel.add(consumer);
		buttonsPanel.add(administrator);
		container.add(buttonsPanel);
	
		// Main Exit Panel
		mainExitPanel = new JPanel(new FlowLayout());
		mainExit = new JButton("Main Exit");
		mainExitPanel.add(mainExit);
		container.add(mainExitPanel);
		
		// Registering the Components with the Event Handlers
		ConsumerHandler CHandler = new ConsumerHandler();
		consumer.addActionListener(CHandler);	// Consumer Button
		
		AdminHandler AHandler = new AdminHandler();
		administrator.addActionListener(AHandler);	// Administrator Button
		
		ExitHandler EHandler = new ExitHandler();
		mainExit.addActionListener(EHandler);	// Main Exit Button
		
		setSize(600,400);
		setVisible(true);
	}	
	
	// Method to get an instance of Vending Machine
	/**
	 * @return machine - Vending Machine instance
	 */
	public VendingMachine getVendingMachine(){
		return machine;
	}
	
	// Event Handler Classes
	// Event Handler Class for Consumer Button
	private class ConsumerHandler implements ActionListener {
		public void actionPerformed(ActionEvent event){
			setVisible(false);		 // close the MainUI Window
			dispose();				// Release all of the native window resources used by MainUI Window
			consumerUI.setVisible(true);
		}
	}
	
	// Event Handler Class for Administrator Button	
	public class AdminHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			setVisible(false);		 // close the MainUI Window
			dispose();				// Release all of the native window resources used by MainUI Window
			loginUI.setVisible(true);
		}
	}
	
	// Eevnt Handler Class for Main Exit Button
	public class ExitHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			try {
				// Create an instance of BufferedWriter to write in the database file
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
				
				// Write the Vending Machine Data into the XML file
				machine.WriteXMLToFile(writer);
				writer.close();		// Close the File
				
				dispose();			// Release all of the native window resources used by MainUI Window
				System.exit(0);		// Exit the System
			} 
			catch(IOException e) {		// Catching the File I/O exception
				e.printStackTrace();
			}
		}
	}
}




