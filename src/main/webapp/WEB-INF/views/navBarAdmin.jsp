<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="UTF-8">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/CSS/estilosFuncionario.css">
</head>
<body>
    <div class="sidenav">

        <img class="logo" alt="logo" src="/Images/logo.svg">
        <div class="welcome">
            <div class="welcome-text">Olá de novo,</div>
            <div class="user">${loggedInUser.getPreferredName()}</div>
            <div class="user_type">Administrador</div>
        </div>
        <div class="nav-menu">
            <div class="bookmark"></div>
            <div class="main-menu">
                <a href="/VistaGeralAdmin"><img class="button-image" alt="icon" src="/Images/iconHome.png"><e> Início</e></a>
                <a href="/ghorarios"><img class="button-image" alt="icon" src="/Images/iconScheduleMini.png">Gerir Horários</a>
                <a href="/gespecialidade"><img class="button-image" alt="icon" src="/Images/iconStetoscope.png">Gerir Especialidades</a>
                <a href="/RegistosA"><img class="button-image" alt="icon" src="/Images/iconUsers.png"><e> Registos</e></a>
            </div>
            <br>
            <br>
            <br>
            <br>
        </div>
        <div class="lower-part">
            <img class="upskill-logo-image" src="../Images/iconUpskill.png">
            <hr>
            <div><a href="<c:url value="/logout" />"><img src="../Images/iconLogout.png"/><span>Terminar Sessão</span></a></div>
        </div>

    </div>
</body>
</html>