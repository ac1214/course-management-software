package coursesoftware.datatypes;

public class Admin {
    private String username;

    /**
     * Constructor for admin class
     *
     * @param username user name for the admin
     */
    public Admin(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String newUName) {
        this.username = newUName;
    }
}
