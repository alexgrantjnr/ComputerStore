package controllers;

import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.*;
import play.db.ebean.Transactional;
import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import views.html.*;
import models.*;
import java.io.*;
import java.nio.file.Files;
import static play.mvc.Http.MultipartFormData;

public class AdminController extends Controller {

    private FormFactory formFactory;

    @Inject
    public AdminController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    public Result adminPanel() {
        Form<Product> addProductForm = formFactory.form(Product.class);
        List<Product> allProducts = Product.findAll();
        return ok(adminpanel.render(addProductForm, allProducts, getUserFromSession()));
    }


    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    //Add a Product to the database
    public Result addProductSubmit() {
        //Create a new Form object of type product, which will be passed to the view
        Form<Product> addProductForm = formFactory.form(Product.class);
        //Create a Form object which takes the form passed from the view
        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();
        //Passes a list of all products to search page
        List<Product> allProducts = Product.findAll();

        //If the form has errors return a bad request
        if (newProduct.hasErrors()) {
            flash("error","Please correct the form below");
            return badRequest(adminpanel.render(addProductForm, allProducts, getUserFromSession()));
        }
        //Making a new object of type Product and assigning the variables from the form to the object
        Product newProd = newProduct.get();

        MultipartFormData body = request().body().asMultipartFormData();

        MultipartFormData.FilePart part = body.getFile("ProductImage");

        //If image form isnt equal to null then try get the file and convert to byte array
        if(part != null){
            File picture = (File) part.getFile();
            try{
                byte[] imageArray = new byte[(int) picture.length()];
                FileInputStream fileStream= new FileInputStream(picture);
                fileStream.read(imageArray);
                fileStream.close();
                newProd.setProductImage(imageArray);
            }catch (IOException ex){
                return internalServerError("Error reading file upload");
            }
        }
        //Persisting the object to the database
        newProd.save();
        //Redirect to the admin panel
        return redirect(controllers.routes.AdminController.adminPanel());
    }

    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    public Result deleteProduct(Long productId) {
        Product.deleteProduct(productId);
        return redirect(routes.AdminController.adminPanel());
    }

    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }
}