package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

import vm.*;

//Administrator UI Class
/**
 * This class creates the Window for Administrator Interface of the Vending Machine.
 * <br> This window shows the available Food Stocks, Display Screen, Administrator Operations and different Machine h/w Slots.
 * <br> This window also contains a button which has the link to the Main User Interface of the Vending Machine.
 * <br> This class also implements {@link Observer} interface. This acts as an observer for the observable VendingMachine class. 
 */

public class AdminUI extends JFrame implements Observer{
	private static final long serialVersionUID = 3L;
	
	private JPanel itemPanel, displayPanel, actionPanel0, actionPanel1, actionPanel2, actionPanel3, actionPanel4, subPanel1, subPanel2;
	private JPanel operationPanel, slotsPanel,cashPanel, dispatchPanel, exitPanel;
	private JLabel priceLabel, descLabel, caloriesLabel, sugarLabel, fatsLabel, quantLabel;
	private JLabel displayLabel, codeLabel, newPriceLabel, amountLabel, changeLabel, dispatchLabel;
	private JLabel[] itemLabelArray, codeLabelArray, priceLabelArray, quantityLabelArray;
	private JButton ok1, ok2, ok3, ok4, exit, buy, cancel;
	private JRadioButton changePrice, addItem, displayBalance;
	private JTextField codeText, newPriceText, priceText, descText, caloriesText, sugarText, fatsText, quantText;
	private JTextField amountText, changeText;
	
	private String display = "          Display Screen               ", accept = "Enter Item Code", newPrice = "Enter New Price";
	private String text, text1, text2, text3, text4, text5;
	private Integer intValue;
	private Double intValue1, intValue2, intValue3, intValue4;
	
	private boolean done;
	private Hashtable itemList;
	
	private MainUI parentUI;	
	
