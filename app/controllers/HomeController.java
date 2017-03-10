package controllers;

import play.mvc.*;

import views.html.*;


public class HomeController extends Controller {

    
    public Result index() {
        return ok(index.render("Index"));
    }

    public Result account() {
        return ok(account.render("Account"));
    }

    public Result adminPanel() {
        return ok(adminpanel.render("AdminPanel"));
    }

    public Result payment() {
        return ok(payment.render("Payment"));
    }

    public Result product() {
        return ok(product.render("Product"));
    }

    public Result search() {
        return ok(search.render("Search"));
    }

}
