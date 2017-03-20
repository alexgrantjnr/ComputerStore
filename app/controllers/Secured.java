package controllers;

import play.*;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.*;
import models.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx){
    return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.HomeController.index());
    }
}
