<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link rel="stylesheet" href="/CSS/PWRecovery.css">
</head>
<body>
<div class="PW-Recovery-grid">
    <div class="PW-Recovery-flex">
        <figure class="heartLogo">
            <img class="heartLogo" alt="hearLogo" src="/Images/mini-logo.svg">
        </figure>
        <div class="instructions">
            <p>Digite o Email associado ao seu registo</p>
        </div>
        <div class="PW-RecoveryForm">
            <form id="PW-recovery-form" method="post" action="/recuperarAcesso" >
            <span class="errorMessage" style="color: red">${errorMessageEmail}</span>
            <span class="successMessage" style="color: green">${successMessageEmail}</span>
                <input class="field-input" type="email" id="email" name="email" placeholder="Email">
                <button type="submit" class="submitButton">
                    <img class="submit-button-arrow-image" src="../Images/iconArrow.svg">
                    <span class="submit-button">ENVIAR</span>
                </button>
            </form>
            <div class="haveAccount">
                <a href="/login"> login</a>
                <a href="/landingPage"> página inicial</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>