	// AdminUI Constructor
	/**
	 * @param parentUI an instance of MainUI class
	 */
	public AdminUI(MainUI parentUI){
		super("SmartCal Vending Machine - Administrator Operations");
		super.setLocation(200, 10);
		this.parentUI = parentUI;
		
		VendingMachine machine = parentUI.getVendingMachine();
		machine.addObserver(this); 				// Register AdminUI as an observer to receive VendingMachine Updates
	
		Container container1 = getContentPane();
		container1.setLayout(new BoxLayout(container1, BoxLayout.Y_AXIS));
		
		//itemPanel = Food Stock Panel
		itemPanel = new JPanel(new GridLayout(0,4));
		itemPanel.setBorder(BorderFactory.createTitledBorder("Food Stock"));
		
		itemLabelArray = (JLabel[])Array.newInstance(JLabel.class, 15);
		codeLabelArray = (JLabel[])Array.newInstance(JLabel.class, 15);
		priceLabelArray = (JLabel[])Array.newInstance(JLabel.class, 15);
		quantityLabelArray = (JLabel[])Array.newInstance(JLabel.class, 15);
	
		for(int i=0; i < 15; i++) { 							// Maximum No. Food Item Slots is 15
			itemLabelArray[i] = new JLabel();
			codeLabelArray[i] = new JLabel();
			priceLabelArray[i] = new JLabel();
			quantityLabelArray[i] = new JLabel();
		
			itemPanel.add(itemLabelArray[i]);
			itemPanel.add(codeLabelArray[i]);
			itemPanel.add(priceLabelArray[i]);
			itemPanel.add(quantityLabelArray[i]);
		}
			
		// store the itemLabels from Hashtable to the itemLabelArray
		
		itemList = machine.getFoodStocks();					// Get the list of Food Items from the Vending Machine &
		Enumeration e = itemList.elements();				// Store it in the Enumeration
		
		for(int i = 0; e.hasMoreElements(); i++){				// Store the Items from Enumeration to the AdminUI Variables
			VendingMachine.FoodStock stock = (VendingMachine.FoodStock)e.nextElement();
			FoodItem item = stock.item;
			itemLabelArray[i].setText(item.getDescription());
			
			Integer code = item.getItemCode();
			String str = code.toString();
			codeLabelArray[i].setText("Item Code: " + str);
			
			Double price = item.getPrice();
			String str1 = price.toString();
			priceLabelArray[i].setText("Price: " + str1);
			
			Integer quant = machine.GetQuantity(code);
			String str2 = quant.toString();
			quantityLabelArray[i].setText("Quantity: " + str2);
		}
		
		container1.add(itemPanel);
		
		// displayPanel
		displayPanel = new JPanel(new FlowLayout());
		displayLabel = new JLabel(display);
		displayPanel.setBorder(BorderFactory.createTitledBorder("Display Screen"));						
		displayPanel.add(displayLabel);
		
		container1.add(displayPanel);
		
		// operationPanel = Administrator Operations Panel
		// operationPanel = subPanel1(actionPanel0 + actionPanel1 + actionPanel2) + subPanel2(actionPanel3 + actionPanel4)
		operationPanel = new JPanel(new GridLayout(0,1));
		operationPanel.setBorder(BorderFactory.createTitledBorder("Administrator Operations"));
		
		// actionPanel0
		actionPanel0 = new JPanel(new FlowLayout());
		displayBalance = new JRadioButton("Display Machine Balance");
		
		actionPanel0.add(displayBalance);
		
		//actionPanel1
		actionPanel1 = new JPanel(new FlowLayout());
		changePrice = new JRadioButton("Change the Price of a Food Item");
		
		actionPanel1.add(changePrice);
		
		//actionPanel2
		actionPanel2 = new JPanel(new FlowLayout());
		codeLabel = new JLabel(accept);
		codeText = new JTextField(10);
		newPriceLabel = new JLabel(newPrice);
		newPriceText = new JTextField(10);
		ok3 = new JButton("OK");
		
		actionPanel2.add(codeLabel);
		actionPanel2.add(codeText);
		actionPanel2.add(newPriceLabel);
		actionPanel2.add(newPriceText);
		actionPanel2.add(ok3);
	
		// subPanel1 = actionPanel0 + actionPanel1 + actionPanel2
		subPanel1 = new JPanel(new GridLayout(3,1));
		subPanel1.add(actionPanel0);
		subPanel1.add(actionPanel1);
		subPanel1.add(actionPanel2);
		
		//actionPanel3
		actionPanel3 = new JPanel(new FlowLayout());
		addItem = new JRadioButton("Introduce New Food Item");
		ok4 = new JButton("OK");
		
		actionPanel3.add(addItem);
		actionPanel3.add(ok4);
		
		//actionPanel4
		actionPanel4 = new JPanel(new GridLayout(2,3));
		priceLabel = new JLabel("Price");
		priceText = new JTextField(10);
		descLabel = new JLabel("Description");
		descText = new JTextField(10);
		caloriesLabel = new JLabel("Calories");
		caloriesText = new JTextField(10);
		sugarLabel = new JLabel("Sugar Contents");
		sugarText = new JTextField(10);
		fatsLabel = new JLabel("Fat Contents");
		fatsText = new JTextField(10);
		quantLabel = new JLabel("Quantity");
		quantText = new JTextField(10);
		
		
		actionPanel4.add(priceLabel);
		actionPanel4.add(priceText);
		actionPanel4.add(descLabel);
		actionPanel4.add(descText);
		actionPanel4.add(caloriesLabel);
		actionPanel4.add(caloriesText);
		actionPanel4.add(sugarLabel);
		actionPanel4.add(sugarText);
		actionPanel4.add(fatsLabel);
		actionPanel4.add(fatsText);
		actionPanel4.add(quantLabel);
		actionPanel4.add(quantText);
		
		// subPanel2 = actionPanel3 + actionPanel4
		subPanel2 = new JPanel(new FlowLayout());
		subPanel2.add(actionPanel3);
		subPanel2.add(actionPanel4);
	
		// operationPanel = subPanel1 + subPanel2
		operationPanel.add(subPanel1);
		operationPanel.add(subPanel2);
		
		container1.add(operationPanel);

		// slotsPael = H/W Slots Panel
		// slotsPanel = cashPanel + dispatchPanel
		slotsPanel = new JPanel(new GridLayout(2,1));
		slotsPanel.setBorder(BorderFactory.createTitledBorder("Hardware Slots"));
		
		// cashPanel
		cashPanel = new JPanel(new FlowLayout());
		amountLabel = new JLabel("Enter Amount");
		amountText = new JTextField(10);
		buy = new JButton("Buy");
		cancel = new JButton("Cancel");
		changeLabel = new JLabel("Collect Change");
		changeText = new JTextField(10);
		ok1 = new JButton("Collect Change");
		
		cashPanel.add(amountLabel);
		cashPanel.add(amountText);
		cashPanel.add(buy);
		cashPanel.add(cancel);
		cashPanel.add(changeLabel);
		cashPanel.add(changeText);
		cashPanel.add(ok1);
		
		slotsPanel.add(cashPanel);
		
		// dispatchPanel
		dispatchPanel = new JPanel(new FlowLayout());
		dispatchLabel = new JLabel("Food Dispatcher");
		ok2 = new JButton("Collect Item");
		
		dispatchPanel.add(dispatchLabel);
		dispatchPanel.add(ok2);
	
		slotsPanel.add(dispatchPanel);
		
		container1.add(slotsPanel);
		
		// exitPanel
		exitPanel = new JPanel(new FlowLayout());
		exit = new JButton("Back to main Menu");
		
		exitPanel.add(exit);
		slotsPanel.add(exitPanel);
		
		container1.add(exitPanel);
		
		// Register the Radio Button for 'Change Price' to Event Handler
		PriceRadioHandler PRHandler = new PriceRadioHandler();
		changePrice.addActionListener(PRHandler);

		// Register the Radio Button for 'Introduce Item' to Event Handler
		ItemRadioHandler IRHandler = new ItemRadioHandler();
		addItem.addActionListener(IRHandler);
		
		// Register the Radio Button for 'Display Machine Balance' to Event Handler
		BalanceRadioHandler BRHandler = new BalanceRadioHandler();
		displayBalance.addActionListener(BRHandler);
		
		// Register the Button for 'Change Price' to Event Handler
		ChangePriceHandler CPHandler = new ChangePriceHandler();
		ok3.addActionListener(CPHandler);
		
		// Register the Button for 'Introduce Item' to Event Handler
		IntroduceHandler IHandler = new IntroduceHandler();
		ok4.addActionListener(IHandler);
		
		// Register the Button for 'Back to Main Menu' to Event Handler
		MainMenuHandler2 MMHandler2 = new MainMenuHandler2();
		exit.addActionListener(MMHandler2);
		
	}
	
