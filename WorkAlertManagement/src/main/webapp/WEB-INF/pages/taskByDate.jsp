<!DOCTYPE html>
<html xmlns:th="https://www.thymeleaf.org/">
<head>
<meta charset="ISO-8859-1">
<title>TaskByDate</title>
</head>
<body>
<h1 style="text-align: center;color: blue">TaskByDate</h1>

<form action="/taskByDate" method="POST" >
<pre>
		Enter Date :: <input type="date" name="date"/>
		<input type="submit" value="Get Tasks">
</pre>
</form>
</body>	
</html>