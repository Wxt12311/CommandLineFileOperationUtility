package com;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressBook implements Serializable {
    private static final long serialVersionUID = 1L;

    private String owner;
    private String fileName;
    private List<Contact> contacts = new ArrayList<>();

    public AddressBook(String owner, String fileName) {
        this.owner = owner;
        this.fileName = fileName;
    }

    // Getter & Setter
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public List<Contact> getContacts() { return contacts; }
    public void setContacts(List<Contact> contacts) { this.contacts = contacts; }
}