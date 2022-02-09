<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>BrewDay!</title>
	</head>
	<body>
		<h1>Inserire i dati della nuova ricetta</h1>
		
		<br>
		
		<form:form action="modifyInfoRecipe" method="POST" modelAttribute="r">
		
			<input type="hidden" id="iD" name="iD" value="${ricettaID}"/>
			
			<input type="text" id="nome" name="nome" value="${Ricetta.nome}" pattern="^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{3,}$"
				placeholder="Come si chiama la ricetta?" required 
	  			oninvalid="this.setCustomValidity('Nome non consentito')"
	  			oninput="this.setCustomValidity('')"/>
			
			<br>
			<br>
	  		
	  		<textarea id="descrizione" name="descrizione">${Ricetta.descrizione}</textarea>
	  			
	  		<br>
			<br>
			
			<textarea id="procedimento" name="procedimento">${Ricetta.procedimento}</textarea>
	  			
	  		<input type="hidden" id="autore" name="autore" value="${autore}"/>
			
			<br>
			<br>
			
			<input type="submit" id="submit" value="Invia"/>
			
	 	</form:form>
	 	
	 	<form action="recipes" method="GET">
	 		<input type="submit" value="Torna indietro"/>
	 	</form>
	 	
	</body>
</html>