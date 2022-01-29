<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>BrewDay!</title>
</head>
<body>
<h1>Benvenuto in BrewDay!</h1>
<br>
<h3>Inserire i dati di accesso:</h3>
<br>
	<form action="/login" method="POST" onSubmit="return check();">
		<input type="text" id="nickname" name="nickname" value="" required placeholder="Inserire nickname"
  			oninvalid="this.setCustomValidity('Nickname mancante')"
  			oninput="this.setCustomValidity('')"/>
		<br>
		<input type="password" id="pwd" name="pwd" value="" required placeholder="Inserire password"
  			oninvalid="this.setCustomValidity('Password mancante')"
  			oninput="this.setCustomValidity('')"/>
  			<input type="checkbox" onclick="myFunction()">Mostra Password
		<br>
		<br>
		<input type="submit" id="Accedi" value="Accedi"/>
 	</form>
 	
 	<br>
 	<form action="/signin" method="GET">
 	<input type="submit" value="Registrati"/>
 	</form>
 	<script>
 	function myFunction() {
 		  var x = document.getElementById("pwd");
 		  if (x.type === "password") {
 		    x.type = "text";
 		  } else {
 		    x.type = "password";
 		  }
 		}
 	</script>
</body>
</html>