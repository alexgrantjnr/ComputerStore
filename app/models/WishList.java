package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class WishList extends Model{

    @SequenceGenerator(name = "wishlist_gen",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishlist_gen")
    public long wishlistId;
    @Constraints.Required
    public int numItems;
    @Constraints.Required
    public long userId;
    @Constraints.Required
    public long productId;

    public WishList(long wishlistId, int numItems, long userId, long productId) {
        this.wishlistId = wishlistId;
        this.numItems = numItems;
        this.userId = userId;
        this.productId = productId;
    }

    public long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}


