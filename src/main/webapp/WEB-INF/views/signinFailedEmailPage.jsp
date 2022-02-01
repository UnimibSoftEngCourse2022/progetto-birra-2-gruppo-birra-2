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
		<h1>Registrati!</h1>
		
		<br>
		
		<h3>Prego inserisci i tuoi dati:</h3>
		
		<br>
		
		<form:form action="signin" method="POST" modelAttribute="u">
		
			<input type="text" id="nickname" name="nickname" value="" pattern="^[A-Za-z0-9'.!_#^-~]{3,}$"
				placeholder="Inserire nickname" required 
	  			oninvalid="this.setCustomValidity('Nickname non valido')"
	  			oninput="this.setCustomValidity('')"/>
			
			<br>
			<br>
			
			<input type="email" id="email" name="email" value="" pattern="^[A-Za-z0-9.!#$%&'*+-/=?^_`{|}~]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"
				placeholder="Inserire email" required
	  			oninvalid="this.setCustomValidity('Email non valida')"
	  			oninput="this.setCustomValidity('')"/>
			
			<br>
			<br>
			
			<input type="password" id="password" name="password" value="" pattern="^[A-Za-z0-9.!#$%&'*+-/=?^_`{|}~]{4,20}$"
				placeholder="Inserire password" required
	  			oninvalid="this.setCustomValidity('Password non consentita')"
	  			oninput="this.setCustomValidity('')"/>
	  			
	  		<input type="checkbox" onclick="showPwdFunction()">Mostra Password
			
			<br>
			<br>
			
			<input type="submit" id="submit" value="Invia"/>
			
	 	</form:form>
	 	
	 	<form action="/BrewDayApp" method="GET">
	 		<input type="submit" value="Torna indietro"/>
	 	</form>

	 	<script type="text/javascript">
	 	
	 	function showPwdFunction() {
	 		  var x = document.getElementById("password");
	 		  if (x.type === "password") {
	 		    x.type = "text";
	 		  } else {
	 		    x.type = "password";
	 		  }
	 		}
	 	</script>
	 	
	 	<script type="text/javascript">
 			alert("Email già associata ad un account. Premere Ok per riprovare");
 		</script>
	 	
	</body>
</html>