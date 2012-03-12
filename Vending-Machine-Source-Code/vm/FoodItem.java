package vm;

/**
 * @author Rutuja.
 * <p> This class defines a FoodItem. 
 * It implements the {@link NutritionalInfo} interface.
 */
public class FoodItem implements NutritionalInfo{
	private int itemCode;
	private double price;
	private String description;
	private double calorificValue;
	private double sugarContents;
	private double fatContents;
	
	public FoodItem(){
		itemCode = 0;
		price = 0.0;
		description = null;
		calorificValue = 0.0;
		sugarContents = 0.0;
		fatContents = 0.0;
	}
	
	/**
	 * 
	 * @param itemCode Itemcode of food item
	 * @param price Price of food item
	 * @param description Description of food item
	 * @param calorificValue Calorific value
	 * @param sugarContents Sugar contents in gms
	 * @param fatContents Fat contents in gms
	 */
	public FoodItem(int itemCode, double price, String description, double calorificValue, double sugarContents, double fatContents ){
		this.itemCode = itemCode;
		this.price = price;
		this.description = description;
		this.calorificValue = calorificValue;
		this.sugarContents = sugarContents;
		this.fatContents = fatContents;
	}

	/**
	 * 
	 * @return Itemcode of this food item
	 */
	public int getItemCode(){
		return this.itemCode;
	}
	/**
	 * 
	 * @return Price of this food item
	 */
	public double getPrice(){
		return this.price;
	}

	/**
	 * 
	 * @return Description of food item
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * 
	 * @param itemCode The itemcode for this food item
	 */
	public void setItemCode(int itemCode){
		this.itemCode = itemCode;
	}
	/**
	 * 
	 * @param amount The price for this food item
	 */
	public void setPrice(double amount){
		this.price = amount;
	}
	/**
	 * 
	 * @param description The description for this food item
	 */
	public void setDescription(String description){
		this.description = description;
	}
	/**
	 * 
	 * @param calories The calorific value of this food item
	 */
	public void setCalorificValue(double calories){
		this.calorificValue = calories;
	}
	/**
	 * 
	 * @param sugar The sugar contents of this food item in gms
	 */
	public void setSugarContents(double sugar){
		this.sugarContents = sugar;
	}
	/**
	 * 
	 * @param fats The fat content of this food item in gms
	 */
	public void setFatContents(double fats){
		this.fatContents = fats;
	}
	/**
	 * @return The calorific value
	 */
	public double GetCalories(){
		return this.calorificValue;
	}
	/**
	 * @return The sugar contents in gms
	 */
	public double GetSugarContents(){
		return this.sugarContents;
	}
	/**
	 * @return The fat contents in gms
	 */
	
	public double GetFatContents(){
		return this.fatContents;
	}
}

/**
 * 
 * @author Rutuja
 * This interface declares the methods for 
 * accessing the nutritional information of a food item.
 * The {@link FoodItem} class implements this interface.
 */
interface NutritionalInfo{
	public double GetCalories();
	public double GetSugarContents();
	public double GetFatContents();
}