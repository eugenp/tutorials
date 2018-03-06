<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table cellspacing="15">
  <s:url value="/spitters/${spittle.spitter.username}" var="spitter_url" />
  <tr>
    <td>
        <img src="<s:url value="/resources/images/spitter_avatar.png"/>" 
             width="48" height="48" /></td>
    <td><a href="${spitter_url}">${spittle.spitter.username}</a> <small><c:out value="${spittle.text}" /> <small><c:out value="${spittle.when}" /></small></small>
    <s:url value="/spittles/${spittle.id}" var="spittle_url" />
    <sf:form method="delete" action="${spittle_url}" name="deleteSpittle_${spittle.id}" cssClass="deleteForm">
      <input type="submit" value="Delete"/>
    </sf:form>      
    </td>
  </tr>
</table>
