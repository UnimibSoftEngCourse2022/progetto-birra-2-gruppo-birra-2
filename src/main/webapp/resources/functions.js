function showError(e) {
    nicknameRE = /^[A-Za-z0-9'.!_#^-~]{3,}$/;
    passwordRE = /^[A-Za-z0-9.!#$%&'*+-/=?^_{|}~]{4,20}$/;

    var nickname = document.getElementById('nickname');
    var password = document.getElementById('password');
    var nicknameError = document.getElementById('nicknameError');
    var passwordError = document.getElementById('passwordError');

    if (!nicknameRE.test(nickname.value) || nickname.value === '') {
        e.preventDefault()
        nickname.style.borderColor = "#FF3B30";
        nicknameError.innerText = "Nickname non inserito o non valido";
    } else {
        nickname.style.borderColor = "white";
        nicknameError.innerText = "";
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