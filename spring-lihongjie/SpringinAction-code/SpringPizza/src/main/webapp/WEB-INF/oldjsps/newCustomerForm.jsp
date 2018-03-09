<%@ taglib prefix="form"   
    uri="http://www.springframework.org/tags/form" %>

<!-- 
  Form for entering new customer information.
  
  From listing 15.4
 -->
<h2>New customer</h2>
<form:form action="flow.htm" commandName="order.customer">
  <input type="hidden" name="_flowExecutionKey" 
      value="${flowExecutionKey}">

  <b>Phone:  </b> ${requestParameters.phoneNumber} <br/>
  <b>Name:  </b> <form:input path="name" /><br/>
  <b>Street address:  </b> 
      <form:input path="streetAddress" /><br/>
  <b>City:  </b> <form:input path="city" /><br/>
  <b>State:  </b> <form:input path="state" /><br/>
  <b>Zip:  </b> <form:input path="zipCode" /><br/>
  <input type="submit" class="button" 
      name="_eventId_submit" value="Submit">
  <input type="submit" class="button" 
      name="_eventId_cancel" value="Cancel">  
</form:form>
