/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 	modified by group to work as grocery store
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

public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String id;
	private Calendar date;
	private double feePaid;
	private static final String MEMBER_STRING = "M";
	private List shoppingCart = new LinkedList();
	private List transactions = new ArrayList();

	/**
	 * Represents a single member
	 * 
	 * @param name
	 *            name of the member
	 * @param address
	 *            address of the member
	 * @param phone
	 *            phone number of the member
	 */

	// DONE ---------------------------------------------------------

	public Member(String name, String address, String phone, Calendar date,
			double feePaid) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.date = date;
		this.feePaid = feePaid;
		id = MEMBER_STRING + (MemberIdServer.instance()).getId();
	}

	/**
	 * Gets an iterator to a collection of selected ransactions
	 * 
	 * @param date
	 *            the date for which the transactions have to be retrieved
	 * @return the iterator to the collection
	 */

	public Iterator getTransactions(Calendar dateStart, Calendar dateEnd) {
		List result = new ArrayList();
		for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
			Transaction transaction = (Transaction) iterator.next();
			if (transaction.onDate(dateStart, dateEnd)) {
				result.add(transaction);
			}
		}
		return (result.iterator());
	}

	/**
	 * Return the productlist of the customer
	 * 
	 * @return
	 */
	public List getShoppingCart() {
		return shoppingCart;
	}

	/**
	 * Add an item to the shopping cart
	 * 
	 * @param item
	 */
	public void addToCart(Product item) {
		shoppingCart.add(item);
	}

	/**
	 * Store Transaction in user transaction list
	 * 
	 * @param item
	 */
	public void addTransaction(Transaction item) {
		boolean allowAdd = true;
		if (transactions.size() == 0) {
			transactions.add(item);
		} else {
			for (int i = 0; i < transactions.size(); i++) {
				Transaction hold = (Transaction) transactions.get(i);
				if(hold.getId() == item.getId())
				{
					allowAdd = false;
				}
			}
			if(allowAdd){
				transactions.add(item);
			}
		}

	}

	/**
	 * {
	 * 
	 * Getter for name
	 * 
	 * @return member name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for phone number
	 * 
	 * @return phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Getter for address
	 * 
	 * @return member address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Getter for id
	 * 
	 * @return member id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the date member joined
	 * 
	 * @return
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Gets the fee member paid
	 * 
	 * @return
	 */
	public double getFee() {
		return feePaid;
	}

	/**
	 * Setter for name
	 * 
	 * @param newName
	 *            member's new name
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Setter for address
	 * 
	 * @param newName
	 *            member's new address
	 */
	public void setAddress(String newAddress) {
		address = newAddress;
	}

	/**
	 * Setter for phone
	 * 
	 * @param newName
	 *            member's new phone
	 */
	public void setPhone(String newPhone) {
		phone = newPhone;
	}

	/**
	 * Sets the join date
	 * 
	 * @param date
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * Sets the fee paid
	 * 
	 * @param feePaid
	 */
	public void setFee(double feePaid) {
		this.feePaid = feePaid;
	}

	/**
	 * Checks whether the member is equal to the one with the given id
	 * 
	 * @param id
	 *            of the member who should be compared
	 * @return true iff the member ids match
	 */
	public boolean equals(String id) {
		return this.id.equals(id);
	}

	/**
	 * String form of the member
	 * 
	 */
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		String string = "Member Name: " + name + " \nAddress: " + address
				+ " \nID: " + id + "\nPhone: " + phone + "\nDate joined: "
				+ format.format(date.getTime()) + "\nFee Paid: " + feePaid;

		return string;
	}
}