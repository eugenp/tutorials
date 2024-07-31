package controllers;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

// Add the following to conf/routes 
/*
GET     /$model;format="camel"$        controllers.$model;format="Camel"$Controller.$model;format="camel"$Get
POST    /$model;format="camel"$        controllers.$model;format="Camel"$Controller.$model;format="camel"$Post
*/

/**
 * $model;format="Camel"$ form controller for Play Java
 */
public class $model;format="Camel"$Controller extends Controller {

    private final Form<$model;format="Camel"$Data> $model;format="camel"$Form;

    @Inject
    public $model;format="Camel"$Controller(FormFactory formFactory) {
        this.$model;format="camel"$Form = formFactory.form($model;format="Camel"$Data.class);
    }

    public Result $model;format="camel"$Get() {
        return ok(views.html.$model;format="camel"$.form.render($model;format="camel"$Form));
    }

    public Result $model;format="camel"$Post() {
        Form<$model;format="Camel"$Data> boundForm = $model;format="camel"$Form.bindFromRequest();
        if (boundForm.hasErrors()) {
            return badRequest(views.html.$model;format="camel"$.form.render(boundForm));
        } else {
            $model;format="Camel"$Data $model;format="camel"$ = boundForm.get();
            flash("success", "$model;format="Camel"$ " + $model;format="camel"$);
            return redirect(routes.$model;format="Camel"$Controller.$model;format="camel"$Get());
        }
    }

}
