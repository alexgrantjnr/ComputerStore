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
        Form<Search> searchProductForm = formFactory.form(Search.class);

        //Render the add Product view passing the form object
        return ok(login.render(searchProductForm, loginForm));
    }

    //Handle login submit
    public Result loginSubmit() {
        //Bind form instance to the values submitted from the form
        Form<Search> searchProductForm = formFactory.form(Search.class);
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

        //Check for errors
        //Uses the validation method in the Login class
        if(loginForm.hasErrors()) {
            return badRequest(login.render(searchProductForm, loginForm));
        }
        else {
            //User loged in successfully
            //clear the existing session - resets session id
            session().clear();
            //Stor the logged in email in session cokie
            session("email", loginForm.get().getEmail());
        }
        //Return to home page
        return redirect(controllers.routes.HomeController.index());
    }
}