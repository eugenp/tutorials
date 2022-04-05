<html>
    <head>
      <title>All fruit!</title>
    </head>
    <body>
      <h1>All fruit!</h1>
      <p>Fruits:</p>
      <ul>
		<#list items as fruit>
			<li>${fruit.name}</li>
		</#list>
	  </ul>	
    </body>
</html>