	// Event Handler Classes
	// Event Handler Class for 'Change Price' Button
	public class ChangePriceHandler implements ActionListener{
		boolean done;
		
		public void actionPerformed(ActionEvent event){
			if(changePrice.isSelected() && (codeText.getText() != null)){
				text = codeText.getText();			// Get the text value of Item Code
				intValue = Integer.valueOf(text);

				text1 = newPriceText.getText();		// Get the text value of New Price
				intValue1 = Double.valueOf(text1);
				
				if(intValue1 <= 0){
					displayLabel.setForeground(Color.magenta);
					display = "Enter Correct Price !";			// Display Error Message for Wrong (negative & zero) Input
					displayLabel.setText(display);
					newPriceText.setText(null);
				}
				else{
					done = ChangePrice(intValue, intValue1);	// Call to the Method to Change the Price 

					if(done){
						displayLabel.setForeground(Color.magenta);
						display = "Price Changed !";
						displayLabel.setText(display);
					}
					else{
						displayLabel.setForeground(Color.magenta);
						display = "Enter Correct Item Code";		// Display Error Message for Wrong Input
						displayLabel.setText(display);
						codeText.setText(null);
					}
				}
			}
		}
	}

	// Event Handler Class for 'Introduce item' Button
	public class IntroduceHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(addItem.isSelected() && (priceText.getText() != null)){
				text = priceText.getText();				// Get the text values of new Food Item - Price, Description, Calorific Value,
				text1 = descText.getText();				// Sugar Contents, Fats Contents and Quantity
				text2 = caloriesText.getText();
				text3 = sugarText.getText();
				text4 = fatsText.getText();
				text5 = quantText.getText();

				intValue1 = Double.valueOf(text);
				intValue2 = Double.valueOf(text2);
				intValue3 = Double.valueOf(text3);
				intValue4 = Double.valueOf(text4);
				intValue = Integer.valueOf(text5);
				
