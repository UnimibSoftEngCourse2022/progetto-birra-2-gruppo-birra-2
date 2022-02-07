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
		
		<form:form action="Addrecipes" method="POST" modelAttribute="r">
		
			<input type="hidden" id="iD" name="iD" value="0"/>
			
			<input type="text" id="nome" name="nome" value="" pattern="^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{3,}$"
				placeholder="Come si chiama la ricetta?" required 
	  			oninvalid="this.setCustomValidity('Nome non consentito')"
	  			oninput="this.setCustomValidity('')"/>
			
			<br>
			<br>
			
			<input type="text" id="descrizione" name="descrizione" value="" pattern="^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{1,}$"
				placeholder="Descrizione personale della ricetta" required 
	  			oninvalid="this.setCustomValidity('Caratteri non consentiti')"
	  			oninput="this.setCustomValidity('')"/>
	  			
	  		<br>
			<br>
			
			<input type="text" id="procedimento" name="procedimento" value="" pattern="^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{1,}$"
				placeholder="Spiega qui i passaggi per crearla" required 
	  			oninvalid="this.setCustomValidity('Caratteri non consentiti')"
	  			oninput="this.setCustomValidity('')"/>
	  			
	  		<input type="hidden" id="autore" name="autore" value="${autore}"/>
			
			<br>
			<br>
			
			<input type="submit" id="submit" value="Invia"/>
			
	 	</form:form>
	 	
	 	<form action="/BrewDayApp" method="GET">
	 		<input type="submit" value="Torna indietro"/>
	 	</form>
	 	
	</body>
</html>