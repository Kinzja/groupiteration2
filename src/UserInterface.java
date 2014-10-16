/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS"AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  
 */
import java.util.*;
import java.text.*;
import java.io.*;

/**
 * Project by Marshall Streeter, Michael Bryant, Jacob Kinzet, David Scluzasik
 * This class implements the user interface for the Library project. The
 * commands are encoded as integers using a number of static final variables. A
 * number of utility methods exist to make it easier to parse the input.
 * 
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));
	private static Store store;
	private static final int EXIT = 0;
	private static final int ENROLL_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int GET_MEMBER_INFO = 3;
	private static final int CHECKOUT = 4;
	private static final int ADD_PRODUCT = 5;
	private static final int GET_PRODUCT_INFO = 6;
	private static final int PROCESS_SHIPMENT = 7;
	private static final int CHANGE_PRICE = 8;
	private static final int PRINT_TRANSACTIONS = 9;
	private static final int SAVE = 10;
	private static final int RETRIEVE = 11;
	private static final int HELP = 12;
	private static final int ADD_TO_CART = 13;

	/*
	 * Private constructor
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and  use it?")) {
			retrieve();
		} else {
			store = Store.instance();
		}
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */

	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * Gets a token after prompting
	 * 
	 * @param prompt
	 *            - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */

	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 * 
	 * @param prompt
	 *            The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 * 
	 */

	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the string to a number
	 * 
	 * @param prompt
	 *            the string for prompting
	 * @return the integer corresponding to the string
	 * 
	 */

	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Prompts for a date and gets a date object
	 * 
	 * @param prompt
	 *            the prompt
	 * @return the data as a Calendar object
	 */

	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat
						.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard
	 * 
	 * @return a valid command
	 * 
	 */

	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("\nEnter command:\n"
						+ HELP + " for help"));
				if (value >= EXIT && value <= ADD_TO_CART) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Displays the help screen
	 * 
	 */

	public void help() {
		System.out
				.println("Enter a number between 0 and 13 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ENROLL_MEMBER + " To add a member");
		System.out.println(REMOVE_MEMBER + " To remove a member");
		System.out.println(GET_MEMBER_INFO + " To get member information");
		System.out.println(CHECKOUT + " To checkout");
		System.out.println(ADD_PRODUCT + " To add product");
		System.out.println(GET_PRODUCT_INFO + " To get product info");
		System.out.println(PROCESS_SHIPMENT + " To process shipment");
		System.out.println(CHANGE_PRICE + " To change price");
		System.out.println(PRINT_TRANSACTIONS + " To print transactions");
		System.out.println(SAVE + " To save data");
		System.out.println(RETRIEVE + " To retrieve");
		System.out.println(HELP + " For help");
		System.out.println(ADD_TO_CART + " To shop products");
	}

	/**
	 * Method to run addMember steps
	 * 
	 * @throws ParseException
	 */
	public void addMember() throws ParseException {
		String name = getToken("Enter member name");
		String address = getToken("Enter address");
		String phone = getToken("Enter phone");
		Calendar date = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		date.setTime(df
				.parse(getToken("Enter today's date (Enter as MM/DD/YYYY)")));
		double feePaid = Double.parseDouble(getToken("Enter fee paid"));
		Member result;
		result = store.addMember(name, address, phone, date, feePaid);
		if (result == null) {
			System.out.println("Could not add member");
		}
		System.out.println(result);
	}

	/**
	 * Method to run remove user steps
	 * 
	 * @throws ParseException
	 */
	public void removeMember() throws ParseException {
		String id = getToken("Enter member id");
		boolean result;
		result = store.removeMember(id);
		if (result == false) {
			System.out.println("Could not remove member");
		} else {
			System.out.println("Member Removed");
		}
	}

	/**
	 * Runs the steps to get member info
	 */
	private void getMemberInfo() {
		ArrayList result = new ArrayList();
		String name = getToken("Enter member name");
		result = store.getMemberInfo(name);
		if (result.size() != 0) {
			for (int i = 0; i < result.size(); i++) {
				String list = "\nMember Name: "
						+ ((Member) result.get(i)).getName() + "\nAddress: "
						+ ((Member) result.get(i)).getAddress() + "\nFeePaid: "
						+ ((Member) result.get(i)).getFee() + "\nID: "
						+ ((Member) result.get(i)).getId();
				System.out.println(list);
			}
		} else {
			System.out.println("Member doesent exist");
		}

	}

	/**
	 * Runs the steps to add product
	 */
	public void addProducts() {
		Product result;
		do {
			String name = getToken("Enter  product name");
			int stock = Integer.parseInt(getToken("Enter stock on hand"));
			double price = Double.parseDouble(getToken("Enter current price"));
			result = store.addProduct(name, stock, price);
			if (result != null) {
				System.out.println(result);
			} else {
				System.out.println("Product could not be added");
			}
			if (!yesOrNo("Add more products?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Get a products information
	 */
	public void getProduct() {
		Product result;
		String name = getToken("Enter product name ");
		result = store.getProduct(name);
		if (result != null) {
			System.out.println("Product Name: " + result.toString());
		}
	}

	/**
	 * Change a products price
	 */
	private void changePrice() {
		int id = Integer.parseInt(getToken("Enter the product id "));
		double price = Double.parseDouble(getToken("Enter the new price "));
		Product result = store.changePrice(id, price);
		System.out.println("Price changed for: " + result.getName()
				+ "\n New Price is: " + result.getPrice());

	}

	/**
	 * Gets a user's transactions
	 */
	public void getTransactions() {
		Iterator result;
		String memberID = getToken("Enter member id");
		Calendar dateStart = getDate("Please enter start date to search records as mm/dd/yy");
		Calendar dateEnd = getDate("Please enter end date of search as mm/dd/yy");
		result = store.getTransactions(memberID, dateStart, dateEnd);
		if (result == null) {
			System.out.println("Invalid Member ID");
		} else {
			while (result.hasNext()) {
				Transaction transaction = (Transaction) result.next();
				System.out.println(transaction.toString());
			}
			System.out.println("\n  There are no more transactions \n");
		}
	}

	/**
	 * Method to be called for saving the Library object. Uses the appropriate
	 * Library method for saving.
	 * 
	 */
	private void save() {
		if (store.save()) {
			System.out
					.println(" The store has been successfully saved in the file StoreData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Method to be called for retrieving saved data. Uses the appropriate
	 * Library method for retrieval.
	 * 
	 */
	private void retrieve() {
		try {
			Store tempLibrary = Store.retrieve();
			if (tempLibrary != null) {
				System.out
						.println(" The store has been successfully retrieved from the file StoreData \n");
				store = tempLibrary;
			} else {
				System.out.println("File doesnt exist; creating new library");
				store = Store.instance();
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * Check out a users cart
	 */
	private void checkout() {
		if (store.checkOut() != null) {
			System.out.println(store.checkOut());
		} else {
			System.out.println("Nothing in Cart");
		}
	}

	/**
	 * Process shipment add to inventory or make new product
	 */
	private void processShipment() {

		while (yesOrNo("Add Items to stock?")) {
			int id = Integer.parseInt(getToken("Enter the product id: "));
			int newStock = Integer.parseInt(getToken("Enter the new stock: "));
			Product result = store.getProduct(id);
			if (result != null) {
				result.setStock(newStock);
				System.out.println(result.toString());
			} else {
				System.out.println("Product is not stocked in the store.");
				if (yesOrNo("Would you like to add product?")) {
					addProducts();
				}

			}
		}
	}

	/**
	 * Browse the catalog to shop for products
	 */
	private void browseCatalog() {
		String member = getToken("Enter Member ID");
		if (store.searchMembership(member) != null)// if member exists display
													// catalog
		{
			store.searchMembership(member).getShoppingCart().clear();
			ArrayList<String> itemsSelected = new ArrayList<String>();
		
			do {
				System.out.println(store.shopProducts());// display current
															// products in the
															// catalog
				String product = getToken("Select product name to add to cart");
				if (!itemsSelected.contains(product)) {
					Double quantity = Double.parseDouble(getToken("How many?"));
					Product productItem = store.getProduct(product);
					productItem.setInCart(quantity);
					if (store.getProductToCart(product, quantity) != null) {
						System.out.println("Item added to cart");
					} else {
						System.out
								.println("Item not added, select yes to attempt again");
					}

					itemsSelected.add(product);
				} else {
					System.out.println("Item already in cart");
				}
			} while (yesOrNo("Add more to Cart?"));
		} else {
			System.out.println("Member not recognized");
		}
	}

	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalties.
	 * 
	 * @throws ParseException
	 * 
	 */
	public void process() throws ParseException {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case ENROLL_MEMBER:
				addMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case GET_MEMBER_INFO:
				getMemberInfo();
				break;
			case CHECKOUT:
				checkout();
				break;
			case ADD_PRODUCT:
				addProducts();
				break;
			case ADD_TO_CART:
				browseCatalog();
				break;
			case GET_PRODUCT_INFO:
				getProduct();
				break;
			case PROCESS_SHIPMENT:
				processShipment();
				break;
			case CHANGE_PRICE:
				changePrice();
				break;
			case PRINT_TRANSACTIONS:
				getTransactions();
				break;
			case SAVE:
				save();
				break;
			case RETRIEVE:
				retrieve();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	/**
	 * The method to start the application. Simply calls process().
	 * 
	 * @param args
	 *            not used
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		UserInterface.instance().process();
	}
}