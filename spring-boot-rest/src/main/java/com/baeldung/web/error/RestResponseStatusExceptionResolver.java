package com.baeldung.web.error;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class RestResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(RestResponseStatusExceptionResolver.class);
    
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, 
        HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                return handleIllegalArgument(
                  (IllegalArgumentException) ex, request, response, handler);
            }
        } catch (Exception handlerException) {
            logger.warn("Handling of [{}] resulted in Exception", ex.getClass().getName(), handlerException);
        }
        return null;
    }

    private ModelAndView handleIllegalArgument(IllegalArgumentException ex, 
        final HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String accept = request.getHeader(HttpHeaders.ACCEPT);
        
        response.sendError(HttpServletResponse.SC_CONFLICT);
        response.setHeader("ContentType", accept);
        
        final ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", prepareErrorResponse(accept));
        return modelAndView;
    }
    
    /** Prepares error object based on the provided accept type.
     * @param accept The Accept header present in the request.
     * @return The response to return
     * @throws JsonProcessingException
     */
    private String prepareErrorResponse(String accept) throws JsonProcessingException {
        final Map<String, String> error = new HashMap<>();
        error.put("Error", "Application specific error message");
        
        final String response;
        if(MediaType.APPLICATION_JSON_VALUE.equals(accept)) {
            response = new ObjectMapper().writeValueAsString(error);
        } else {
            response = new XmlMapper().writeValueAsString(error);
        }
        
        return response;
    }
    
    

}
