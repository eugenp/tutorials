package restx.demo.rest;

import restx.demo.domain.Message;
import restx.demo.Roles;
import org.joda.time.DateTime;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RolesAllowed;
import restx.security.RestxSession;

import javax.validation.constraints.NotNull;

@Component @RestxResource
public class HelloResource {

    /**
     * Say hello to currently logged in user.
     *
     * Authorized only for principals with Roles.HELLO_ROLE role.
     *
     * @return a Message to say hello
     */
    @GET("/message")
    @RolesAllowed(Roles.HELLO_ROLE)
    public Message sayHello() {
        return new Message().setMessage(String.format(
                "hello %s, it's %s",
                RestxSession.current().getPrincipal().get().getName(),
                DateTime.now().toString("HH:mm:ss")));
    }

    /**
     * Say hello to anybody.
     *
     * Does not require authentication.
     *
     * @return a Message to say hello
     */
    @GET("/hello")
    @PermitAll
    public Message helloPublic(String who) {
        return new Message().setMessage(String.format(
                "hello %s, it's %s",
                who, DateTime.now().toString("HH:mm:ss")));
    }

    public static class MyPOJO {
        @NotNull
        String value;
        public String getValue(){ return value; }
        public void setValue(String value){ this.value = value; }
    }
    @POST("/mypojo")
    @PermitAll
    public MyPOJO helloPojo(MyPOJO pojo){
        pojo.setValue("hello "+pojo.getValue());
        return pojo;
    }
}
