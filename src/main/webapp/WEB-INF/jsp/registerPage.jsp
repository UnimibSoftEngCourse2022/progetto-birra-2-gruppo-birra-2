<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrati!</title>
</head>
<body>
<h1>Registrati!</h1>
<br>
<h3>Prego inserisci i tuoi dati:</h3>
<br>
	<form action="/signin" method="POST">
		<label for="nickname">Nickname:</label>
		<br>
		<input type="text" id="nickname" name="nickname" value="" />
		<br>
		<label for="email">Email:</label>
		<br>
		<input type="email" id="email" name="email" value="" />
		<br>
		<label for="pwd">Password:</label>
		<br>
		<input type="password" id="pwd" name="pwd" value="" />
		<br>
		<br>
		<input type="submit" value="Registra"/>
 	</form>
</body>
</html>