package app.controllers;

import app.models.Product;
import org.codehaus.jackson.map.ObjectMapper;
import org.javalite.activeweb.AppController;
import org.javalite.activeweb.annotations.RESTful;

import java.util.Map;

@RESTful
public class ProductsController extends AppController {

    private ObjectMapper mapper = new ObjectMapper();

    public void index() {
        try {
            view("products", Product.findAll());
            render().contentType("application/json");
        } catch (Exception e) {
            view("message", "There was an error.", "code", 200);
            render("message");
        }
    }

    public void create() {
        try {
            Map payload = mapper.readValue(getRequestString(), Map.class);
            Product p = new Product();
            p.fromMap(payload);
            p.saveIt();
            view("message", "Successfully saved product id " + p.get("id"), "code", 200);
            render("message");
        } catch (Exception e) {
            view("message", "There was an error.", "code", 200);
            render("message");
        }
    }

    public void update() {
        try {
            Map payload = mapper.readValue(getRequestString(), Map.class);
            String id = getId();
            Product p = Product.findById(id);
            if (p == null) {
                view("message", "Product id " + id + " not found.", "code", 200);
                render("message");
                return;
            }
            p.fromMap(payload);
            p.saveIt();
            view("message", "Successfully updated product id " + id, "code", 200);
            render("message");
        } catch (Exception e) {
            view("message", "There was an error.", "code", 200);
            render("message");
        }
    }

    public void show() {
        try {
            String id = getId();
            Product p = Product.findById(id);
            if (p == null) {
                view("message", "Product id " + id + " not found.", "code", 200);
                render("message");
                return;
            }
            view("product", p);
            render("_product");
        } catch (Exception e) {
            view("message", "There was an error.", "code", 200);
            render("message");
        }
    }

    public void destroy() {
        try {
            String id = getId();
            Product p = Product.findById(id);
            if (p == null) {
                view("message", "Product id " + id + " not found.", "code", 200);
                render("message");
                return;
            }
            p.delete();
            view("message", "Successfully deleted product id " + id, "code", 200);
            render("message");
        } catch (Exception e) {
            view("message", "There was an error.", "code", 200);
            render("message");
        }
    }

    @Override
    protected String getContentType() {
        return "application/json";
    }

    @Override
    protected String getLayout() {
        return null;
    }
}
