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
        return ok(login.render(loginForm,getUserFromSession()));
    }

    //Handle login submit
    public Result loginSubmit() {
        //Bind form instance to the values submitted from the form
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        //Check for errors
        //Uses the validation method in the Login class
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm,getUserFromSession()));
        }
        else {
            //User logged in successfully
            //clear the existing session - resets session id
            session().clear();
            //Store the logged in email in session cookie
            session("email", loginForm.get().getEmail());
        }
        //Return to home page
        return redirect(controllers.routes.HomeController.index());
    }

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.LoginController.login()
        );
    }

    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }
}