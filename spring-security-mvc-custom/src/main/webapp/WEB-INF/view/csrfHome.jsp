<html>
<head></head>

<body>
	<h1>CSRF test on Origin</h1>
	<a href="transfer?accountNo=1234&amount=100">Transfer Money to John</a>
	
	<form action="transfer" method="POST">
       <label>Account Number</label> <input name="accountNo" type="number"/>
       <label>Amount</label>         <input name="amount" type="number"/>
       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
       <input type="submit">
	</form>
</body>
</html>