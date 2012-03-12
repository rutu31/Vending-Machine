
package vm;

import java.util.*;
import java.util.Observable;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * 
 * @author Rutuja
 *  <p>
 *	This class defines a Vending Machine.
 *	It extends from {@link Observable}.
 *	
 */
public class VendingMachine extends Observable{
	Hashtable<Integer, FoodStock> itemList; 	// Hashtable key = itemCode & value = FoodStock(FoodItem, Quantity)
	private double currentBalance;
	private int maxItemCode=0, flag=0;
	private MoneyHolder holder;
	
	/**
	 * 
	 * @author Rutuja
	 * This class contains {@link FoodItem} and 
	 * its available quantity in the Vending Machine 
	 */
	public class FoodStock {
		// Data Members
		public FoodItem item;
		public int quantity;
		
		/**
		 * 
		 * @param item FoodItem
		 * @param quantity Quantity of FoodItem item
		 */
		public FoodStock(FoodItem item, int quantity){
			this.item = item;
			this.quantity = quantity;
		}
	}
	
	/**
	 * 
	 * @author Rutuja
	 * This class maintains the collection of money 
	 * in the Vending Machine
	 */
	public class MoneyHolder{
		// Data Members
		private double machineBalance;
		
		// MoneyHolder Constructors
		public MoneyHolder(){
			this.machineBalance = 0.0;
		}
		/**
		 * 
		 * @param machineBalance Balance in the Vending Machine
		 */
		public MoneyHolder(double machineBalance){
			this.machineBalance = machineBalance;
		}
		
		// MoneyHolder Methods
		/**
		 * @return Balance in the Vending Machine
		 */
		public double getBalance(){
			return this.machineBalance;
		}
		
		/**
		 * 
		 * @param machineBalance Balance in the Vending Machine
		 */
		public void setBalance(double machineBalance){
			this.machineBalance = machineBalance;
		}
		
		/**
		 * 
		 * @param amount Amount to be deposited in the Vending Machine
		 */
		public void DepositMoney(double amount){
			this.machineBalance += amount;
		}
		
		/**
		 * 
		 * @param amount Amount to be withdrawn from the Vending Machine
		 */
		public void WithDrawMoney(double amount){
			this.machineBalance -= amount;
		}
	}

	// VendingMachine constructor
	public VendingMachine() {
		itemList = new Hashtable<Integer, FoodStock>();
		holder = new MoneyHolder();
		currentBalance = 0;
		maxItemCode = 0;
	}
	
	/* 1. Buy a Food Item using Cash */
	/** 
	 * @param itemCode The item code of the food item which is being bought
	 * @param amount The amount the user has entered
	 * @return The balance or -1 or -2
	 * <p>
   	 * set currBalance = currBalance + amount
   	 * <br>if currBalance > price, return (currBalance-price), and set balance = price, currBalance = 0
   	 * <br>if currBalance == price, return (0), and set balance = price, currBalance = 0
   	 * <br>if currBalance < price, return (-1)
   	 * <br>if item doesn't exist or Quantity is 0, return (-2) 
	 *
   	 */
	public double BuyItemWithCash(int itemCode, double amount) {
		double change, price;
		
		FoodStock stock = (FoodStock)itemList.get(itemCode); // Get the required Food Stock
	
		if (stock == null || stock.quantity == 0) {
			return -2;
		}
	
		currentBalance += amount;
		price = stock.item.getPrice();
		if (currentBalance >= price) {   	// Sufficient Amount entered
			change = currentBalance - price;
			holder.DepositMoney(price);
			currentBalance = 0;
			stock.quantity--;
			
			setChanged();
			notifyObservers(itemList);

			return change;
		}
		else {
			flag = -1; 		// Condition - entered Amount not sufficient
			return -1;
		}
	}
	
	/* 2. Cancel buy Operation */
	/**
	 * @param amount The Amount entered in the Vending Machine
	 * @return amount or change 
	 * <p>
	 * If amount is entered more than once to make it equal to the food item's price,
	 * <br> then set the change = currentBalance which is total amount entered for the transaction
	 * <br> and return change
	 * <br> Otherwise, return the amount entered   
	 */
	public double CancelBuyOperation(double amount){
		
		double change;
		
		if(flag == -1){			// entered Amount not sufficient to buy the Food Item
			flag = 0;					
			change = currentBalance;		// Return the entered amount
			currentBalance = 0;
			
			return change;
		}
		else{
			return amount;
		}
	}
	
	/* 3. Display Nutritional Info */
	/**
	 * @param itemCode Item Code of the Food Item whose Nutritional Info is required
	 * @return boolean value 
	 * <br>True - if stock is present     
	 * <br> False - if stock is not present
	 */
	public boolean DisplayNutritionalInfo(int itemCode){
		double calories;
		double sugar;
		double fats;
		String name;
		
		FoodStock stock = (FoodStock)itemList.get(itemCode); // Get the required Food Stock
		
		if(stock!=null){
			System.out.println("Nutritional Information for Food Item " + itemCode);
			name = stock.item.getDescription();  					// Call to the Method in FoodItem
			System.out.println("Description : " + name);
			calories = stock.item.GetCalories();
			System.out.println("No. of Calories : " + calories);
			sugar = stock.item.GetSugarContents();
			System.out.println("Sugar Contents : " + sugar);
			fats = stock.item.GetFatContents();
			System.out.println("Fat Contents : " + fats);
			return true;
		} 
		else{
			return false;
		}
	}
 
