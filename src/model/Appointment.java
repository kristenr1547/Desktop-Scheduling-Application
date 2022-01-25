package model;

import query.ContactQuery;
import query.CustomerQuery;
import query.UserQuery;

import java.time.LocalDateTime;

public class Appointment {
    private int apptID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerID;
    private int contactID;
    private Contact contact;
    private int userID;
    private User user;
    private Customer customer;

    public Appointment(int apptID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime,
                       int customerID, int contactID, int userID) {
        this.apptID = apptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.contactID = contactID;
        this.userID = userID;
        this.contact = ContactQuery.createContactByID(contactID);
        this.user = UserQuery.createUserByID(userID);
        this.customer = CustomerQuery.createCustomerbyID(customerID);
    }

    public int getApptID() {
        return apptID;
    }

    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Contact getContact() {
        return contact;
    }

    public User getUser() {
        return user;
    }

    public Customer getCustomer() {
        return customer;
    }
}
