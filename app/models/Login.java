package models;

public class Login{
    public String email;
    public String password;

    //Validate method - invoked during error checking
    //after form based on a Login object has been submited

    public String validate () {
        if (User.authenticate(getEmail(), getPassword()) == null) {
            return "Invalid user or password";
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}