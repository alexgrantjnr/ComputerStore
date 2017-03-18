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

public class HomeController extends Controller {

    private FormFactory formFactory;


    @Inject
    public HomeController(FormFactory f){
    this.formFactory = f;
    }

    public Result index() {

        return ok(index.render());
    }

    public Result account() {

        return ok(account.render());
    }

    public Result payment() {
        return ok(payment.render());
    }

    public Result product(Long productId) {
        Product prod = Product.getProductById(productId);
        return ok(product.render(prod));
    }

    public Result searchProduct(String productName) {
        List<Product>products = Product.findByName(productName);
        if (products.isEmpty()){
            List<Product>productsAll = Product.findAll();
            return ok(search.render(productsAll));
        }
        return ok(search.render(products));
    }

    public Result cart() {
        return ok(cart.render());
    }

    public Result wishlist() {
        return ok(wishlist.render());
    }

}
