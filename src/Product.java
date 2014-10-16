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
import java.lang.*;
import java.io.*;

/**
 * Represents a single book
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * 
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int stock;
	private double price;
	private int id;
	private double inCart = 0; // variable to keep track of individual totals in
							// cart for receipt print out

	/**
	 * Creates a book with the given id, title, and author name
	 * 
	 * @param title
	 *            book title
	 * @param author
	 *            author name
	 * @param id
	 *            book id
	 */
	// DONE ---------------------------------------------------------

	public Product(String name, int stock, double price) {
		this.name = name;
		this.stock = stock;
		this.price = price;
		this.id = ProductIdServer.instance().getId();
	}
	public Product(String name, double price)
	{
		this.name = name;
		this.price = price;
	}
	/**
	 * Retuns the # of this item in a cart
	 * @return
	 */
	public double getInCart() {
		return inCart;
	}
	/**
	 * Sets the number of item in cart
	 * @param inCart
	 */
	public void setInCart(double inCart) {
		this.inCart = inCart;
	}

	/**
	 * Getter for author
	 * 
	 * @return author name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter for title
	 * 
	 * @return title of the book
	 */
	public int getStock() {
		return stock;
	}
	/**
	 * Updates the stock in association with processing shipment
	 * @param quantity
	 */
	public void setStock(int quantity){
		stock += quantity;
	}

	/**
	 * Getter for id
	 * 
	 * @return id of the book
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Returns object id
	 * @return
	 */
	public Object getId() {
		return id;
	}
	/**
	 * sets the price
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * String to display on receipts, uses the inCart total
	 * @return
	 */
	public String receiptString() {
		return "\nName " + name + " " + " \nPrice " + price + "\nQuantity: "
				+ inCart;
	}

	/**
	 * String form of the product
	 * Should be used for getting information, not receipts
	 * 
	 */
	public String toString() {
		return "\nName " + name + " " + "\nStock " + stock + " \nPrice "
				+ price + "\nId:" + id;
	}


}