package com.baeldung.apache.velocity.servlet;

import com.baeldung.apache.velocity.model.Product;
import com.baeldung.apache.velocity.service.ProductService;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityLayoutServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LayoutServlet extends VelocityLayoutServlet {
	ProductService service = new ProductService();

	@Override
	public Template handleRequest(HttpServletRequest request, HttpServletResponse response, Context context) {

        Logger logger= LoggerFactory.getLogger(LayoutServlet.class);

		List<Product> products = service.getProducts();

		context.put("products", products);

		Template template = null;

		try {
			template = getTemplate("templates/layoutdemo.vm");

			response.setHeader("Template Returned", "Success");
		} catch (Exception e) {
			logger.error("Error while reading the template ",e);
		}

		return template;

	}

}
