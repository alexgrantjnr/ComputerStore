package controllers;

import play.mvc.*;

import views.html.*;


public class HomeController extends Controller {

    private FormFactory formFactory;

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

        Form<Product> addProductForm = formFactory.form(Product.class);

        return ok(adminpanel.render(addProductForm));
    }

    public Result addProductSubmit(){

        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();

        if (newProduct.hasErrors){
            return badRequest(adminpanel.render(addProductForm));
        }

        Product newProd = newProduct.get();

        newProd.save();

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
