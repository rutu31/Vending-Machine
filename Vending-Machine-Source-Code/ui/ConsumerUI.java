
package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

import vm.*;

//Consumer UI Class
/**
 * This class creates the Window for Consumer Interface of the Vending Machine.
 * <br> This window shows the available Food Stocks, Display Screen, Consumer Operations and different Machine h/w Slots.
 * <br> This window also contains a button which has the link to the Main User Interface of the Vending Machine.
 * <br> This class also implements {@link Observer} interface. This acts as an observer for the observable VendingMachine class. 
 */
public class ConsumerUI extends JFrame implements Observer{
	
	// Data Members
	private static final long serialVersionUID = 2L;
	
	private JPanel itemPanel,displayPanel, actionPanel1, actionPanel2, dispatchPanel, exitPanel, subPanel1, subPanel2, subPanel3;
	private JLabel displayLabel, acceptLabel, acceptLabel1, enterLabel, changeLabel, dispatcherLabel, calorieLabel;
	private JLabel[] itemLabelArray, codeLabelArray, priceLabelArray, quantityLabelArray;
	private JTextField codeText, codeText1, amountText, changeText, calorieText;
	private JRadioButton buyItem, displayNutri, querySuggest;
	private JButton ok1, ok2, ok3, ok4, buy, exit, cancel;
	
	private String enterAmount = "Enter Amount", change = "Collect Change", dispatcher = "Food Dispatcher"; 
	private String display = "          Display Screen               ", accept = "Enter Item Code";
	private String text, text1;
	private Integer intValue, intValue2, cancelFlag=0;
	private Double changeAmount;
	private Hashtable itemList;
	
