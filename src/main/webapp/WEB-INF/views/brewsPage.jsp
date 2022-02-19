<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BrewDay!</title>
</head>
<body>

		<form action="createBeer" method="GET">
 			<input type="submit" value="Crea Birra"/>
 		</form>

 		<form action="homePage" method="GET">
    	<input type="submit" value="Torna indietro" />
		</form>

 		<div id="lista"></div>

<script>
var x = "";
<c:forEach var="item" items="${spesa}">
		x += "${item}" + "\n";
</c:forEach>
if(x.length > 0)
alert(x);
</script>

<script>
var lista = document.getElementById("lista");
var birre = new Array();
<c:forEach var="birre" items="${listBirre}">
	var text = "${birre}";
	birre.push(text.split(" - "));
</c:forEach>
for(val of birre)
	lista.innerHTML += "<p>Nome ricetta: "+val[0] +" Quantità creata: " +val[1] +"l  Note: " + val[2]+"</p><br>";
</script>

</body>
</html>