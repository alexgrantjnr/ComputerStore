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

    @SequenceGenerator(name = "payment_gen",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_gen")
    public long paymentId;
    public String holdersEmail;
    @Constraints.Required
    public String cardNumber;
    @Constraints.Required
    public String expiryDate;
    @Constraints.Required
    public String cvvNum;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date paymentDate = new Date();
    @Constraints.Required
    public String holdersName;
    public double total;

    public PaymentDetails(){
    }

    public static Finder<String,PaymentDetails> find = new Finder<>(PaymentDetails.class);

    public static List<PaymentDetails> findAll() {
    return PaymentDetails.find.orderBy("holdersEmail").findList();
    }

    public static List<PaymentDetails> findByUser(String email){
        return find.where().eq("holdersEmail",email).findList();
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

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
