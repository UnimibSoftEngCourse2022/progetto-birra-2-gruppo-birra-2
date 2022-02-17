<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Brew Day!</title>
</head>
<body>

<h1>Inserire il nome della ricetta che si vuole produrre</h1>

	<form action="showrecipesMake" method="POST">
			<input type="text" id="ricerca" name="ricerca" value="" pattern="^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{3,}$"
				placeholder="Cerca la ricetta che vuoi produrre" required 
	  			oninvalid="this.setCustomValidity('Nome non valido')"
	  			oninput="this.setCustomValidity('')"/>
	  		<input type="hidden" id="autore" name="autore" value="${autore}"/>
	  		&nbsp;&nbsp;
	  	
 			<input type="submit" value="Cerca Ricetta"/>
 	</form>
 	
 	<form action="homePage" method="GET">
	 		<input type="submit" value="Torna indietro"/>
	 	</form>
	 	
	 	
<br>
<br>

<div id="lista"></div>
	
	<script>
		var div = document.getElementById("lista");
		var ricette = new Array();
		<c:forEach var="recipes" items="${listRicette}">
			ricette.push(new Array('${recipes.ID}','${recipes.nome}','${recipes.descrizione}'));                              
		</c:forEach>
		for (const val of ricette)
		   {
				var cont = document.createElement("div");
				cont.innerHTML += "<h2>"+val[1]+"</h2><p>"+val[2]+"</p>";
				cont.onclick = function() {location.href='makeBeerPage?id='+val[0]+'';};
				div.appendChild(cont);
		   }
	</script>
</body>
</html>