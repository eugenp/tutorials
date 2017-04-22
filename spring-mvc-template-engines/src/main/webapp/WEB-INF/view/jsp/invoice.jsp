<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="invoice" type="com.baeldung.teng.invoicing.domain.Invoice" scope="request" />

<!DOCTYPE html>
<html lang="en">

<head>
  <title>Invoice</title>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, shrink-to-fit=no" />
  <link href="../../assets/main.css" rel="stylesheet" />
</head>

<body>

<header>
  <h1>INVOICE</h1>
  <h2>Baeldung – The Spring Resource</h2>
</header>

<section>
  <dl>
    <dt>Invoice #</dt>
    <dd>${invoice.id}</dd>
    <dt>Date</dt>
    <dd>${invoice.date}</dd>
  </dl>
</section>

<c:if test="${not empty invoice.customer}">
  <section>
    <dl>
      <dt>BILL TO:</dt>
      <dd>${invoice.customer.firstName} ${invoice.customer.lastName}<br />
        <c:if test="${not empty invoice.customer.vatNumber}">
          <dl>
            <dt>VAT #</dt>
            <dd>${invoice.customer.vatNumber}</dd>
          </dl>
        </c:if>
      </dd>
    </dl>
  </section>
</c:if>

<table>
  <thead>
  <tr>
    <th>Item</th>
    <th>Quantity</th>
    <th>Unit Price (€)</th>
    <th>VAT (%)</th>
    <th>Line Total</th>
  </tr>
  </thead>

  <tbody>
  <c:forEach var="item" items="${invoice.items}">
    <tr>
      <td>${item.name}</td>
      <td>${item.quantity}</td>
      <td>${item.unitPrice}</td>
      <td>${item.vat}</td>
      <td>${item.totalPrice}</td>
    </tr>
  </c:forEach>
  </tbody>

  <tfoot>
  <tr>
    <td colspan="3">&nbsp;</td>
    <td>Total:</td>
    <td>€${invoice.totalPrice}</td>
  </tr>
  </tfoot>
</table>

<footer>
  <p>
    Baeldung – The Spring Resource |
    Java, Spring and Web Development tutorials |
    <a href="http://baeldung.com">baeldung.com</a>
  </p>
  <p>
    <a href="mailto:email@baeldung.com">email@baeldung.com</a>
  </p>
</footer>

<%--script src="js/main.js"></script--%>
</body>

</html>
