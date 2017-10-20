package com.example.contactfinder.model;


public class Contact {
    private long contactId;
	private String name;
	private String cellno;
	
	public Contact() {
	}

	public Contact(String name, String cellno) {
		this.name = name;
		this.cellno = cellno;
	}
	
	public long getID() {
		return contactId;
	}
	 public void setId(long contactId) {
		  this.contactId=contactId;
		  
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellNo() {
		return cellno;
	}
	public void setCellNo(String cellno) {
		this.cellno = cellno;
	}
}
