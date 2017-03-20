package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class User extends Model{

    @SequenceGenerator(name = "user_gen",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    public long userId;
    @Constraints.Required
    public String firstName;
    @Constraints.Required
    public String lastName;
    @Constraints.Required
    public String role;
    @Constraints.Required
    public String email;
    @Constraints.Required
    public String password;
    @Constraints.Required
    public String age;
    @Constraints.Required
    public String phone,mobile;
    @Constraints.Required
    public byte[] profilePic;


    public User(long userId, String firstName, String lastName, String role, String email, String password, String age, String phone, String mobile, byte[] profilePic) {
        this.userId = userId;

        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.age = age;
        this.phone = phone;
        this.mobile = mobile;
        this.profilePic = profilePic;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }
  
    public static Finder<String, User> find = new Finder<String, User>(User.class);

    public static List<User> findAll() {
        return User.find.all();
    }

    public static User authenticate(String email, String password){
        return find.where().eq("email", email).eq("password", password).findUnique();
    }

    public static User getUserById(String id){
        if (id == null){
            return null;
        }else{
            return find.byId(id);
        }
    }
}


