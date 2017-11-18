package org.baeldung.boot.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class HeaderVersionArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(Version.class) != null;
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter, final ModelAndViewContainer modelAndViewContainer, final NativeWebRequest nativeWebRequest, final WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

        return request.getHeader("Version");
    }
}
