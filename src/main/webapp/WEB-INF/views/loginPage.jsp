<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>BrewDay!</title>
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

        <spring:url value="/resources/functions.js" var="functionsJS" />
        <spring:url value="/resources/style.css" var="styleCSS" />
        <spring:url value="/resources/login/header.js" var="headerJS" />
        <spring:url value="/resources/login/header.css" var="headerCSS" />
        <spring:url value="/resources/login/login.css" var="loginCSS" />

        <script src="${functionsJS}"></script>
        <link href="${styleCSS}" rel="stylesheet" />
        <script src="${headerJS}"></script>
        <link href="${headerCSS}" rel="stylesheet" />
        <link href="${loginCSS}" rel="stylesheet" />

    </head>
    <body>
        <header-accedi logo="${logoPNG}"></header-accedi>
            <div class="ContainerForm">
                <h1>Benvenuto</h1>
                <form action="login" method="POST" onsubmit="return showError(event)">

                    <label for="nickname">Nickname</label>
                    <input type="text" id="nickname" name="nickname" value="" />
                    <div id="nicknameError"></div>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" value="" />
                    <div id="passwordError"></div>

                    <img src="${disabledPNG}" alt="no image" onclick="showPwdFunction()" id="eyeIcon"></i>

                    <div class="Button">
                        <input class="SigninLogin" type="submit" id="Accedi" value="Accedi in BrewDay!"/>
                    </div>

                    <div id="loginError"></div>

                </form>
            </div>

            <script type="text/javascript">

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
        	 	
                if (${alertFlag} == true) {
                    var error = document.getElementById('loginError');
                    error.innerText = "Forse hai sbagliato qualcosa";
                }
         			
         	</script>
    </body>
</html>
