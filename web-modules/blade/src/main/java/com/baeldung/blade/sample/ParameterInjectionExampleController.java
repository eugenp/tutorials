package com.baeldung.blade.sample;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import com.baeldung.blade.sample.vo.User;
import com.blade.mvc.annotation.CookieParam;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.HeaderParam;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.MultipartParam;
import com.blade.mvc.annotation.Param;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PathParam;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.http.Response;
import com.blade.mvc.multipart.FileItem;
import com.blade.mvc.ui.RestResponse;

@Path
public class ParameterInjectionExampleController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ParameterInjectionExampleController.class);

    @GetRoute("/params/form")
    public void formParam(@Param String name, Response response) {
        log.info("name: " + name);
        response.text(name);
    }

    @GetRoute("/params/path/:uid")
    public void restfulParam(@PathParam Integer uid, Response response) {
        log.info("uid: " + uid);
        response.text(String.valueOf(uid));
    }

    @PostRoute("/params-file") // DO NOT USE A SLASH WITHIN THE ROUTE OR IT WILL BREAK (?)
    @JSON
    public RestResponse<?> fileParam(@MultipartParam FileItem fileItem) throws Exception {
        try {
            byte[] fileContent = fileItem.getData();

            log.debug("Saving the uploaded file");
            java.nio.file.Path tempFile = Files.createTempFile("baeldung_tempfiles", ".tmp");
            Files.write(tempFile, fileContent, StandardOpenOption.WRITE);

            return RestResponse.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RestResponse.fail(e.getMessage());
        }
    }

    @GetRoute("/params/header")
    public void headerParam(@HeaderParam String customheader, Response response) {
        log.info("Custom header: " + customheader);
        response.text(customheader);
    }

    @GetRoute("/params/cookie")
    public void cookieParam(@CookieParam(defaultValue = "default value") String myCookie, Response response) {
        log.info("myCookie: " + myCookie);
        response.text(myCookie);
    }

    @PostRoute("/params/vo")
    public void voParam(@Param User user, Response response) {
        log.info("user as voParam: " + user.toString());
        response.html(user.toString() + "<br/><br/><a href='/'>Back</a>");
    }
}