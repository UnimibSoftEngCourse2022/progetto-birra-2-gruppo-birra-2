<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
</head>
<body>

	<form action="recipes" method="GET">
 		<input type="submit" value="Gestisci Ricette"/>
 	</form>
 	
 	<input type="button" onclick="location.href='editUserIng?nick=${autore}';" value="Gestisci Ingredienti" />
&nbsp;&nbsp;&nbsp;
<input type="button" onclick="location.href='editUserEquip?nick=${autore}';" value="Gestisci Attrezzatura" />
&nbsp;&nbsp;&nbsp;
	
	<form action="logout" method="GET">
 		<input type="submit" value="Log out"/>
 	</form>
</body>
</html>