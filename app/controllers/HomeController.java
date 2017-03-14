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
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);
        return ok(index.render(searchProductForm));
    }

    public Result account() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);
        return ok(account.render(searchProductForm));
    }

    public Result adminPanel() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);
        //Create a new Form object of type product, which will be passed to the view
        Form<Product> addProductForm = formFactory.form(Product.class);

        return ok(adminpanel.render(addProductForm,searchProductForm));
    }

    //Add a Product to the database
    public Result addProductSubmit(){
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);
        //Create a new Form object of type product, which will be passed to the view
        Form<Product> addProductForm = formFactory.form(Product.class);
        //Create a Form object which takes the form passed from the view
        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();
        //If the form has errors return a bad request
        if (newProduct.hasErrors()){
            return badRequest(adminpanel.render(addProductForm,searchProductForm));
        }
        //Making a new object of type Product and assigning the variables from the form to the object
        Product newProd = newProduct.get();
        //Persisting the object to the database
        newProd.save();
        //Redirect to the admin panel
        return redirect(controllers.routes.HomeController.adminPanel());

    }

    public Result payment() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);
        return ok(payment.render(searchProductForm));
    }

    public Result product() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);
        return ok(product.render(searchProductForm));
    }

    public Result searchProduct() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);

        return ok(search.render(searchProductForm));
    }

    public Result cart() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);
        return ok(cart.render(searchProductForm));
    }

    public Result wishlist() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Search> searchProductForm = formFactory.form(Search.class);
        return ok(wishlist.render(searchProductForm));
    }

}