				if(intValue1<0 || intValue2<0 || intValue3<0 || intValue4<0 || intValue<0){
					displayLabel.setForeground(Color.magenta);
					display = "No negative entries, please ! Check them again !!";	// Display Error Message for Wrong Input
					displayLabel.setText(display);
				}
				else{											// Call to the Method to Introduce New Food Item
					done = IntroduceItem(intValue1, text1, intValue2, intValue3, intValue4, intValue);
					if(done){
						displayLabel.setForeground(Color.magenta);
						display = "New Item Introduced !";
						displayLabel.setText(display);
					}
					else{
						displayLabel.setForeground(Color.magenta); // Display Error Message, if item not introduced
						display = "Item Not Introduced !";
						displayLabel.setText(display);
					}
				}
			}
		}
	}
	
	// Event Handler Class for 'Change Price' Radio Button
	public class PriceRadioHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			addItem.setSelected(false);
			displayBalance.setSelected(false);
			
			codeText.setText(null);
			newPriceText.setText(null);
			priceText.setText(null);
			descText.setText(null);
			caloriesText.setText(null);
			sugarText.setText(null);
			fatsText.setText(null);
			quantText.setText(null);
			
			displayLabel.setForeground(Color.magenta);
			display = "Enter Item Code and New Price";
			displayLabel.setText(display);
		}
	}
	
	// Event Handler Class for 'Introduce Item' Radio Button
	public class ItemRadioHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			changePrice.setSelected(false);
			displayBalance.setSelected(false);
			
			codeText.setText(null);
			newPriceText.setText(null);
			priceText.setText(null);
			descText.setText(null);
			caloriesText.setText(null);
			sugarText.setText(null);
			fatsText.setText(null);
			quantText.setText(null);
			
			displayLabel.setForeground(Color.magenta);
			display = "Enter Price, Description, Calorie Contents, Sugar Contents, Fats and Quantity";
			displayLabel.setText(display);
		}
	}
	
	// Event Handler Class for 'Display Machine Balance' Radio Button
	public class BalanceRadioHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			changePrice.setSelected(false);
			addItem.setSelected(false);
			
			codeText.setText(null);
			newPriceText.setText(null);
			priceText.setText(null);
			descText.setText(null);
			caloriesText.setText(null);
			sugarText.setText(null);
			fatsText.setText(null);
			quantText.setText(null);
			
			Double balance = GetMachineBalance();
			String str = balance.toString();
			display = ("Machine Balance = " + str);
			displayLabel.setForeground(Color.magenta);
			displayLabel.setText(display);
		}
	}

	// Event Handler Class for 'Back to Main Menu' Button
	public class MainMenuHandler2 implements ActionListener{
		public void actionPerformed(ActionEvent event){
			setVisible(false);
			dispose();
			parentUI.setVisible(true);
		}
	}
	
	// Methods
	// 1. Method to Change the Price of a Food Item
	/**
	 * @param itemCode Item Code of the Food Item whose Value is to be changed
	 * @param newPrice New Price of the Food Item
	 * @return boolean value
	 * <p>
	 * <br> This method calls the 'ChangePrice' method in the VendingMachine class.
	 */
	public boolean ChangePrice(int itemCode, double newPrice){
		VendingMachine machine = parentUI.getVendingMachine();
		
		return machine.ChangePrice(itemCode, newPrice);  // Call to the Vending Machine Method
	}
	
	// 2. Method to Introduce New Food Item
	/**
	 * @param price Price of the new Food Item to be introduced
	 * @param description Description of the new Food Item to be introduced
	 * @param calories Calorific value of the new Food Item to be introduced
	 * @param sugar Sugar Contents of the new Food Item to be introduced
	 * @param fats Fat Contents of the new Food Item to be introduced
	 * @param quantity Quantity of the new Food Item to be introduced
	 * @return boolean value
	 * <p>
	 * <br> This method calls the 'AddFoodItem' method in the VendingMachine class.
	 * 
	 */
	public boolean IntroduceItem(double price, String description, double calories, 
								 double sugar, double fats, int quantity){
		VendingMachine machine = parentUI.getVendingMachine();
	
		return machine.AddFoodItem(price, description, calories, sugar, fats, quantity); // Call to the Vending Machine Method
	}
	
	// 3. Method to get the Machine Balance
	/**
	 * @return balance
	 * <p>
	 * <br> This method calls the 'getBalance' method in the Moneyholder class Which is an inner class of VendingMachine class.
	 */
	public double GetMachineBalance(){
		VendingMachine machine = parentUI.getVendingMachine();
		VendingMachine.MoneyHolder holder = machine.getMoneyHolder();
		
		return holder.getBalance();  // Call to the Money Holder Method
	}
	
	// Update Method for the Observer AdminUI
	/**
	 * This method contains the definition for the 'update' method of the observer interface.
	 * This method updates the Food Stock panel in the user interface window.
	 */
	public void update(Observable observable, Object obj){
		VendingMachine machine = (VendingMachine)observable;
		itemList = (Hashtable)obj;
		Enumeration e = itemList.elements();

		for(int i = 0; e.hasMoreElements(); i++){			// Display the updates in the available Food Stocks
			VendingMachine.FoodStock stock = (VendingMachine.FoodStock)e.nextElement();
			FoodItem item = stock.item;
			
			String str0 = item.getDescription();
			itemLabelArray[i].setText(str0);
			
			Integer code = item.getItemCode();
			String str = code.toString();
			codeLabelArray[i].setText("Item Code: " + str);
			
			Double price = item.getPrice();
			String str1 = price.toString();
			priceLabelArray[i].setText("Price: " + str1);
			
			Integer quant = machine.GetQuantity(code);
			String str2 = quant.toString();
			quantityLabelArray[i].setText("Quantity: " + str2);
		}

	}
}