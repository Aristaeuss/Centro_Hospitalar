<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/CSS/navBarUtente.css">
</head>

<div class="sidenav">
        <img class="logo" alt="logo" src="/Images/logo.svg">
        <div class="welcome">
            <div class="welcome-text">Olá de novo,</div>
            <div class="user">${loggedInUser.getPreferredName()}</div>
            <div class="user_type">Utente</div>
        </div>
        <div class="nav-menu">
            <div class="main-menu">
                <a href="/VistaGeralUtente"><img class="button-image" alt="icon" src="/Images/iconHome.png"> Início</a>
                <a href="/perfilUtente"><img class="button-image" alt="icon" src="/Images/iconUserProfile.png"> Perfil</a>
                <a href="/faturasUtente"><img class="button-image" alt="icon" src="/Images/iconReceipt.png"> Faturas</a>
                <a href="/userListMedicos"><img class="button-image" alt="icon" src="/Images/iconStetoscope.png"> Médicos</a>
                <a href="/consultasUtente"><img class="button-image" alt="icon" src="/Images/iconHeartRate.png"> Consultas</a>
            </div>
            <div class="sub-menu">
                <div class="nav-title">Acessos Rápidos</div>
                <a href="/infoUtente"><img class="button-image" alt="icon" src="/Images/iconInfo.png"> Informações Gerais</a>
                <a href="/calendarioUtente"><img class="button-image" alt="icon" src="/Images/iconCalendar.png"> Calendário de Vagas</a>
                <a href="/contactosUtente"><img class="button-image" alt="icon" src="/Images/iconPhone.png"> Contactos</a>
            </div>
        </div>
        <div class="lower-part">
            <img class="upskill-logo-image" src="../Images/iconUpskill.png">
            <hr>
            <div><a href="<c:url value="/logout" />"><img src="../Images/iconLogout.png"/><span>Terminar Sessão</span></a></div>
        </div>

    </div>

</html>