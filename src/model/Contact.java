package model;

/**
 * Creates contact objects that are coordinated with the MYSQL database contact table.
 */
public class Contact {


    private String name;
    private int contactID;

    /**
     *
     * @param name The contact's name.
     * @param contactID The contact's unique identifier.
     */
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

    /**
     *
     * @return String that is easier to comprehend than the given 'toString' method.
     */
    @Override
    public String toString() {
        return "ID: " + contactID + " " + name;
    }
}
