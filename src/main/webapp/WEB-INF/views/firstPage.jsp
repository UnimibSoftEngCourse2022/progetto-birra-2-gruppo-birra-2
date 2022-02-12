<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>BrewDay!</title>

		<!-- Custom Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Sora:wght@100;200;300;400;500;600;700;800&display=swap" rel="stylesheet">

        <!-- External Files -->
        <spring:url value="/resources/assets/+.png" var="plusPNG" />
        <spring:url value="/resources/assets/lente.png" var="lentePNG" />
        <spring:url value="/resources/assets/logo.png" var="logoPNG" />
		<link href="${logoPNG}" rel="icon" />

	</head>
	<body>
		<h3>Logo BrewDay!</h3>
		
		<div style="text-align: center">
  			Benvenuto in BrewDay!
  		</div>
		
		<div style="position: absolute; top: 0; right: 0;  width: 100px; text-align:right;">
			<form action="login" method="GET">
				<input type="submit" id="login" value="Log in"/>
		 	</form>
		 	
		 	<form action="signin" method="GET">
				<input type="submit" id="signin" value="Sign in"/>
		 	</form>
  		</div>
  		
  		<br>
  		<br>
  		
  		<div style="text-align: center">
  			Presentazione sito bla bla bla
  		</div>
	</body>
</html>