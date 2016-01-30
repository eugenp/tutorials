package com.baeldung.server;

import com.baeldung.Movie;
import com.baeldung.client.ServicesInterface;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.util.List;

public class RestEasyClient {

    public static void main(String[] args) {

        Movie st = new Movie();
        st.setImdbID("12345");

		/*
		 *  Alternatively you can use this simple String to send
		 *  instead of using a Student instance
		 *
		 *  String jsonString = "{\"id\":12,\"firstName\":\"Catain\",\"lastName\":\"Hook\",\"age\":10}";
		 */

        try {
            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target("http://localhost:8080/RestEasyTutorial/rest/movies/listmovies");

            ServicesInterface simple = target.proxy(ServicesInterface.class);
            final List<Movie> movies = simple.listMovies();

            /*
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Server response : \n");
            System.out.println(response.readEntity(String.class));

            response.close();
*/
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

}