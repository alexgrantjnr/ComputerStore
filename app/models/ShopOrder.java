package models;

import java.util.*;
import javax.persistence.*;
import java.util.Date;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;


// ShopOrder entity managed by Ebean
@Entity
public class ShopOrder extends Model {

    @Id
    private Long id;

    private Date OrderDate;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    private User user;

    // Default constructor
    public  ShopOrder() {
        OrderDate = new Date();
    }

    public double getOrderTotal() {

        double total = 0;

        for (OrderItem i: items) {
            total += i.getItemTotal();
        }
        return total;
    }

    //Generic query helper
    public static Finder<Long,ShopOrder> find = new Finder<Long,ShopOrder>(ShopOrder.class);

    //Find all Products in the database
    public static List<ShopOrder> findAll() {
        return ShopOrder.find.all();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}