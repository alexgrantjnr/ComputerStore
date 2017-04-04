package controllers;


import org.mindrot.jbcrypt.BCrypt;
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
        //List<Product> indexProducts = Product.findRandomProducts();
        return ok(index.render(getUserFromSession(),null));
    }

    public Result register(){
        Form<User> addUserForm = formFactory.form(User.class);
        return ok(register.render(addUserForm,getUserFromSession()));
    }

    public Result registerSubmit(){
        Form<User> newUser = formFactory.form(User.class).bindFromRequest();
        User newRegisteredUser = newUser.get();
        newRegisteredUser.setRole("Customer");
        newRegisteredUser.setPassword(BCrypt.hashpw(newRegisteredUser.getPassword(), BCrypt.gensalt()));
        newRegisteredUser.save();
        return redirect(routes.LoginController.login());
    }

    @Security.Authenticated(Secured.class)
    public Result account() {
        User userDetails = getUserFromSession();
        Form<User> updateDetailsForm = formFactory.form(User.class).fill(userDetails);
        return ok(account.render(getUserFromSession(),updateDetailsForm));
    }

    @Security.Authenticated(Secured.class)
    public Result updateDetails(){
        Form<User> newDetails = formFactory.form(User.class).bindFromRequest();
        User userUpdated = newDetails.get();
        userUpdated.setPassword(BCrypt.hashpw(userUpdated.getPassword(), BCrypt.gensalt()));
        userUpdated.update();
        return redirect(routes.HomeController.account());
    }

    @Security.Authenticated(Secured.class)
    public Result payment() {
        return ok(payment.render(getUserFromSession()));
    }

    public Result product(Long productId) {
        Product prod = Product.getProductById(productId);
        return ok(product.render(prod,getUserFromSession()));
    }

    public Result searchCategory(String categoryName) {
        List<Product>products = Product.findByCategory(categoryName);
        if (products.isEmpty()){
            List<Product>productsAll = Product.findAll();
            return ok(search.render(productsAll,getUserFromSession()));
        }
        return ok(search.render(products,getUserFromSession()));
    }

    public Result searchProduct(String productName) {
        List<Product>products = Product.findByName(productName);
        if (products.isEmpty()){
            List<Product>productsAll = Product.findAll();
            return ok(search.render(productsAll,getUserFromSession()));
        }
        return ok(search.render(products,getUserFromSession()));
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
        return ok(blog.render(getUserFromSession(),blogPosts));
    }

    @Security.Authenticated(Secured.class)
    public Result updatePostLikes(Long blogPostId){
        BlogPost update = BlogPost.getBlogPostById(blogPostId);
        int likeNumUpdate = (update.getNumLikes()+1);
        update.setNumLikes(likeNumUpdate);
        update.update();
        return redirect(controllers.routes.HomeController.blog());
    }

    private User getUserFromSession(){
        return User.getUserById(session().get("email"));
    }
}
