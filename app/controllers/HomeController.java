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

    public Result adminPanel() {

        //Create a new Form object of type product, which will be passed to the view
        Form<Product> addProductForm = formFactory.form(Product.class);

        return ok(adminpanel.render(addProductForm));
    }

    //Add a Product to the database
    public Result addProductSubmit(){
        //Create a Form object which takes the form passed from the view
        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();

        //If the form has errors return a bad request
        if (newProduct.hasErrors()){
            return badRequest(adminpanel.render(null));
        }
        //Making a new object of type Product and assigning the variables from the form to the object
        Product newProd = newProduct.get();

        //Persisting the object to the database
        newProd.save();

        //Redirect to the admin panel
        return redirect(controllers.routes.HomeController.adminPanel());

    }

    public Result payment() {
        return ok(payment.render());
    }

    public Result product() {
        return ok(product.render());
    }

    public Result search() {



        return ok(search.render());
    }

    public Result cart() {
        return ok(cart.render());
    }

    public Result wishlist() {
        return ok(wishlist.render());
    }

}