	private MainUI parentUI;
	
	
	// ConsumerUI Constructor
	/**
	 * @param parentUI an instance of MainUI class
	 */
	public ConsumerUI(MainUI parentUI){
		super("SmartCal Vending Machine - Consumer Operations");
		super.setLocation(200, 10);
		this.parentUI = parentUI;
		
		VendingMachine machine = parentUI.getVendingMachine();	// Get an instance of Vending Machine 
		machine.addObserver(this); 			// Register ConsumerUI as an observer to receive VendingMachine updates
		
		Container container1 = getContentPane();
		container1.setLayout(new BoxLayout(container1, BoxLayout.Y_AXIS));
		
		// itemPanel = Food Stock Panel
		itemPanel = new JPanel(new GridLayout(0,4));
		itemPanel.setBorder(BorderFactory.createTitledBorder("Food Stock")); 
		
		itemLabelArray = (JLabel[])Array.newInstance(JLabel.class, 15);
		codeLabelArray = (JLabel[])Array.newInstance(JLabel.class, 15);
		priceLabelArray = (JLabel[])Array.newInstance(JLabel.class, 15);
		quantityLabelArray = (JLabel[])Array.newInstance(JLabel.class, 15);
	
		for(int i=0; i < 15; i++) {						// Maximum No. of Food Item Slots is 15 
			itemLabelArray[i] = new JLabel();
			codeLabelArray[i] = new JLabel();
			priceLabelArray[i] = new JLabel();
			quantityLabelArray[i] = new JLabel();
		
			itemPanel.add(itemLabelArray[i]);
			itemPanel.add(codeLabelArray[i]);
			itemPanel.add(priceLabelArray[i]);
			itemPanel.add(quantityLabelArray[i]);
		}
		
		itemList = machine.getFoodStocks();			// Get the list of Food Items from the Vending Machine &
		Enumeration e = itemList.elements();		// Store it in the Enumeration
		
		for(int i = 0; e.hasMoreElements(); i++){					// Store the Items from Enumeration to the ConsumerUI Variables
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
		displayPanel.add(displayLabel);
		displayPanel.setBorder(BorderFactory.createTitledBorder("Display Screen"));
		
		container1.add(displayPanel);
		
		// actionPanel1 = Consumer Operations Panel
		actionPanel1 = new JPanel(new GridLayout(0,1));
		actionPanel1.setBorder(BorderFactory.createTitledBorder("Consumer Operations"));
		
		// actionPanel1 = subPanel1 + subPanel2  + subPanel3
		
		// subPanel1
		subPanel1 = new JPanel(new FlowLayout());
		buyItem = new JRadioButton("Buy a Food Item");
		acceptLabel = new JLabel(accept);
		codeText = new JTextField(10);
		
		subPanel1.add(buyItem);
		subPanel1.add(acceptLabel);
		subPanel1.add(codeText);
		
		actionPanel1.add(subPanel1);
		
		// subPanel2
		subPanel2 = new JPanel(new FlowLayout());
		displayNutri = new JRadioButton("Display Nutritional Info");
		acceptLabel1 = new JLabel(accept);
		codeText1 = new JTextField(10);
		ok3 = new JButton("Display"); // for enter itemCode
		
		subPanel2.add(displayNutri);
		subPanel2.add(acceptLabel1);
		subPanel2.add(codeText1);
		subPanel2.add(ok3);
		
		actionPanel1.add(subPanel2);
		
		// subPanel3
		subPanel3 = new JPanel(new FlowLayout());
		querySuggest = new JRadioButton("Query Suggestion of Food Items");
		calorieLabel = new JLabel("Maximum Calorie Limit");
		calorieText = new JTextField(10);
		ok4 = new JButton("Display");
		
		subPanel3.add(querySuggest);
		subPanel3.add(calorieLabel);
		subPanel3.add(calorieText);
		subPanel3.add(ok4);
		
		actionPanel1.add(subPanel3);
		
		container1.add(actionPanel1);
		
		// actionPanel2 - H/W Slots = actionPanel2 + dispatchPanel
		actionPanel2 = new JPanel(new FlowLayout());
		actionPanel2.setBorder(BorderFactory.createTitledBorder("Hardware Slots"));
		enterLabel = new JLabel(enterAmount);
		amountText = new JTextField(10);
		buy = new JButton("BUY");
		cancel = new JButton("Cancel");
		changeLabel = new JLabel(change);
		changeText = new JTextField(10);
		ok1 = new JButton("Collect Change");
		
		actionPanel2.add(enterLabel);
		actionPanel2.add(amountText);
		actionPanel2.add(buy);
		actionPanel2.add(cancel);
		actionPanel2.add(changeLabel);
		actionPanel2.add(changeText);
		actionPanel2.add(ok1);
		
		// Dispatch Panel
		dispatchPanel = new JPanel(new FlowLayout());
		dispatcherLabel = new JLabel(dispatcher);
		ok2 = new JButton("Collect Item");
				
		dispatchPanel.add(dispatcherLabel);
		dispatchPanel.add(ok2);
		
		actionPanel2.add(dispatchPanel);
		
		container1.add(actionPanel2);
		
		// Exit Panel
		exitPanel = new JPanel(new FlowLayout());
		exit = new JButton("Back to Main Menu");
		exitPanel.add(exit);
		
		container1.add(exitPanel);
		
		// Registering the components to the Event Handlers
		// Register Buy button to Event Handler
		BuyHandler BHandler = new BuyHandler();
		buy.addActionListener(BHandler);
		
		// Register button of cancel to Event Handler
		CancelHandler CHandler = new CancelHandler();
		cancel.addActionListener(CHandler);
		
		// Register RadioButton of 'Buy Item' to Event Handler
		BuyRadioHandler BRHandler = new BuyRadioHandler ();
		buyItem.addActionListener(BRHandler);
		
		// Register RadioButton of 'Display Nutritional Info' to Event Handler 
		NutriRadioHandler NutriHandler = new NutriRadioHandler ();
		displayNutri.addActionListener(NutriHandler);

		// Register RadioButton of 'Query Suggestion' to Event Handler
		QueryRadioHandler QueryHandler = new QueryRadioHandler();
		querySuggest.addActionListener(QueryHandler);
		
		// Register Button for 'Display Nutritional Info' to Event Handler
		DisplayNutriHandler DNHandler = new DisplayNutriHandler();
		ok3.addActionListener(DNHandler);
		
		// Register Button for 'Query Suggestion' to Event Handler
		DisplayNutriHandler DNHandler1 = new DisplayNutriHandler();
		ok4.addActionListener(DNHandler1);
		
		// Register Button for 'Back to Main Menu' to Event Handler 
		MainMenuHandler1 MMHandler1 = new MainMenuHandler1();
		exit.addActionListener(MMHandler1);
		
		// Register Button for 'Collect Change' to Event Handler 
		CollectChangeHandler CCHandler = new CollectChangeHandler();
		ok1.addActionListener(CCHandler);

		// Register Button for 'Collect Food Item' to Event Handler
		CollectItemHandler CIHandler = new CollectItemHandler();
		ok2.addActionListener(CIHandler);
		
	}
	
	// Event Handler Classes
	// Event Handler Class for 'Buy Item' Button
	private class BuyHandler implements ActionListener {
		public void actionPerformed(ActionEvent event){
			if(buyItem.isSelected() && (codeText.getText() != null) && (amountText.getText() != null)){
				text = codeText.getText();				// Get the text value of Item Code
				try {
					intValue = Integer.valueOf(text);
				} catch (NumberFormatException e) {
					display = "Enter Correct Item Code !";		// Display Error Message for Wrong Input
					displayLabel.setText(display);
					codeText.setText(null);
					return;
				}
				
				text1 = amountText.getText();			// Get the text value of Amount entered
				Double val;
				try {
					val = Double.valueOf(text1);
				} catch (NumberFormatException e) {
					display = "Enter Correct Amount !";			// Display Error Message for Wrong Input
					displayLabel.setText(display);
					amountText.setText(null);
					return;
				}
				
				if(val < 0){
					display = "Enter Correct Amount !";		 // Display Error Message for Wrong (negative) Input
					displayLabel.setText(display);
					amountText.setText(null);
				}
				else{
					changeAmount = BuyFoodItemWithCash(intValue, val);	// Call to the Method to Buy the Food Item

					if(changeAmount == -1){						// Insufficient Amount
						display = "Amount not Sufficient ! Enter cash !!";
						displayLabel.setText(display);
					}
					else if(changeAmount == -2){				// Wrong Item Code
						display = "Enter correct Item Code !";
						displayLabel.setText(display);
						codeText.setText(null);
						amountText.setText(null);
					}

					else if(changeAmount > 0 ){					// Display the Change 
						String str = changeAmount.toString();
						changeText.setText(str);
						display = "Collect the Change !";
						displayLabel.setText(display);
					}
					else{										// No change (changeAmount == 0)
						display = "Collect your Food Item";
						displayLabel.setText(display);
					}
				}
			}
		}
	}
	
	// Event Handler Class for 'Cancel' Button
	private class CancelHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			 Double change;
			 
			 if(buyItem.isSelected() && (codeText.getText() != null) && (amountText.getText() != null)){
				 String str = amountText.getText();
				 Double val = Double.valueOf(str);

				 change = CancelBuy(val);			// Call to the Method to Cancel buying Operation

				 if(change > 0){					// Return the Amount entered
					 String str1 = change.toString();
					 changeText.setText(str1);
					 display = "Collect the Change !";
					 displayLabel.setText(display);
					 amountText.setText(null);
					 cancelFlag = 1;
				 }
			 }
		}
	}
	
	// Event Handler Class for JRadioButton of 'Buy Item'
	public class BuyRadioHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			displayNutri.setSelected(false); 	
			querySuggest.setSelected(false);
			
			codeText.setText(null);
			codeText1.setText(null);
			amountText.setText(null);
			calorieText.setText(null);
			changeText.setText(null);
			
			display = "Enter the Item Code & Amount";
			displayLabel.setForeground(Color.magenta);
			displayLabel.setText(display);
		}
	}
	
	// Event Handler Class for JRadioButton of 'Display Nutritional Info'
	public class NutriRadioHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			buyItem.setSelected(false); 
			querySuggest.setSelected(false);
			
			codeText.setText(null);
			codeText1.setText(null);
			amountText.setText(null);
			calorieText.setText(null);
			changeText.setText(null);
			
			display = "Enter the Item Code";
			displayLabel.setForeground(Color.magenta);
			displayLabel.setText(display);
		}
	}
	
	// Event Handler Class for JRadioButton of 'Query Suggestion'
	public class QueryRadioHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			buyItem.setSelected(false); 
			displayNutri.setSelected(false); 
			
			codeText.setText(null);
			codeText1.setText(null);
			amountText.setText(null);
			calorieText.setText(null);
			changeText.setText(null);
			
			display = "Enter the Maximum Calorie Limit";
			displayLabel.setForeground(Color.magenta);
			displayLabel.setText(display);
			
		}
	}
	
	// Event Handler Class for 'Display Nutritional Info' Button & 'Display Query Suggestion' Button
	public class DisplayNutriHandler implements ActionListener{
		double calories, sugar, fats;
		int flag=0;
		
		public void actionPerformed(ActionEvent event){
			if(displayNutri.isSelected() && codeText.getText() != null){	// for Display Nutritional Info Button
				try{
					intValue2 = Integer.valueOf(codeText1.getText());	// Get the text value of Item Code
				} catch(NumberFormatException e){
					display = "Enter Correct Item Code";				// Display Error Message for Wrong Input
					displayLabel.setForeground(Color.magenta);
					displayLabel.setText(display);
					codeText1.setText(null);
					return;
				}
				FoodItem item = DisplayNutritionalInfo(intValue2);		// Call to the Method to Display Nutritional Info
				
				if(item != null){
					calories = item.GetCalories();		// Get the nutrional Info of the Item Code from the FoodItem
					sugar = item.GetSugarContents();
					fats = item.GetFatContents();

					display = "Calorific Value = " + calories + "   Sugar Contents = "+ sugar + "gms" + "   Fat Contents = " + fats + "gms";
					displayLabel.setForeground(Color.magenta);
					displayLabel.setText(display);
				}
				else{
					display = "Enter Correct item Code";				// Display Error Message for Wrong Input
					displayLabel.setForeground(Color.magenta);
					displayLabel.setText(display);
					codeText.setText(null);
				}
			}
			
			if(querySuggest.isSelected() && calorieText.getText() != null){		// for Display Query Suggestion Button
				try{
					intValue2 = Integer.valueOf(calorieText.getText());			// Get the text value of Maximum Calorie Limit
				} catch(NumberFormatException e){
					display = "Enter Correct Calorie Limit";				// Display Error Message for Wrong Input
					displayLabel.setForeground(Color.magenta);
					displayLabel.setText(display);
					calorieText.setText(null);
					return;
				}
				
				if(intValue2 < 0){       
					display = "Enter Correct Calorie Limit";		// Display Error Message for Wrong (negative) Input
					displayLabel.setForeground(Color.magenta);
					displayLabel.setText(display);
					calorieText.setText(null);
				}
				else{
					Hashtable itemList = DisplaySuggestion();
					Enumeration e = itemList.elements();

					String finalStr = "";
					for(int i = 0; e.hasMoreElements(); i++){			// Get the list of Food items below the entered Calorie Limit
						VendingMachine.FoodStock stock = (VendingMachine.FoodStock)e.nextElement();
						FoodItem item = stock.item;
						if(item.GetCalories() <= intValue2){
							flag = 1;
							if (!finalStr.equals("")) {
								finalStr = finalStr + " , " + item.getDescription();
							} else {
								finalStr = item.getDescription();
							}
						}
					}
					if(flag == 1){
						display = finalStr;					// Display the list of Food items below the entered Calorie Limit
						displayLabel.setForeground(Color.magenta);
						displayLabel.setText(display);
						flag = 0;
					}
					else{
						display = "No items in the Machine below Calorie Limit " + intValue2;  // No Items below the entered Calorie Limit
						displayLabel.setForeground(Color.magenta);
						displayLabel.setText(display);
					}
				}
			}
		}
	}
	
	// Event Handler Class for 'Back to Main Menu' Button
	public class MainMenuHandler1 implements ActionListener{
		public void actionPerformed(ActionEvent event){
			setVisible(false);
			dispose();
			parentUI.setVisible(true);
		}
	}

	// Event Handler Class for 'Collect Change' Button
	public class CollectChangeHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			codeText.setText(null);
			amountText.setText(null);
			changeText.setText(null);
			calorieText.setText(null);
			
			if(cancelFlag != 1){					// Buying operation is not canceled 
				display = "Collect the Food Item !";
				displayLabel.setForeground(Color.magenta);
				displayLabel.setText(display);
			}
			else{
				cancelFlag = 0;						// Buying operation is canceled 
				display = "Thank you...";
				displayLabel.setForeground(Color.magenta);
				displayLabel.setText(display);
			}
		}
	}
	
	// Event Handler Class for 'Collect Food Item' Button
	public class CollectItemHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			codeText.setText(null);
			amountText.setText(null);
			changeText.setText(null);
			calorieText.setText(null);
			
			display = "Thank You...";
			displayLabel.setForeground(Color.magenta);
			displayLabel.setText(display);
		}
	}
	
	// Methods
	// 1. Method to Buy the Food Item
	/**
	 * @param itemCode Item Code of the Food Item which is to be bought
	 * @param amount Amount entered for buying the Food Item
	 * @return change
	 * <p>
	 * This method calls the 'BuyItemWithCash' method in the VendingMachine class.
	 */
	public double BuyFoodItemWithCash(int itemCode , double amount){
		double change;
		
		VendingMachine machine = parentUI.getVendingMachine();	 
		change = machine.BuyItemWithCash( itemCode, amount);	// Call to the method in Vending Machine Class
		
		return change;
	}
	
	// 2. Method to Display Nutritional Info
	/**
	 * @param itemCode Item Code of the Food Item whose Nutritional Info is to be displayed
	 * return item - Food Item
	 * <p>
	 * This method calls the 'GetFoodItem' method in the VendingMachine class.  
	 */
	public FoodItem DisplayNutritionalInfo(int itemCode){
		VendingMachine machine = parentUI.getVendingMachine();
		FoodItem item = machine.GetFoodItem(itemCode);			// Call to the method in Vending Machine Class
	
		return item;
	}
	
	// 3. Method to Display Query Suggestion
	/**
	 * @return itemList - List of the food items
	 * <p>
	 * This method calls the 'getFoodStocks' method in the VendingMachine class.
	 */
	public Hashtable DisplaySuggestion(){
		VendingMachine machine = parentUI.getVendingMachine();
		Hashtable itemList = machine.getFoodStocks();
		
		return itemList;
	}
	
	// 4. Method to Cancel the buying operation
	/**
	 * @param amount Amount entered for buying operation
	 * @return change
	 * <p>
	 * This method calls the 'CancelBuy' method in the VendingMachine class.
	 */
	public double CancelBuy(double amount){
		double change;
		
		VendingMachine machine = parentUI.getVendingMachine();
		change = machine.CancelBuyOperation(amount);		// Call to the method in Vending Machine Class
		
		return change;
	}
	
	// Update Method for the Observer ConsumerUI
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
		
	}
}
