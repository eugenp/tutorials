//<start id="java_postFilterSimple"/> 
@PreAuthorize("hasRole('ROLE_SPITTER)")
@PostFilter("filterObject.spitter.username == principal.name")
public List<Spittle> getABunchOfSpittles() { 
  ... 
}
//<end id="java_postFilterSimple"/> 


//<start id="java_postFilterHasPermission"/> 
@PreAuthorize("hasRole('ROLE_SPITTER)")
@PostFilter("hasPermission(filterObject, 'delete')")
public List<Spittle> getSpittlesToDelete() { 
  ... 
}
//<end id="java_postFilterHasPermission"/> 

//<start id="java_SpittlePermissionEvaluator"/> 
package com.habuma.spitter.security;
import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import com.habuma.spitter.domain.Spittle;

public class SpittlePermissionEvaluator implements PermissionEvaluator {
	public boolean hasPermission(Authentication authentication,
			Object target, Object permission) {
		if (target instanceof Spittle) {
			Spittle spittle = (Spittle) target;
			if ("delete".equals(permission)) {
				return spittle.getSpitter().getUsername().equals(
				    authentication.getName()) || hasProfanity(spittle);
			}
		}

		throw new UnsupportedOperationException(
				"hasPermission not supported for object <" + target
						+ "> and permission <" + permission + ">");
	}

	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException();
	}
	
	private boolean hasProfanity(Spittle spittle) {
	    ...
	    return false;
	}
}
//<end id="java_SpittlePermissionEvaluator"/> 

<!--<start id="xml_globalMethodSecurityExpressionHandler"/>--> 
<global-method-security pre-post-annotations="enabled">
	<expression-handler ref="expressionHandler"/>
</global-method-security>
<!--<end id="xml_globalMethodSecurityExpressionHandler"/>--> 

<!--<start id="xml_ExpressionHandler"/>--> 
<beans:bean id="expressionHandler" class=
    "org.springframework.security.access.expression.method.[CA]
                                   DefaultMethodSecurityExpressionHandler">
  <beans:property name="permissionEvaluator">
    <beans:bean class=
                "com.habuma.spitter.security.SpittlePermissionEvaluator" />
  </beans:property>
</beans:bean>
<!--<end id="xml_ExpressionHandler"/>--> 
