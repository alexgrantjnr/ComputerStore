package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Order extends Model{

    @Id
    @GeneratedValue
    public long orderId;
    @Constraints.Required
    public String date;
    @Constraints.Required
    public String dispatched;
    @Constraints.Required
    public String userId;

    public Order(long orderId, String date, String dispatched, String userId) {
        this.orderId = orderId;
        this.date = date;
        this.dispatched = dispatched;
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDispatched() {
        return dispatched;
    }

    public void setDispatched(String dispatched) {
        this.dispatched = dispatched;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


