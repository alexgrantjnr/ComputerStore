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

public class LoginController extends Controller {

    private FormFactory formFactory;
    private Environment env;

    @Inject
    public LoginController(Environment e, FormFactory f) {
        this.env = e;
        this.formFactory = f;
    }

    //Render and return the Login view
    public Result login () {
        //Create a form by wrapping the Product class
        //in a FormFactory form instance
        Form<Login> loginForm = formFactory.form(Login.class);
        //Render the add Product view passing the form object
        return ok(login.render(loginForm,User.getUserById(session().get("email"))));
    }

    // Handle login submit
    public Result loginSubmit() {
        // Bind form instance to the values submitted from the form
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        // Check for errors
        // Uses the validate method defined in the Login class
        if (loginForm.hasErrors()) {
            // If errors, show the form again
            return badRequest(login.render(loginForm, User.getUserById(session().get("email"))));
        }
        else {
            // User Logged in successfully
            // Clear the existing session - resets session id
            session().clear();
            // Store the logged in email in the session (cookie)
            session("email", loginForm.get().getEmail());
        }
        // Return to admin or customer home page
        User u = User.getUserById(session().get("email"));
        if (u.getRole().equals("admin")) {
            Form<Product> addProductForm = formFactory.form(Product.class);
            List<Product> allProducts = Product.findAll();

            return redirect(controllers.routes.HomeController.index());
        }
        else {
            return redirect(controllers.routes.HomeController.index());
        }
    }

    public Result logout() {
        User u = User.getUserById(session().get("email"));
        u.getBasket().removeAllItems();
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.HomeController.index()
        );
    }
}