<!DOCTYPE html>
<html xmlns:th="https://www.thymeleaf.org/">
<head>
<meta charset="ISO-8859-1">
<title>Task Page</title>
</head>
<body>
<h1 style="text-align: center;color: blue">Task Page</h1>

<form action="/save" method="POST" >
<pre>
		Task 1 :: <input type="text" name="task1"/>
		Task 2 :: <input type="text" name="task2"/>
		Task 3 :: <input type="text" name="task3"/>
		Task 4 :: <input type="text" name="task4"/>
		<input type="submit" value="Add to TODO List">
</pre>
</form>
<b>${message}</b>
</body>	
</html>