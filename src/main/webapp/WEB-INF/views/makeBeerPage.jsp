<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>BrewDay!</title>
</head>
<body>
 	<h2>${Ricetta.nome}</h2>
 	<br>
 	<p>${Ricetta.autore}<p>
 	<br>
 	<br>
 	<p>${Ricetta.descrizione}<p>
 	<br>
 	<br>
 	<p>${Ricetta.procedimento}<p>
 	<br>
 	<br>
 	<h4>INGREDIENTI</h4>
 	
				<c:forEach var="component" items="${listRecComponents}" varStatus="status">
	        		<p>${component}<p>
				</c:forEach>
	<h4>ATTREZZATURA</h4>
 	
				<c:forEach var="tool" items="${listRecTools}" varStatus="status">
	        		<p>${tool}<p>
				</c:forEach>
<br>
<br>				

<form action="createBeer" method="GET">
    <input type="submit" value="Torna indietro" />
</form>


	<form action="makebeer" method="POST">
			<input type="number" min=0 max=50 id="quantita" name="quantita" value="" 
				placeholder="Quantità" required />
	  		<input type="hidden" id="autore" name="autore" value="${autore}"/>
	  		<input type="hidden" id="idricetta" name="idricetta" value="${IDRicetta}"/>
	  		&nbsp;&nbsp;
	  		<textarea id="note" name="note"></textarea>
	  		
 			<input type="submit" value="Crea birra"/>
 	</form>
</body>
</html>