	/* 4. Change the Price of a FoodItem */
	/**
	 * @param itemCode item Code of the Food Item whose price is to be changed
	 * @param newPrice New Price to be set
	 * @return boolean value 
	 * <br> True - If price is changed 
	 * <br> False - if stock is not present
	 * <p> 
	 * <br>	After setting the price of the food item to new price, 
	 * <br> it marks this Observable object as having been changed and
	 * <br> notifies the Observers of the changes made.  
	 */
	public boolean ChangePrice(int itemCode, double newPrice){
		FoodStock stock = (FoodStock)itemList.get(itemCode);		// Get the required Food Stock
		
		if(stock!=null){
			stock.item.setPrice(newPrice); // Call to the Method in FoodItem
			setChanged(); 				// Indication of changes in the Observable VendingMachine
			notifyObservers(itemList);  // Notify the changes to the Observers
			return true;
		}
		else{
			return false;
		}
	}
	
	/* 5. Add new FoodItem */
	/**
	 * @param price Price of new Food Item
	 * @param description Description of the new Food Item
	 * @param calories Calorific value of new Food Item
	 * @param sugar Sugar contents of new Food Item - in gms
	 * @param fats Fat Contents of new Food Item - in gms
	 * @param quantity Quantity of new Food Item
	 * @return boolean value 
	 * <br> True - if new Food Item added in the Hashtable
	 * <br> False - if new Food Item not added in the Hashtable, because of some value already present at the given key.
	 *  
	 */
	public boolean AddFoodItem(double price, String description, double calories, 
							   double sugar, double fats, int quantity){
		maxItemCode++;    // Set the Item Code
		
		FoodItem item = new FoodItem(maxItemCode, price, description, calories, sugar, fats); // Create an instance of FoodItem
		FoodStock stock = new FoodStock(item, quantity);     // create an instance of FoodStock
		
		if (itemList.put(maxItemCode, stock) == null) {
			setChanged(); 					// Indication of changes in the Observable VendingMachine
			notifyObservers(itemList);		// Notify the changes to the Observers
			return true;
		} 
		else {
			return false;
		}
	}
	
	/* 6. Update the Quantity of a FoodItem */
	/**
	 * @param itemCode Item Code of the Food Item whose quantity is to be updated
	 * @param quantity New quantity
	 * @return boolean value 
	 * <br> True - If quantity updated 
	 * <br> False - If quantity not updated, because that food item is not present    
	 */
	public boolean UpdateQuantity(int itemCode, int quantity){
		FoodStock stock = (FoodStock)itemList.get(itemCode);		// Get the required Food Stock
		
		if(stock!=null){
			stock.quantity = quantity;			// Set the new Quantity
			return true;
		}
		else
			return false;
	}
	
	/* 7. Remove a FoodItem */
	/**
	 * @param itemCode Item Code of Food Item which is to be removed
	 * @return boolean value 
	 * <br> True - If Food Item removed 
	 * <br> False - If Food Item not removed, because that food item is not present
	 * 
	 */
	public boolean RemoveFoodItem(int itemCode){
		if (itemList.remove(itemCode) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/* 8. Get the Quantity of a FoodItem */
	/**
	 * @param itemCode Item Code of Food Item whose quantity is required
	 * @return quantity or -1
	 * <br> quantity - when the food item is present
	 * <br> -1 - when the food item is not present  
	 */
	public int GetQuantity(int itemCode) {
		FoodStock stock = (FoodStock)itemList.get(itemCode);		// Get the required Food Stock
		
		if(stock != null){
			return stock.quantity;
		}
		else {
			return -1;
		}
	}
	
	/* 9.Get the FoodItwem given an itemCode*/
	/**
	 * @param itemCode Item Code of the Food Item which is required
	 * @return FoodItem or null 
	 * <br> FoodItem - if item is present 
	 * <br> null - if item is not present
	 */
	public FoodItem GetFoodItem(int itemCode){
		FoodStock stock = (FoodStock)itemList.get(itemCode);		// Get the required Food Stock
		
		if(stock != null){
			return stock.item;
		}
		else{
			return null;
		}
	}
	
	/* 10. Writing to the XML File */
	/**
	 * @param writer An instance of {@link BufferedWriter} to write text to a character-output stream
	 * <p>
	 * <br> This method writes the vending Machine data to the database file in the XML format.
	 */
	public void WriteXMLToFile(BufferedWriter writer){
		Enumeration e = itemList.elements();
		String str;
		
		try{
			writer.write("<VendingMachine>\n");				// Writing to XML File
			while(e.hasMoreElements()) {
				FoodStock stock = (FoodStock)e.nextElement();
				FoodItem item = stock.item;
				str = "<FoodItem price=\"" + item.getPrice() + "\" description=\"" + item.getDescription() 
										   + "\" calorificValue=\"" + item.GetCalories() + "\" sugarContents=\"" + item.GetSugarContents() 
										   + "\" fatContents=\"" + item.GetFatContents() + "\" quantity=\"" + stock.quantity 
										   + "\" ></FoodItem>" + "\n";
				writer.write(str);
			}
			writer.write("</VendingMachine>");
		}
		catch(IOException e1){ 			// File I/O Exception
			e1.printStackTrace();
		}		
	}
	
	/*11. Get the List of all Food Stocks */
	/**
	 * @return itemList - List of all available food stocks in the Vending Machine 
	 */
	public Hashtable<Integer, FoodStock> getFoodStocks(){
		return itemList;
	}
	
	/*12. Get MoneyHolder */
	/**
	 * @return holder - The Money Holder in the Vending Machine
	 */
	public MoneyHolder getMoneyHolder(){
		return holder;
	}
}
