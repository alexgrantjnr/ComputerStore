package controllers;

import play.mvc.*;

import views.html.*;


public class HomeController extends Controller {

    
    public Result index() {
        return ok(index.render());
    }

    public Result account() {
        return ok(account.render());
    }

    public Result adminPanel() {
        return ok(adminpanel.render());
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

}
