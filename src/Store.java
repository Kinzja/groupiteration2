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
import java.io.*;

public class Store implements Serializable {
	private Catalog catalog;
	private MemberList memberList;
	private static Store store;
	private String currentMember;
	private Transaction transactionCheckOut; // to hold record to current
												// transaction to send products

	/*
	 * Private for the singleton pattern Creates the catalog and member
	 * collection objects
	 */
	// DONE ---------------------------------------------------------
	private Store() {
		catalog = Catalog.instance();
		memberList = MemberList.instance();
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	// DONE ---------------------------------------------------------
	public static Store instance() {
		if (store == null) {
			MemberIdServer.instance(); // instantiate all singletons
			return (store = new Store());
		} else {
			return store;
		}
	}

	/**
	 * Organizes the operations for adding a book
	 * 
	 * @param title
	 *            book title
	 * @param author
	 *            author name
	 * @param id
	 *            book id
	 * @return the Book object created
	 */
	// DONE ---------------------------------------------------------

	public Product addProduct(String name, int stock, double price) {
		Product product = new Product(name, stock, price);
		if (catalog.insertProduct(product)) {
			return (product);
		}
		System.out.println("ProductList already contains that product");
		return null;
	}

	/**
	 * Used to send display products string to UI
	 * 
	 * @return
	 */
	public String shopProducts() {
		return catalog.displayProducts();
	}

	/**
	 * Organizes the operations for adding a member
	 * 
	 * @param name
	 *            member name
	 * @param address
	 *            member address
	 * @param phone
	 *            member phone
	 * @return the Member object created
	 */
	// DONE ---------------------------------------------------------

	public Member addMember(String name, String address, String phone,
			Calendar date, double feePaid) {
		Member member = new Member(name, address, phone, date, feePaid);
		if (memberList.insertMember(member)) {
			return (member);
		}
		return null;
	}

	// DONE ---------------------------------------------------------
	/**
	 * Removes member based of id
	 * 
	 * @param id
	 * @return
	 */
	public boolean removeMember(String id) {

		if (memberList.removeMember(id) == true) {
			return true;
		}
		return false;
	}

	// DONE ---------------------------------------------------------
	/**
	 * Gets member info based off name
	 * 
	 * @param name
	 * @return
	 */
	public ArrayList getMemberInfo(String name) {
		return memberList.getMemberInfo(name);
	}

	/**
	 * Searches for a given member
	 * 
	 * @param memberId
	 *            id of the member
	 * @return true iff the member is in the member list collection
	 */
	// DONE ---------------------------------------------------------

	public Member searchMembership(String memberId) {
		Member holder = memberList.search(memberId);

		if (holder != null) {
			currentMember = holder.getId();
		}
		return holder;
	}

	/**
	 * Changes the price of a product, return product to UI for display of
	 * change
	 * 
	 * @param id
	 * @param price
	 * @return
	 */
	public Product changePrice(int id, double price) {
		Product product = catalog.searchById(id);
		product.setPrice(price);
		return product;
	}

	/**
	 * Returns an iterator to the transactions for a specific member on a
	 * certain date
	 * 
	 * @param memberId
	 *            member id
	 * @param date
	 *            date of issue
	 * @return iterator to the collection
	 */

	public Iterator getTransactions(String memberId, Calendar dateStart,
			Calendar dateEnd) {
		Member member = memberList.search(memberId);
		if (member == null) {
			return (null);
		}
		return member.getTransactions(dateStart, dateEnd);
	}

	/**
	 * Gets the product based on id
	 * 
	 * @param id
	 * @return
	 */
	public Product getProduct(int id) {

		return catalog.getProduct(id);
	}

	/**
	 * Returns the transaction receipt UI after adding products to transaction
	 * 
	 * @return
	 */
	public String checkOut() {
		transactionCheckOut = new Transaction("Purchase Receipt");
		transactionCheckOut.setAmount(0);
		transactionCheckOut.getTransactionProducts().clear();
		List products = memberList.search(currentMember).getShoppingCart();
		for (int i = 0; i <= products.size() - 1; i++) {
			transactionCheckOut.addProduct(products.get(i));
		}
		memberList.search(currentMember).addTransaction(transactionCheckOut);
		return transactionCheckOut.returnReceipt();
	}

	// DONE ---------------------------------------------------------
	/**
	 * Gets product based off name
	 * 
	 * @param name
	 * @return
	 */
	public Product getProduct(String name) {
		Product product = catalog.getProduct(name);
		if (product == null) {
			System.out.println("Product is not in the list");
		}
		return product;
	}

	/**
	 * Sends the product to the cart based off name
	 * 
	 * @param name
	 * @return
	 */
	public Product getProductToCart(String name, double quanity) {
		Product product = catalog.getProduct(name);
		int total = -(int)quanity;
		if (product == null) {
			return null;
		} else {
			catalog.getProduct(name).setStock(total);
			memberList.search(currentMember).addToCart(product);
			return product;
		}
	}

	/**
	 * Retrieves a deserialized version of the library from disk
	 * 
	 * @return a Library object
	 */
	public static Store retrieve() {
		try {
			FileInputStream file = new FileInputStream("StoreData");
			ObjectInputStream input = new ObjectInputStream(file);
			input.readObject();
			MemberIdServer.retrieve(input);
			return store;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	/**
	 * Serializes the Library object
	 * 
	 * @return true iff the data could be saved
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("StoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(store);
			output.writeObject(MemberIdServer.instance());
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Writes the object to the output stream
	 * 
	 * @param output
	 *            the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(store);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	/**
	 * Reads the object from a given stream
	 * 
	 * @param input
	 *            the stream to be read
	 */
	private void readObject(java.io.ObjectInputStream input) {
		try {
			input.defaultReadObject();
			if (store == null) {
				store = (Store) input.readObject();
			} else {
				input.readObject();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * String form of the library
	 * 
	 */

	// DONE ---------------------------------------------------------

	@Override
	public String toString() {
		return catalog + "\n" + memberList;
	}

}