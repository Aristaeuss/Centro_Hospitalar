<!DOCTYPE html>
<html lang="en">

<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <fmt:setBundle basename="messages" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <link rel="stylesheet" href="../CSS/loginEstilos.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>

</head>

<body>
    <div id="main-div">
        <img id="top-logo-image" src="../Images/logo.svg">
        <div id="center-login-box">
            <form id="login-form" method="post" action="/login" >
                <div class="fields-container">

                    <div class="field-container" id="email-container">
                    <span class="errorMessage" style="color:red;"> ${errorMessage}</span>
                        <input name="username" class="field-input" id="username-field" type="text" placeholder="Username">
                    </div>
                    <div class="field-container" id="password-container">
                        <input name="password" class="field-input" id="password-field" type="password" placeholder="Password"><br>
                        <a href="recuperarAcesso">Esqueceu-se da password?</a>
                    </div>
                </div>
                <button type="submit" class="submit-button-container">
                    <img class="submit-button-arrow-image" src="../Images/iconArrow.svg">
                    <span class="submit-button">ENTRAR</span>
                </button>
                <p class="register-suggestion">Não tem conta? <a href="registo">Registe-se aqui!</a></p>
            </form>
        </div>

        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active" id="carousel1">
                <img class="d-block w-100" class="carouselImage" src="../Images/bottomMessage.png" alt="First slide">
            </div>
            <div class="carousel-item"id="carousel2">
                <img class="d-block w-100" class="carouselImage" src="../Images/bottomMessage.png" alt="Second slide">
            </div>
            <div class="carousel-item" id="carousel3">
                <img class="d-block w-100" class="carouselImage" src="../Images/bottomMessage.png" alt="Third slide">
            </div>
        </div>
            <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only"></span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only"></span>
            </a>

    </div>
</body>
<script type="text/javascript">
function validate() {
    if (document.login-form.username.value == "" && document.f.password.value == "") {
        alert("Username and password are required");
        document.f.username.focus();
        return false;
    }
    if (document.login-form.username.value == "") {
        alert("Username is required");
        document.f.username.focus();
        return false;
    }
    if (document.login-form.password.value == "") {
	alert("Password is required");
	document.f.password.focus();
        return false;
    }
}
</script>
</html>