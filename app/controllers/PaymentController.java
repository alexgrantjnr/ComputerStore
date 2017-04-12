package controllers;

import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;
import javax.inject.Inject;
import views.html.*;
import models.*;

import java.util.List;

public class PaymentController extends Controller{

    private FormFactory formFactory;
    private Environment env;

    @Inject
    public PaymentController(FormFactory formFactory, Environment env) {
        this.formFactory = formFactory;
        this.env = env;
    }

    @Security.Authenticated(Secured.class)
    public Result payment() {
        Form<PaymentDetails> paymentForm = formFactory.form(PaymentDetails.class);
        return ok(payment.render(getUserFromSession(),paymentForm));
    }

    public Result paymentSubmit(){
        Form<PaymentDetails> paymentForm = formFactory.form(PaymentDetails.class).bindFromRequest();
        if (paymentForm.hasErrors()){
            return badRequest(payment.render(getUserFromSession(),paymentForm));
        }else {
            PaymentDetails userDetails = paymentForm.get();
            userDetails.setHoldersEmail(getUserFromSession().getEmail());
            if (userDetails.getCardNumber().length() == 16 && userDetails.getCardNumber().matches("[0-9]+")) {
                if (userDetails.getCvvNum().length() == 3 && userDetails.getCvvNum().matches("[0-9]+")){
                    if (userDetails.getExpiryDate().matches("[0-9]+")) {
                        userDetails.setTotal(getUserFromSession().getBasket().getBasketTotal());
                        userDetails.save();
                    }else{
                        return badRequest(payment.render(getUserFromSession(), paymentForm));
                    }
                }else{
                    return badRequest(payment.render(getUserFromSession(), paymentForm));
                }
            }else{
                return badRequest(payment.render(getUserFromSession(), paymentForm));
            }
        }
        List<Product> indexProducts = Product.indexProducts();
        return ok(index.render(getUserFromSession(),indexProducts));
    }

    private User getUserFromSession(){
        return User.getUserById(session().get("email"));
    }

}
