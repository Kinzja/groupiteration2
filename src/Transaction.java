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
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

/**
 * Represents a single Transaction (issue, renew, etc.)
 * 
 * @author Brahma Dathan Modified by Marshall Streeter
 * 
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private double amount;
	private Calendar date;
	private String productsReceipt;
	private int id; 
	private List transactionProducts = new ArrayList();

	/**
	 * Creates the transaction with a given type and book title. The date is the
	 * current date.
	 * 
	 * @param type
	 *            The type of transaction
	 * @param title
	 *            The title of the book
	 * 
	 */
	public Transaction(String title) {
		this.title = title;
		date = new GregorianCalendar();
		date.setTimeInMillis(System.currentTimeMillis());
		this.id = TransactionIdServer.instance().getId();
	}
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	/**
	 * gets id
	 * @return
	 */
	public int getId(){
		return id;
	}
	/**
	 * Checks whether this transaction is on the given date
	 * 
	 * @param date
	 *            The date for which transactions are being sought
	 * @return true iff the dates match
	 */
	public boolean onDate(Calendar dateStart, Calendar dateEnd) {

		int startYear = dateStart.get(Calendar.YEAR);
		int endYear = dateEnd.get(Calendar.YEAR);
		int startMonth = dateStart.get(Calendar.MONTH);
		int endMonth = dateEnd.get(Calendar.MONTH);
		int startDay = dateStart.get(Calendar.DAY_OF_MONTH);
		int endDay = dateEnd.get(Calendar.DAY_OF_MONTH);
		int currentYear = date.get(Calendar.YEAR);
		int currentMonth = date.get(Calendar.MONTH);
		int currentDay = date.get(Calendar.DAY_OF_MONTH);

		if (startYear <= currentYear && endYear >= currentYear) {
			if (startYear < currentYear && endYear > currentYear) {
				return true; // if for sure in middle range of years, can skip
								// further checking
			}
			// reaches here if range is only within same year
			if (startMonth <= currentMonth && endMonth >= currentMonth) {
				if (startMonth < currentMonth && endMonth > currentMonth) {
					return true;
				}
				// reaches here if range is equal to current month
				if (startDay <= currentDay && endDay >= currentDay) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Serch for the product in the transaction
	 * 
	 * @param product
	 * @return
	 */
	public boolean search(Product product) {
		for (Iterator iterator = transactionProducts.iterator(); iterator
				.hasNext();) {
			Product item = (Product) iterator.next();
			if (item.getName().equals(product.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds product to the transaction
	 * 
	 * @param object
	 * @param cartLength
	 */
	public void addProduct(Object object) {

		Product item = (Product) object;
		if(search(item))
		{
			
		}else{
			transactionProducts.add(item);
		}

	}
	/**
	 * Gets transactionlist
	 * @return
	 */
	public List getTransactionProducts(){
		return transactionProducts;
	}
	/**
	 * Calculates total transaction amount stored in amount and used in receipt
	 */
	private void transactionAmount() {

		for (int z = 0; z <= transactionProducts.size() - 1; z++) {
			Product item = (Product) transactionProducts.get(z);
				if(item.getName().equals(item.getName())){
					amount += (item.getPrice()* item.getInCart());
				}
				
		
		}

	}

	/**
	 * Generates the receipt to send back to UI
	 * 
	 * @return
	 */
	public String returnReceipt() {
		transactionAmount();
		StringBuilder receipt = new StringBuilder();
		
		for (int i = 0; i <= transactionProducts.size() - 1; i++) {
			Product item = (Product) transactionProducts.get(i);
			receipt.append(item.receiptString());
			receipt.append("\nItem Total: " + item.getInCart()
					* item.getPrice());
			receipt.append(System.lineSeparator());

		}

		receipt.append("Amount: " + amount);
		
		return receipt.toString();
	}

	/**
	 * Returns the title field
	 * 
	 * @return title field
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets amount
	 * 
	 * @return
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Returns the date as a String
	 * 
	 * @return date with month, date, and year
	 */
	public String getDate() {
		return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/"
				+ date.get(Calendar.YEAR);
	}
	private String productListToString(){
		StringBuilder string = new StringBuilder();
		for(int i = 0 ; i < transactionProducts.size(); i++){
			Product item = (Product) transactionProducts.get(i);
			string.append(item.getName());
			string.append(System.lineSeparator());
		}
		return string.toString();
	}
	/**
	 * String form of the transaction
	 * 
	 */
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		return (title + "Date: " + format.format(date.getTime())+"\nAmount: "+amount
				+ "\nProducts: " + productListToString() + "\n-----------------------------------");
	}
}