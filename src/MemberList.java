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

public class MemberList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List members = new ArrayList();
	private static MemberList memberList;

	/**
	 * Private constructor for singleton pattern
	 */
	private MemberList() {
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	  //DONE ---------------------------------------------------------

	public static MemberList instance() {
		if (memberList == null) {
			return (memberList = new MemberList());
		} else {
			return memberList;
		}
	}

	/**
	 * Checks whether a member with a given member id exists.
	 * 
	 * @param memberId
	 *            the id of the member
	 * @return true iff member exists
	 * 
	 */
	  //DONE ---------------------------------------------------------
	/**
	 * Search member based off member id
	 * @param memberId
	 * @return
	 */
	public Member search(String memberId) {
		for (Iterator iterator = members.iterator(); iterator.hasNext();) {
			Member member = (Member) iterator.next();
			if (member.getId().equals(memberId)) {
				return member;
			}
		}
		return null;
	}

	/**
	 * Inserts a member into the collection
	 * 
	 * @param member
	 *            the member to be inserted
	 * @return true iff the member could be inserted. Currently always true
	 */
	  //DONE ---------------------------------------------------------
	/**
	 * Insert member, true if successful
	 * @param member
	 * @return
	 */
	public boolean insertMember(Member member) {
		members.add(member);
		return true;
	}
	
	  //DONE ---------------------------------------------------------
	/**
	 * Returns the member information
	 * @param name
	 * @return
	 */
	public ArrayList getMemberInfo(String name) {
		ArrayList resultList = new ArrayList();
		for (Iterator iterator = members.iterator(); iterator.hasNext();) {
			Member member = (Member) iterator.next();
			if (member.getName().equals(name)) {
				resultList.add(member);
			}

		}
		return resultList;
	}

	  //DONE ---------------------------------------------------------
	/**
	 * Removes the member from member list
	 * @param id
	 * @return
	 */
	public boolean removeMember(String id) {
		Member memberToRemove = search(id);
		if(members.remove(memberToRemove) == true){
			return true;
		}
		return false;
	}

	/**
	 * Supports serialization
	 * 
	 * @param output the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(memberList);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Supports serialization
	 * 
	 * @param input the stream to be read from
	 */
	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (memberList != null) {
				return;
			} else {
				input.defaultReadObject();
				if (memberList == null) {
					memberList = (MemberList) input.readObject();
				} else {
					input.readObject();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * String form of the collection
	 * 
	 */
	@Override
	public String toString() {
		return members.toString();
	}
}