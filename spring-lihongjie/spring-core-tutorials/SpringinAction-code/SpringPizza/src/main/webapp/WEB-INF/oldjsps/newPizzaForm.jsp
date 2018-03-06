<%@ taglib prefix="form"   
    uri="http://www.springframework.org/tags/form" %>

<h2>New customer</h2>
<form:form action="flow.htm" commandName="order.customer">
  <input type="hidden" name="_flowExecutionKey" 
      value="${flowExecutionKey}">

  <b>Size: </b><br/>
  <input type="radio" value="SMALL" name="size">
      Small (12-inch)<br/>
  <input type="radio" value="MEDIUM" name="size">
      Medium (14-inch)<br/> 
  <input type="radio" value="LARGE" name="size" checked>
      Large (16-inch)<br/>
  <input type="radio" value="GINORMOUS" name="size">
      Ginormous (20-inch)<br/>  
  <br/>
  
  <b>Toppings: </b><br/>
  <input type="checkbox" value="PEPPERONI" 
      name="toppings">Pepperoni<br/>
  <input type="checkbox" value="SAUSAGE" 
      name="toppings">Sausage<br/>
  <input type="checkbox" value="HAMBURGER" 
      name="toppings">Hamburger<br/>
  <input type="checkbox" value="MUSHROOM" 
      name="toppings">Mushroom<br/>
  <input type="checkbox" value="CANADIAN_BACON" 
      name="toppings">Canadian Bacon<br/>
  <input type="checkbox" value="PINEAPPLE" 
      name="toppings">Pineapple<br/>
  <input type="checkbox" value="GREEN_PEPPER" 
      name="toppings">Green Pepper<br/>
  <input type="checkbox" value="ONION" 
      name="toppings">Onion<br/>
  <input type="checkbox" value="JALAPENO" 
      name="toppings">Jalapeno<br/>
  <input type="checkbox" value="TOMATO" 
      name="toppings">Tomato<br/>
  <input type="checkbox" value="EXTRA_CHEESE" 
      name="toppings">Extra Cheese<br/>
      
  <input type="submit" class="button" 
      name="_eventId_submit" value="Continue">
  <input type="submit" class="button" 
      name="_eventId_cancel" value="Cancel">          
</form:form>
