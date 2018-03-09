<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div>
  <h2>Create a free Spitter account</h2>
	
	<s:url value="/spitter" var="spitterform_url" /> <!--<co id="co_spitterform_url"/>-->
	
	<form:form method="POST" 
	           action="${spitterform_url}" 
	           modelAttribute="spitter">  <!--<co id="co_form_form"/>-->
		
		Full name: <form:input path="fullName" size="15" /><br/>
		Username: <form:input path="username" size="15" 
		                      maxlength="15" /><br/>
		Password: <form:password path="password" size="30" 
		                         showPassword="true"/><br/> 
		Email Address: <form:input path="email" size="30"/><br/> 
		
		<form:checkbox path="updateByEmail"/>Send me email updates!<br/>
		
		<input name="commit" type="submit" 
		                    value="I accept. Create my account." />
	</form:form>
</div>
