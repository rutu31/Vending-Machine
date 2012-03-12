
package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

//Login UI Class
/**
 * @author Rutuja
 * <p>
 * This class creates the Login Window for the Administrator.
 * <br> It checks for the password validity and if valid, 
 * <br> then links it to the Administrator interface of the Vending Machine. 
 */
public class LoginUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 4L;
	
	private JButton submit;
	private JPanel loginPanel;
	private JPasswordField passwordField;
	private JLabel passwordLabel;
	
	private AdminUI adminUI;
	
	// loginUI Constructor
	/**
	 * @param adminUI An instance of AdminUI class
	 */
	public LoginUI(AdminUI adminUI){
		super("Login Form");
		super.setLocation(300, 300);
		this.adminUI = adminUI;
		
		Container container1 = getContentPane();
		container1.setLayout(new FlowLayout());
		
		loginPanel = new JPanel(new FlowLayout());
		passwordLabel = new JLabel("Enter the Password");
		passwordField = new JPasswordField(10);
		submit = new JButton("SUBMIT");
		
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
		loginPanel.add(submit);
		loginPanel.setOpaque(true);
		
		container1.add(loginPanel);
		submit.addActionListener(this);
	}
	
	// Event Handling 
	public void actionPerformed(ActionEvent event){
		char[] input = passwordField.getPassword();
		
		if(CheckPassword(input)){		// Check for correctness of the Password
			passwordField.setText(null);
			setVisible(false);
			dispose();
			adminUI.setVisible(true);
		}
		else{								
			JOptionPane.showMessageDialog(null, "Invalid Password \n Try Again !");
			passwordField.setText(null);
		}
	}
	
	// Method to Check the correctness of the Password
	/**
	 * @param input Entered password
	 * @return correct 
	 * <p>
	 * <br> This method checks the entered password and sets - 
	 * <br> correct = True - if entered password is equal to the stored password
	 * <br> correct = False - if entered password is not equal to the stored password
	 */
	public boolean CheckPassword(char[] input){
		boolean correct;
		char[] correctPassword = {'r', 'u', 't', 'u', 'j', 'a'};
	
		if (input.length != correctPassword.length) {
            correct = false;
        } 
		else {
            correct = Arrays.equals (input, correctPassword);
        }
		
		return correct;
	}
}
