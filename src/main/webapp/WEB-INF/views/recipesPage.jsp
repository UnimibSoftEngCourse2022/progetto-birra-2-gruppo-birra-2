<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BrewDay!</title>
</head>
<body>

	<form action="Addrecipes" method="GET">
 		<input type="submit" value="Aggiungi Ricetta"/>
 	</form>
 	
 	<form action="homePage" method="GET">
	 		<input type="submit" value="Torna indietro"/>
	 	</form>

	<form action="showrecipes" method="POST">
		<input type="text" id="ricerca" name="ricerca" value="" pattern="^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{3,}$"
				placeholder="Come si chiama la ricetta?" required 
	  			oninvalid="this.setCustomValidity('Nome non valido')"
	  			oninput="this.setCustomValidity('')"/>
	  	<input type="hidden" id="autore" name="autore" value="${autore}"/>
	  		&nbsp;&nbsp;
	  	
 		<input type="submit" value="Cerca Ricetta"/>
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
				cont.onclick = function() {location.href='editRecipe?id='+val[0]+'';};
				div.appendChild(cont);
		   }
	</script>

</body>
</html>