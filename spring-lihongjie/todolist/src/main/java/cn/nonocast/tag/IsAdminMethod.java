package cn.nonocast.tag;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class IsAdminMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean result = false;
        for(GrantedAuthority each : auth.getAuthorities()) {
            if(each.getAuthority().equals("ROLE_ADMIN")) {
                result = true;
                break;
            }
        }
        return result;
    }
}
