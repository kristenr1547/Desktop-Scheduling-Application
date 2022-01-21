package model;

public class Contact {


    private String name;
    private int contactID;

    public Contact(String name, int contactID) {
        this.name = name;
        this.contactID = contactID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    @Override
    public String toString() {
        return "ID: " + contactID + " " + name;
    }
}
