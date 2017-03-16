package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Product extends Model {


    @SequenceGenerator(name = "product_gen",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    public long productId;
    @Constraints.Required
    public String name;
    @Constraints.Required
    public int quantity;
    @Constraints.Required
    public double price;
    @Constraints.Required
    public String description;
    @Constraints.Required
    public byte[] productImage;

    //Query helper to find object with id long
    public static Finder<Long,Product> find = new Finder<>(Product.class);

    //Find all products
    public static List<Product> findAll(){
        return Product.find.all();
    }

    //Find products with specific name
    public static List<Product> findByName(String productName){
        return find.where().ilike("name","%" + productName + "%").findList();
    }

    public static void deleteProduct(Long productId){
        find.ref(productId).delete();
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }
}