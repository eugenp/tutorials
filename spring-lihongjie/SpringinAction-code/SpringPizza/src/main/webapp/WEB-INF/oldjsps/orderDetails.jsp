<%@ taglib prefix="c" 
    uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" 
    uri="http://java.sun.com/jstl/fmt_rt" %>

<!-- 
  Displays order information
  
  From listing 15.5
 -->    
    
<h2>${order.customer.name}</h2>
<b>${order.customer.streetAddress}</b><br/>
<b>${order.customer.city}, ${order.customer.state} 
    ${order.customer.zipCode}</b><br/>
<b>${order.customer.phoneNumber}</b><br/>
<br/>
<a href="flow.htm?_flowExecutionKey=${flowExecutionKey}&_eventId=continue">Place Order</a> | 
<a href="flow.htm?_flowExecutionKey=${flowExecutionKey}&_eventId=cancel">Cancel</a>
<hr/>
<h3>Order total: <fmt:formatNumber type="currency" 
    value="${order.total}"/></h3>
<hr/>
<h3>Pizzas:</h3>
<small>
  <a href="flow.htm?_flowExecutionKey=${flowExecutionKey}&_eventId=addPizza">Add Pizza</a>
</small>
<br>
<c:forEach items="${order.pizzas}" var="pizza">
<li>${pizza.size} : 
  <c:forEach items="${pizza.toppings}" var="topping">
      ${topping}, 
  </c:forEach>
</li>
</c:forEach>
