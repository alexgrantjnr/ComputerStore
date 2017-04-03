package controllers;


import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;
import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import views.html.*;
import models.*;
import models.Basket;

public class ShoppingController extends Controller{

    @Transactional
    public Result addToBasket(Long id){

        Product p = Product.find.byId(id);

        User user = getUserFromSession();

        if (user.getBasket() == null){
            user.setBasket(new Basket());
            user.getBasket().setUser(user);
            user.update();
        }
        user.getBasket().addProduct(p);
        user.update();

        return ok(cart.render(getUserFromSession()));
    }

    // Empty Basket
    @Transactional
    public Result emptyBasket() {

        User c = getUserFromSession();
        c.getBasket().removeAllItems();
        c.getBasket().update();

        return ok(cart.render(c));
    }

    // View an individual order
    @Transactional
    public Result viewOrder(long id) {
        ShopOrder order = ShopOrder.find.byId(id);
        return ok(cart.render(getUserFromSession()));
    }

    @Transactional
    public Result addOne(Long itemId) {

        // Get the order item
        OrderItem item = OrderItem.find.byId(itemId);
        // Increment quantity
        item.increaseQty();
        // Save
        item.update();
        // Show updated basket
        return redirect(routes.HomeController.cart());
    }

    @Transactional
    public Result removeOne(Long itemId) {

        // Get the order item
        OrderItem item = OrderItem.find.byId(itemId);
        // Get user
        User c = getUserFromSession();
        // Call basket remove item method
        c.getBasket().removeItem(item);
        c.getBasket().update();
        // back to basket
        return ok(cart.render(c));
    }


    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }
}
