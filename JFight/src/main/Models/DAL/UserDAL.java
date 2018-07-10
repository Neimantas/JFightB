package Models.DAL;

public class UserDAL {

    private long UserId;
    private String UserName;
    private String Password;
    private String Email;
    private int AccessLevel;

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getAccessLevel() {
        return AccessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        AccessLevel = accessLevel;
    }
}
