package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class OrderItem extends Model {

    @Id
    @GeneratedValue
    public long orderItemId;
    @Constraints.Required
    public int quantity;
    @Constraints.Required
    public double totalPrice;
    @Constraints.Required
    public long orderId;
    @Constraints.Required
    public long productId;

    public OrderItem(long orderItemId, int quantity, double totalPrice, long orderId, long productId) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.productId = productId;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}

