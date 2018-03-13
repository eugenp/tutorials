<%--
 Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
  <head>
    <title>JSP 2.0 Examples - Display Products Tag File</title>
  </head>
  <body>
    <h1>JSP 2.0 Examples - Display Products Tag File</h1>
    <hr>
    <p>This JSP page invokes a tag file that displays a listing of
    products.  The custom tag accepts two fragments that enable
    customization of appearance.  One for when the product is on sale
    and one for normal price.</p>
    <p>The tag is invoked twice, using different styles</p>
    <hr>
    <h2>Products</h2>
    <tags:displayProducts>
      <jsp:attribute name="normalPrice">
        Item: ${name}<br/>
        Price: ${price}
      </jsp:attribute>
      <jsp:attribute name="onSale">
        Item: ${name}<br/>
        <font color="red"><strike>Was: ${origPrice}</strike></font><br/>
        <b>Now: ${salePrice}</b>
      </jsp:attribute>
    </tags:displayProducts>
    <hr>
    <h2>Products (Same tag, alternate style)</h2>
    <tags:displayProducts>
      <jsp:attribute name="normalPrice">
        <b>${name}</b> @ ${price} ea.
      </jsp:attribute>
      <jsp:attribute name="onSale">
        <b>${name}</b> @ ${salePrice} ea. (was: ${origPrice})
      </jsp:attribute>
    </tags:displayProducts>
  </body>
</html>
