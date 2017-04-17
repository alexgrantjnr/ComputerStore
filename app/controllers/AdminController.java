package controllers;

import models.BlogPost;
import models.PaymentDetails;
import models.Product;
import models.User;
import play.api.Environment;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.*;
import javax.inject.Inject;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

// File upload and image editing dependencies
//test
public class AdminController extends Controller {

    // Declare a private FormFactory instance
    private FormFactory formFactory;
    private Environment env;

    //  Inject an instance of FormFactory it into the controller via its constructor
    @Inject
    public AdminController(Environment e, FormFactory f) {
        this.formFactory = f;
        this.env = e;
    }

    //@Security.Authenticated(Secured.class)
    //@With(AuthAdmin.class)
    public Result adminPanel() {
        Form<Product> addProductForm = formFactory.form(Product.class);
        Form<BlogPost> addBlogPostForm = formFactory.form(BlogPost.class);
        List<Product> allProducts = Product.findAll();
        List<BlogPost> allBlogPosts = BlogPost.findAll();
        List<User> allUsers = User.findAll();
        int[] barChartData = barChartData();
        int[] userJoinDate = userJoinDate();
        double[] revenuePerMonth = revenuePerMonth();
        Double[] temp = new Double[revenuePerMonth.length];
        for (int i = 0; i< temp.length; i++){
            temp[i] = revenuePerMonth[i];
        }
        return ok(adminpanel.render(addProductForm, allProducts, getUserFromSession(), addBlogPostForm, allUsers, allBlogPosts, env, barChartData, userJoinDate, temp));
    }

    public Result addBlogSubmit() {
        String saveImageMsg = "";
        Form<BlogPost> addBlogPost = formFactory.form(BlogPost.class).bindFromRequest();
        BlogPost newBlog = addBlogPost.get();
        newBlog.setNumLikes(0);
        newBlog.save();
        MultipartFormData data = request().body().asMultipartFormData();

        FilePart image = data.getFile("upload");

        saveImageMsg = saveFile(newBlog.getBlogId(), image, "BlogPost_Images", false);

        flash("success", "Blog " + newBlog.getBlogTitle() + " has been created/ updated " + saveImageMsg);
        return redirect(controllers.routes.AdminController.adminPanel());
    }

    //@Security.Authenticated(Secured.class)
    //@With(AuthAdmin.class)
    //Add a Product to the database
    @Transactional
    public Result addProductSubmit() {
        String saveImageMsg;
        //Create a new Form object of type product, which will be passed to the view
        Form<Product> addProductForm = formFactory.form(Product.class);
        Form<BlogPost> addBlogPostForm = formFactory.form(BlogPost.class);
        //Create a Form object which takes the form passed from the view
        int[] barChartData = barChartData();
        int[] userJoinDate = userJoinDate();
        double[] revenuePerMonth = revenuePerMonth();

        Double[] temp = new Double[revenuePerMonth.length];
        for (int i = 0; i< temp.length; i++){
            temp[i] = revenuePerMonth[i];
        }

        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();

        //Passes a list of all products to search page
        List<Product> allProducts = Product.findAll();
        //List of all Users
        List<User> allUsers = User.findAll();
        //List of all blog posts
        List<BlogPost> allBlogPosts = BlogPost.findAll();

        //If the form has errors return a bad request
        if (newProduct.hasErrors()) {
            flash("error", "Please correct the form below");
            return badRequest(adminpanel.render(addProductForm, allProducts, getUserFromSession(), addBlogPostForm, allUsers, allBlogPosts, env, barChartData, userJoinDate, temp));
        }
        //Making a new object of type Product and assigning the variables from the form to the object
        Product newProd = newProduct.get();

        newProd.save();

        MultipartFormData data = request().body().asMultipartFormData();

        FilePart image = data.getFile("upload");

        saveImageMsg = saveFile(newProd.getProductId(), image, "productImages", true);

        flash("success", "Product " + newProd.getName() + " has been created/ updated " + saveImageMsg);

        //Redirect to the admin panel
        return redirect(controllers.routes.AdminController.adminPanel());
    }

    //@Security.Authenticated(Secured.class)
    //@With(AuthAdmin.class)
    public Result deleteProduct(Long productId) {
        Product.deleteProduct(productId);
        return redirect(routes.AdminController.adminPanel());
    }

    public Result deleteBlogPost(Long blogId) {
        BlogPost.deleteBlogPost(blogId);
        return redirect(routes.AdminController.adminPanel());
    }

    public Result deleteUser(String email) {
        User.deleteUser(email);
        return redirect(routes.AdminController.adminPanel());
    }

    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }

    // Save an image file
    public String saveFile(Long id, FilePart<File> image, String folder, boolean thumbnail) {
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
                op.resize(600, 400);
                // Save the  image
                op.addImage("public/images/" + folder + "/" + id + ".jpg");
                if (thumbnail == true) {
                    // thumbnail
                    IMOperation thumb = new IMOperation();
                    // Get the uploaded image file
                    thumb.addImage(file.getAbsolutePath());
                    thumb.thumbnail(180);
                    // Save the  image
                    thumb.addImage("public/images/" + folder + "/thumbnails/" + id + ".jpg");
                    // execute the operation
                    try {
                        cmd.run(op);
                        cmd.run(thumb);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        cmd.run(op);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return " and image saved";
                }
            }
            return "image file missing";
        }
        return "image file missing";
    }

    //Gets barchart data based on stock per category
    public int[] barChartData() {
        int[] barChartData = new int[6];
        List<Product> products = Product.findAll();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getCategory().equals("Laptops")) {
                barChartData[0] += products.get(i).getQuantity();
            } else if (products.get(i).getCategory().equals("Desktops")) {
                barChartData[1] += products.get(i).getQuantity();
            } else if (products.get(i).getCategory().equals("Monitors")) {
                barChartData[2] += products.get(i).getQuantity();
            } else if (products.get(i).getCategory().equals("Tablets")) {
                barChartData[3] += products.get(i).getQuantity();
            } else if (products.get(i).getCategory().equals("Accessories")) {
                barChartData[4] += products.get(i).getQuantity();
            } else {
                barChartData[5] += products.get(i).getQuantity();
            }
        }
        return barChartData;
    }

    //Gets users joined per month
    public int[] userJoinDate() {
        int joinDate[] = new int[12];
        Calendar cal = Calendar.getInstance();
        List<User> userJoins = User.findAll();
        for (int i = 0; i < userJoins.size(); i++) {
            cal.setTime(userJoins.get(i).getJoinDate());
            int month = cal.get(Calendar.MONTH);
            for (int j = 0; j < joinDate.length; j++) {
                if (month == j) {
                    joinDate[j]++;
                }
            }
        }
        return joinDate;
    }

    public double[] revenuePerMonth() {
        double[] revenuePerMonth = new double[12];
        Calendar cal = Calendar.getInstance();
        List<PaymentDetails> payments = PaymentDetails.findAll();
        for (int i = 0; i < payments.size(); i++) {
            cal.setTime(payments.get(i).getPaymentDate());
            int month = cal.get(Calendar.MONTH);
            for (int j = 0; j < revenuePerMonth.length; j++) {
                if (month == j) {
                    revenuePerMonth[j] += payments.get(i).getTotal();
                }
            }
        }
        return revenuePerMonth;
    }
}