<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>BrewDay!</title>
	</head>
	<body>
		<h1>Bentornato in BrewDay!</h1>
		
		<br>
		
		<h3>Inserire i dati di accesso:</h3>
		
		<br>
		
		<form action="login" method="POST">
			<input type="text" id="nickname" name="nickname" value="" pattern="^[A-Za-z0-9'.!_#^-~]{3,}$"
				placeholder="Inserire nickname" required 
	  			oninvalid="this.setCustomValidity('Nickname non valido')"
	  			oninput="this.setCustomValidity('')"/>
	  			
			<br>
			<br>
			<br>
			
			<input type="password" id="password" name="password" value="" pattern="^[A-Za-z0-9.!#$%&'*+-/=?^_`{|}~]{4,20}$"
				placeholder="Inserire password" required
	  			oninvalid="this.setCustomValidity('Password non consentita')"
	  			oninput="this.setCustomValidity('')"/>
	  			
	  		<input type="checkbox" onclick="showPwdFunction()">Mostra Password
	  			
			<br>
			<br>
			
			<input type="submit" id="Accedi" value="Accedi"/>
			
	 	</form>

 		<br>
 		<br>
 		
	 	<form action="signin" method="GET">
	 		<input type="submit" value="Registrati"/>
	 	</form>
	 	
	 	<br>
 		<br>
 		
 		<form action="/BrewDayApp" method="GET">
	 		<input type="submit" value="Torna indietro"/>
	 	</form>
	 	
	 	<script>
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
	 	if( ${alertFlag} == true )
 			alert("Account Inesistente. Premere Ok per riprovare");
 		</script>
	</body>
</html>