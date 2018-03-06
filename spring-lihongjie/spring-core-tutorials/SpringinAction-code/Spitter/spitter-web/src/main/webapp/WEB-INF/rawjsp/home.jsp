<start id="jsp.home_jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="spittle" items="${spittles}">
  <c:out value="${spittle.text}" /> 
  <small><c:out value="${spittle.when}" /></small>
</c:forEach>
<end id="jsp.home_jsp" />
