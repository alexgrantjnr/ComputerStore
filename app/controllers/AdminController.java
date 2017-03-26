package controllers;

import play.mvc.*;
import play.data.*;
import javax.inject.Inject;
import java.util.List;
import views.html.*;
import models.*;


public class AdminController extends Controller {

    private FormFactory formFactory;

    @Inject
    public AdminController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    //@Security.Authenticated(Secured.class)
    //@With(AuthAdmin.class)
    public Result adminPanel() {
        Form<Product> addProductForm = formFactory.form(Product.class);
        Form<BlogPost> addBlogPostForm = formFactory.form(BlogPost.class);
        List<Product> allProducts = Product.findAll();
        List<User> allUsers = User.findAll();
        return ok(adminpanel.render(addProductForm, allProducts, getUserFromSession(),addBlogPostForm,allUsers));
    }

    public Result addBlogSubmit(){
        Form<BlogPost>addBlogPost = formFactory.form(BlogPost.class).bindFromRequest();
        BlogPost newBlog = addBlogPost.get();
        newBlog.setNumLikes(0);
        newBlog.save();
        return redirect(controllers.routes.AdminController.adminPanel());
    }

    //@Security.Authenticated(Secured.class)
    //@With(AuthAdmin.class)
    //Add a Product to the database
    public Result addProductSubmit() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Product> addProductForm = formFactory.form(Product.class);
        Form<BlogPost> addBlogPostForm = formFactory.form(BlogPost.class);
        //Create a Form object which takes the form passed from the view
        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();
        //Passes a list of all products to search page
        List<Product> allProducts = Product.findAll();
        //List of all Users
        List<User> allUsers = User.findAll();

        //If the form has errors return a bad request
        if (newProduct.hasErrors()) {
            flash("error","Please correct the form below");
            return badRequest(adminpanel.render(addProductForm, allProducts, getUserFromSession(),addBlogPostForm,allUsers));
        }
        //Making a new object of type Product and assigning the variables from the form to the object
        Product newProd = newProduct.get();

        //Persisting the object to the database
        newProd.save();
        //Redirect to the admin panel
        return redirect(controllers.routes.AdminController.adminPanel());
    }

    //@Security.Authenticated(Secured.class)
    //@With(AuthAdmin.class)
    public Result deleteProduct(Long productId) {
        Product.deleteProduct(productId);
        return redirect(routes.AdminController.adminPanel());
    }

    public Result deleteUser(String email) {
        User.deleteUser(email);
        return redirect(routes.AdminController.adminPanel());
    }

    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }
}