<h2>Choose Toppings</h2>
<form method="POST" action="flow.htm">
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

  <input type="hidden" name="_flowExecutionKey" 
      value="${flowExecutionKey}">

  <input type="submit" class="button" 
      name="_eventId_submit" value="Continue">
  <input type="submit" class="button" 
      name="_eventId_cancel" value="Cancel">  
</form>
