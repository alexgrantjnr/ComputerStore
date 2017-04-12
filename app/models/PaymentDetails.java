package models;


import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;
import play.data.format.*;
import play.data.validation.*;
import play.Logger;
import models.*;
import models.*;

@Entity
public class PaymentDetails extends Model {
    @Id
    public String holdersEmail;
    @Constraints.Required
    public String cardNumber;
    @Constraints.Required
    public String expiryDate;
    @Constraints.Required
    public String cvvNum;
    @Constraints.Required
    public String holdersName;
    public double total;


    public PaymentDetails(){
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvvNum() {
        return cvvNum;
    }

    public void setCvvNum(String cvvNum) {
        this.cvvNum = cvvNum;
    }

    public String getHoldersName() {
        return holdersName;
    }

    public void setHoldersName(String holdersName) {
        this.holdersName = holdersName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getHoldersEmail() {
        return holdersEmail;
    }

    public void setHoldersEmail(String holdersEmail) {
        this.holdersEmail = holdersEmail;
    }
}
