
package vm;
 
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * 
 * @author Rutuja
 * <p>
 * This class parses the database file in XML format using DOM hierarchy.
 * It creates the Vending Machine instance and puts in it the data structures 
 * from the parsed data. 
 */
public class DataParser{
	
	// Method to get the instance of stocked VendingMachine
	/**
	 * @param fileName Name of the Input File to be parsed
	 * @return machine - Vending Machine instance
	 * <p>
	 * <br> This method creates an instance of DOM parser,
	 * <br> parses the entire input file in the XML document variable 'doc',
	 * <br> which is the root of the document tree and provides the primary 
	 * <br> access to the document's data. Then it creates an instance of 
	 * <br> Vending Machine and loads the parsed database in the machine instance.
	 * 
	 */
	public VendingMachine getVendingMachine(String fileName){
		NodeList children;
		Node node;
		int i, type, flag = 0;
		boolean ok, ok1, ok2;

		// Instantiate the DOM Parser
		DocumentBuilder parser;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			parser = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e){
			e.printStackTrace();
			return null;
			
		}
		
		Document doc; 		// Variable for entire XML document;
		try{
			doc = parser.parse(fileName); 	// Parse the file in the doc
		}
		catch(SAXException e){
			e.printStackTrace();
			return null;
			
		}
		catch(IOException e){				// File I/O Exception
			System.err.println("Error in reading File !\n");
			e.printStackTrace();
			return null;
		}
	
		// Create an instance of VendingMachine
		VendingMachine machine = new VendingMachine();
		
		// File Parsing
		node = doc;
		if(node == null){
			return null;
		}
		
		children = node.getChildNodes();

		while(children!=null && flag == 0){
			for(i=0; i < children.getLength(); i++){
				node = children.item(i);
				type = node.getNodeType();

				if(type == Node.TEXT_NODE){
					if(i == (children.getLength())-1){
						flag =1; 						// Last text_node
					}
					continue;
				}
				else if(type == Node.DOCUMENT_NODE){
					continue;
				}
				else { 									// Type = node.Element_type
					ok = node.hasChildNodes();
					if(ok){
						children = node.getChildNodes();
						break;
					}
					else{
						ok1 = node.hasAttributes(); 		// Check whether the node has any attributes

						if(ok1){
							NamedNodeMap attrList = node.getAttributes();
																				// Get the Node Attributes
							Node price = attrList.getNamedItem("price");   
							Node description = attrList.getNamedItem("description");
							Node calories = attrList.getNamedItem("calorificValue");
							Node sugar = attrList.getNamedItem("sugarContents");
							Node fats = attrList.getNamedItem("fatContents");
							Node quant = attrList.getNamedItem("quantity");

							// Add the Food Item in the VendingMachine
							ok2 = machine.AddFoodItem(Double.valueOf(price.getNodeValue()), description.getNodeValue(), 
									Double.valueOf(calories.getNodeValue()), Double.valueOf(sugar.getNodeValue()), 
									Double.valueOf(fats.getNodeValue()), Integer.valueOf(quant.getNodeValue()));
							
							if(!ok2){
								System.out.println("Error in Adding the Food Items in the Vending Machine !");
							}
						}
					}
				}
			}			
		}				

		return machine;
	}
	
	public static void main(String[] args){
		DataParser parser = new DataParser();
		VendingMachine m1 = parser.getVendingMachine("MachineStock.xml");
		if(m1 != null){
			System.out.println("Loaded the Machine Successfully");
		}
	}
}

