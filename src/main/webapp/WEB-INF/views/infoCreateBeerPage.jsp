<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="makebeer" method="POST">
			<p>Nome ricetta : ${Ricetta.nome}</p>
			<input type="number" min=0 max=50 id="quantita" name="quantita" value="" 
				placeholder="Quantità" required />
	  		<input type="hidden" id="autore" name="autore" value="${autore}"/>
	  		<input type="hidden" id="idricetta" name="idricetta" value="${IDRicetta}"/>
	  		&nbsp;&nbsp;
	  		<textarea id="note" name="note"></textarea>
	  		
 			<input type="submit" value="Crea birra"/>
 	</form>

<form action=createBeer method="GET">
    <input type="submit" value="Torna indietro" />
</form>

</body>
</html>