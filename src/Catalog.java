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
 * The collection class for Product objects
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * Modified by Group
 * 
 */
public class Catalog implements Serializable {
	private static final long serialVersionUID = 1L;
	private List products = new ArrayList();
	private static Catalog catalog;
	

	/**
	 * Private constructor for singleton pattern
	 */
	private Catalog() {
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	  //DONE ---------------------------------------------------------

	public static Catalog instance() {
		if (catalog == null) {
			return (catalog = new Catalog());
		} else {
			return catalog;
		}
	}

	/**
	 * Checks whether a book with a given book id exists.
	 * 
	 * @param bookId
	 *            the id of the book
	 * @return true iff the book exists
	 * 
	 */
	  //DONE ---------------------------------------------------------

	public Product search(String name) {
		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			if (product.getName().equals(name)) {
				return product;
			}
		}
		return null;
	}
	/**
	 * Search by product id, instead of name
	 * @param id
	 * @return
	 */
	public Product searchById(int id) {
		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			if (product.getId().equals(id)) {
				return product;
			}
		}
		return null;
	}
	/**
	 * Inserts a book into the collection
	 * 
	 * @param book
	 *            the book to be inserted
	 * @return true iff the book could be inserted. Currently always true
	 */
	  //DONE ---------------------------------------------------------

	public boolean insertProduct(Product product) {
		Product productSearch = search(product.getName());
		if(productSearch == null){
		products.add(product);
		}
		else
		{
			return false;
		}
		return true;
	}

	/**
	 * Returns an iterator to all books
	 * 
	 * @return iterator to the collection
	 */
	  //DONE ---------------------------------------------------------

	public Product getProduct(String name) {
		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			if (product.getName().equals(name)) {
				return product;
			}
		}
		return null;
	}
	/**
	 * Gets a product from id
	 * @param id
	 * @return
	 */
	public Product getProduct(int id) {
		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			if (product.getId().equals(id)) {
				return product;
			}
		}
		return null;
	}
	/**
	 * Builds a string of all the products in the catalog
	 * Particulary for the browsing of the catalog
	 * @return
	 */
	public String displayProducts(){
		StringBuilder results = new StringBuilder();
		
		for (Iterator iterator = products.iterator(); iterator.hasNext();){
			Product product = (Product) iterator.next();
			results.append(product.toString());
		}
		return results.toString();
	}
	/**
	 * Supports serialization
	 * 
	 * @param output the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(catalog);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	/**
	 * Supports serialization
	 * 
	 * @param input the stream to be read from
	 */
	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (catalog != null) {
				return;
			} else {
				input.defaultReadObject();
				if (catalog == null) {
					catalog = (Catalog) input.readObject();
				} else {
					input.readObject();
				}
			}
		} catch (IOException ioe) {
			System.out.println("in Catalog readObject \n" + ioe);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * String form of the collection
	 * 
	 */
	public String toString() {
		return products.toString();
	}
}
