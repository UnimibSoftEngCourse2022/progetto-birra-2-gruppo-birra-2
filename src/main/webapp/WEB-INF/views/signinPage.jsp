<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Registrati</title>
        <meta charset="UTF-8"/>

		<!-- Custom Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Sora:wght@100;200;300;400;500;600;700;800&display=swap" rel="stylesheet">

        <!-- External Files -->
        <spring:url value="/resources/assets/eye.disable.png" var="disabledPNG" />
        <spring:url value="/resources/assets/eye.able.png" var="ablePNG" />
        <spring:url value="/resources/assets/logo.png" var="logoPNG" />

        <spring:url value="/resources/style.css" var="styleCSS" />
        <spring:url value="/resources/login/header.js" var="headerJS" />
        <spring:url value="/resources/login/header.css" var="headerCSS" />
		<spring:url value="/resources/login/login.css" var="loginCSS" />
        <spring:url value="/resources/signin/signin.css" var="signinCSS" />

        <link href="${styleCSS}" rel="stylesheet" />
        <script src="${headerJS}"></script>
        <link href="${headerCSS}" rel="stylesheet" />
		<link href="${loginCSS}" rel="stylesheet" />
		<link href="${signinCSS}" rel="stylesheet" />

	</head>
	<body>
		
		<header-accedi logo="${logoPNG}" value="Accedi" submitTo="login"></header-accedi>
            <div class="ContainerForm">
                <h1>Benvenuto</h1>
                <form action="signin" method="POST" modelAttribute="u" onsubmit="return showError(event)">

                    <label for="nickname">Nickname</label>
                    <input type="text" id="nickname" name="nickname" value="" />
                    <div id="nicknameError"></div>

					<label for="email">Email</label>
                    <input type="text" id="email" name="email" value="" />
                    <div id="emailError"></div>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" value="" />
                    <div id="passwordError"></div>

                    <img src="${disabledPNG}" alt="no image" onclick="showPwdFunction()" id="eyeIcon"></i>

                    <div class="Button">
                        <input class="SigninLogin" type="submit" value="Crea il mio account"/>
                    </div>

                    <div id="accessError"></div>

                </form>
            </div>

	 	<script>

			function showPwdFunction() {
				var passwordInput = document.getElementById("password");
				var eyeIcon = document.getElementById("eyeIcon")

				if (passwordInput.type === "password") {
					passwordInput.type = "text";
					eyeIcon.src = "${ablePNG}";
				} else {
					passwordInput.type = "password";
					eyeIcon.src = "${disabledPNG}";
				}
			}

			function showError(e) {
				nicknameRE = /^[A-Za-z0-9'.!_#^~-]{3,}$/;
				emailRE = /^[A-Za-z0-9.!#$%&'*+-/=?^_`{|}~]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
				passwordRE = /^[A-Za-z0-9.!#$%&'*+-/=?^_`{|}~]{4,20}$/;

				var nickname = document.getElementById('nickname');
				var password = document.getElementById('password');
				var email = document.getElementById('email')
				var nicknameError = document.getElementById('nicknameError');
				var passwordError = document.getElementById('passwordError');
				var emailError = document.getElementById('emailError');

				if (!nicknameRE.test(nickname.value) || nickname.value === '') {
					e.preventDefault()
					nickname.style.borderColor = "#FF3B30";
					nicknameError.innerText = "Nickname non inserito o non valido";
				} else {
					nickname.style.borderColor = "white";
					nicknameError.innerText = "";
				}

				if (!emailRE.test(email.value) || email.value === '') {
					e.preventDefault()
					email.style.borderColor = "#FF3B30";
					emailError.innerText = "Email non inserita o non valida";
				} else {
					email.style.borderColor = "white";
					emailError.innerText = "";
				}

				if (!passwordRE.test(password.value) || password.value === '') {
					e.preventDefault()
					password.style.borderColor = "#FF3B30";
					passwordError.innerText = "Password non inserita o non valida";
				} else {
					password.style.borderColor = "white";
					passwordError.innerText = "";
				}
			}
			
		</script>
	 	
	 	<script type="text/javascript">

			var error = document.getElementById('accessError');

			if (${alertFlagNick} == true)
				error.innerText = "Nickname non disponibile";
			else if (${alertFlagEmail} == true)
				error.innerText = "Esiste un account con questa email";

 		</script>
	 	
	</body>
</html>