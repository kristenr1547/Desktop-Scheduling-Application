package model;

/**
 * Creates user objects that assist with logging in and alerting if there is an appointment in 15 minutes.
 * It relates to the user table in the database.
 */
public class User {
    private int userId;
    private String userName;
    private String password;

    /**
     *
     * @param userId Unique identifier given from the database.
     * @param userName username used for authenticating.
     * @param password password is used for authenticating.
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     *
     * @return String to be displayed in a combobox in a legible manner.
     */
    @Override
    public String toString() {
        return userId + " " + userName;
    }
}
