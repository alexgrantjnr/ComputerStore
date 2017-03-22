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
        return ok(index.render(getUserFromSession()));
    }

    public Result register(){
        Form<User> addUserForm = formFactory.form(User.class);
        return ok(register.render(addUserForm,getUserFromSession()));
    }

    public Result registerSubmit(){

    }

    @Security.Authenticated(Secured.class)
    public Result account() {
        return ok(account.render(getUserFromSession()));
    }

    @Security.Authenticated(Secured.class)
    public Result payment() {
        return ok(payment.render(getUserFromSession()));
    }

    public Result product(Long productId) {
        Product prod = Product.getProductById(productId);
        return ok(product.render(prod,getUserFromSession()));
    }

    public Result searchProduct(String productName) {
        List<Product>products = Product.findByName(productName);
        if (products.isEmpty()){
            List<Product>productsAll = Product.findAll();
            return ok(search.render(productsAll,getUserFromSession()));
        }
        return ok(search.render(products,getUserFromSession()));
    }

    @Security.Authenticated(Secured.class)
    public Result cart() {
        return ok(cart.render(getUserFromSession()));
    }

    @Security.Authenticated(Secured.class)
    public Result wishlist() {
        return ok(wishlist.render(getUserFromSession()));
    }

    private User getUserFromSession(){
        return User.getUserById(session().get("email"));
    }
}
