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
            <p>Digite uma nova password e confirme</p>
        </div>
        <div class="PW-RecoveryForm">
            <form id="PW-change-form" method="post" action="/alterarPasswordp" >
                <input type="hidden" id="token" name="token" value="${token}">
                <span class="errorMessage" style="color: red">${errorMessagePassword}</span>
                <input class="field-input" type="password" id="password" name="password" placeholder="Nova password">
                <input class="field-input" type="password" id="matchPassword" name="matchPassword" placeholder="Confirme a password">
                <button type="submit" class="submitButton">
                    <img class="submit-button-arrow-image" src="../Images/iconArrow.svg">
                    <span class="submit-button">ALTERAR</span>
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