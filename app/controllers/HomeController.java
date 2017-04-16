package controllers;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.mindrot.jbcrypt.BCrypt;
import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;
import javax.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import views.html.*;
import models.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

public class HomeController extends Controller {

    private FormFactory formFactory;
    private Environment env;

    @Inject
    public HomeController(FormFactory formFactory, Environment env) {
        this.formFactory = formFactory;
        this.env = env;
    }

    public Result index() {
        List<Product> indexProducts = Product.indexProducts();
        return ok(index.render(getUserFromSession(),indexProducts,env));
    }

    public Result register(){
        Form<User> addUserForm = formFactory.form(User.class);
        return ok(register.render(addUserForm,getUserFromSession()));
    }

    public Result registerSubmit(){
        String saveImageMsg = "";
        Form<User> newUser = formFactory.form(User.class).bindFromRequest();
        User newRegisteredUser = newUser.get();
        newRegisteredUser.setRole("Customer");
        newRegisteredUser.setPassword(BCrypt.hashpw(newRegisteredUser.getPassword(), BCrypt.gensalt()));
        newRegisteredUser.save();
        MultipartFormData data = request().body().asMultipartFormData();
        MultipartFormData.FilePart image = data.getFile("profilePic");
        saveImageMsg = saveFile(newRegisteredUser.getEmail(), image);
        flash("success", "Product " + newRegisteredUser.getFirstName() + " has been created/ updated " + saveImageMsg);
        return redirect(routes.LoginController.login());
    }

    @Security.Authenticated(Secured.class)
    public Result account() {
        List<PaymentDetails>userOrders = PaymentDetails.findByUser(getUserFromSession().getEmail());
        User userDetails = getUserFromSession();
        Form<User> updateDetailsForm = formFactory.form(User.class).fill(userDetails);
        return ok(account.render(getUserFromSession(),updateDetailsForm,env,userOrders));
    }

    @Security.Authenticated(Secured.class)
    public Result updateDetails(){
        String saveImageMsg = "";
        Form<User> newDetails = formFactory.form(User.class).bindFromRequest();
        User userUpdated = newDetails.get();
        userUpdated.setPassword(BCrypt.hashpw(userUpdated.getPassword(), BCrypt.gensalt()));
        userUpdated.update();
        MultipartFormData data = request().body().asMultipartFormData();
        MultipartFormData.FilePart image = data.getFile("profilePic");
        saveImageMsg = saveFile(userUpdated.getEmail(), image);
        flash("success", "User " + userUpdated.getFirstName() + " has been created/ updated " + saveImageMsg);
        return redirect(routes.HomeController.account());
    }

    public Result product(Long productId) {
        List<Product> relatedProducts = Product.indexProducts();
        Product prod = Product.getProductById(productId);
        return ok(product.render(prod,getUserFromSession(),env,relatedProducts));
    }

    public Result searchCategory(String categoryName) {
        List<Product>products = Product.findByCategory(categoryName);
        if (products.isEmpty()){
           flash("noproducts", "No Products Found");
            return ok(search.render(products,getUserFromSession(),env));
        }
        return ok(search.render(products,getUserFromSession(),env));
    }

    public Result searchProduct(String productName) {
        List<Product>products = Product.findByName(productName);
        if (products.isEmpty()){
            List<Product>productsAll = Product.findAll();
            return ok(search.render(productsAll,getUserFromSession(),env));
        }
        return ok(search.render(products,getUserFromSession(),env));
    }

    public Result filterProduct(String filter,String min,String max){
        double minimum = Double.parseDouble(min);
        double maximum = Double.parseDouble(max);
        List<Product>products = Product.filterProduct(filter,minimum,maximum);
        if (products.isEmpty()){
            flash("noproducts", "No Products Found");
            return ok(search.render(products,getUserFromSession(),env));
        }
        return ok(search.render(products,getUserFromSession(),env));
    }

    @Security.Authenticated(Secured.class)
    public Result cart() {
        return ok(cart.render(getUserFromSession()));
    }

    @Security.Authenticated(Secured.class)
    public Result wishlist() {
        return ok(wishlist.render(getUserFromSession()));
    }

    public Result blog(){
        List<BlogPost> blogPosts = BlogPost.findAll();
        return ok(blog.render(getUserFromSession(),blogPosts,env));
    }

    @Security.Authenticated(Secured.class)
    public Result updatePostLikes(Long blogPostId){
        BlogPost update = BlogPost.getBlogPostById(blogPostId);
        int likeNumUpdate = (update.getNumLikes()+1);
        update.setNumLikes(likeNumUpdate);
        update.update();
        return redirect(controllers.routes.HomeController.blog());
    }

    // Save an image file
    public String saveFile(String email, Http.MultipartFormData.FilePart<File> image) {
        if (image != null) {
            // Get mimetype from image
            String mimeType = image.getContentType();
            // Check if uploaded file is an image
            if (mimeType.startsWith("image/")) {
                // Create file from uploaded image
                File file = image.getFile();
                // create ImageMagick command instance
                ConvertCmd cmd = new ConvertCmd();
                // create the operation, add images and operators/options
                IMOperation op = new IMOperation();
                // Get the uploaded image file
                op.addImage(file.getAbsolutePath());
                // Resize using height and width constraints
                op.resize(300,300);
                // Save the  image
                op.addImage("public/images/user_profiles/" + email + ".jpg");

                try{
                    cmd.run(op);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                return " and image saved";
            }
        }
        return "image file missing";
    }

    private User getUserFromSession(){
        return User.getUserById(session().get("email"));
    }
}
