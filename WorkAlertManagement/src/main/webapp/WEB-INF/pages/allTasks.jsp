<html xmlns:th="https://www.thymeleaf.org/">

<head>

<meta charset="ISO-8859-1">
<title>All Tasks</title>
</head>
<body>
<h1 style="text-align: center;color: blue">All Tasks</h1>

<table border="1">
	<tr>
		<th>Id</th>
		<th>Task1</th>
		<th>Task2</th>
		<th>Task3</th>
		<th>Task4</th>
		<th>Task Date</th>
	</tr><br>
	<tr><th>a</th></tr>
	<tr th:each="ob:${taskList}">
		<td th:text="${ob.id}"></td>
		<td th:text="${ob.task1}"></td>
		<td th:text="${ob.task2}"></td>
		<td th:text="${ob.task3}"></td>
		<td th:text="${ob.task4}"></td>
		<td th:text="${ob.taskDate}"></td>
	</tr>
</table>	
</body>	
</html>