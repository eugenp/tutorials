<#-- IntelliJ IDEA trick -->
<#-- @ftlvariable name="invoice" type="com.baeldung.teng.invoicing.domain.Invoice" -->

<!DOCTYPE html>
<html lang="en">

<head>
  <title>Invoice #${invoice.id}</title>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, shrink-to-fit=no" />
  <link href="../../../resources/invoice.css" rel="stylesheet" />
</head>

<body>

<header>
  <h1>INVOICE</h1>
  <h2>Baeldung – The Spring Resource</h2>
</header>

<section>
  <dl>
    <dt>Invoice</dt>
    <dd>FTL#${invoice.id}</dd>
    <dt>Date</dt>
    <dd>${invoice.date?datetime}</dd>
  </dl>
</section>

<#if (invoice.customer)??>
<section>
  <dl>
    <dt>BILL TO</dt>
    <dd>${invoice.customer.firstName} ${invoice.customer.lastName}</dd>
    <#if (invoice.customer.vatNumber)??>
      <dt>VAT</dt>
      <dd>${invoice.customer.vatNumber}</dd>
    </#if>
  </dl>
</section>
</#if>

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
  <#list invoice.items as item>
  <tr>
    <td>${item.name}</td>
    <td>${item.quantity}</td>
    <td>${item.unitPrice}</td>
    <td>${item.vat}</td>
    <td>${item.totalPrice}</td>
  </tr>
  </#list>
  </tbody>

  <tfoot>
  <tr>
    <td colspan="3">&nbsp;</td>
    <td><strong>Total</strong></td>
    <td>€${invoice.totalPrice}</td>
  </tr>
  </tfoot>
</table>

<footer>
  <p>
    <em>Baeldung – The Spring Resource | Java, Spring and Web Development tutorials</em>
  </p>
  <p>
    <a href="http://baeldung.com">baeldung.com</a> | <a href="mailto:email@baeldung.com">email@baeldung.com</a>
  </p>
</footer>

<#--<script src="../../../resources/invoice.js"></script>-->
</body>

</html>
