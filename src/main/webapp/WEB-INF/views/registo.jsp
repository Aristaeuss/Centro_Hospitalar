<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link rel="stylesheet" href="/CSS/nunoEstilos.css"> 

</head>
<body>
<div class="main">
        <div class="register-flex">
            <figure class="heartLogo">
                <img class="heartLogo" alt="hearLogo" src="/Images/mini-logo.svg">
            </figure>
            <div class="instructions">Efetue o registo e aguarde o email de confirmação:</div>
            <div class="registerForm-wrapper">
                <form class="registerForm" id="register-form" method="post" action="/registo" modelAttribute="User">
                    <div class="first-line-wrapper">
                    <input class="field-input name" type="text" id="name" name="name" placeholder="Nome completo" required>
                    <input class="field-input preferredName" type="text" id="preferredName" name="preferredName" placeholder="Indique os 2 nomes por que deseja ser tratado" required>
                    <span class="errorMessage" style="color: red">${errorMessageDob}</span>
                    <div class="label-wrapper">
                    <label for="dob" id="dob-label" class="dob-label">Data de Nascimento:</label>
                    <input class="field-input-dob" type="date" id="dob" name="dob" placeholder="Data de Nascimento" required>
                    </div>
                    </div>
                    <div class="second-line-wrapper">
                    <div class="socialSecurity-wrapper">
                    <span class="errorMessage" style="color: red">${errorMessageSocialSecurity}</span>
                    <input class="field-input socialSecurity" type="number" id="socialSecurity" name="socialSecurity" placeholder="Segurança Social" required>
                    </div>
                    <div class="patientNumber-wrapper">
                    <span class="errorMessage" style="color: red">${errorMessagePatientNumber}</span>
                    <input class="field-input patientNumber" type="number" id="patientNumber" name="patientNumber" placeholder="Número de Utente" required>
                    </div>
                    <div class="nif-wrapper">
                    <span class="errorMessage" style="color: red">${errorMessageNif}</span>
                    <input class="field-input nif" type="number" id="nif" name="nif" placeholder="NIF" required>
                    </div>
                    </div>
                    <div class="third-line-wrapper">
                    <input class="field-input telephone" type="number" id="telephone" name="telephone" placeholder="Telefone" required>
                    <input class="field-input address" type="text" id="address" name="address" placeholder="Morada" required>
                    </div>
                    <div class="fourth-line-wrapper">
                    <input class="field-input doorNumber" type="text" id="doorNumber" name="doorNumber" placeholder="Nº porta" required>
                    <input class="field-input floor" type="text" id="floor" name="floor" placeholder="Andar">
                    <input class="field-input postalCode" type="text" id="postalCode" name="postalCode" placeholder="Código Postal" required>
                    <input class="field-input city" type="text" id="city" name="city" placeholder="Concelho" required>
                    </div>
                    <div class="fifth-line-wrapper">
                    <div class="email-wrapper">
                    <span class="errorMessage" style="color: red">${errorMessageEmail}</span>
                    <input class="field-input email" type="email" id="email" name="email" placeholder="Email" required>
                    </div>
                    </div>
                    <div class="sixth-line-wrapper">
                    <div class="username-wrapper">
                    <span class="errorMessage" style="color: red">${errorMessageUsername}</span>
                    <input class="field-input" type="text" id="username" name="username" placeholder="Nome de Utilizador" required>
                    </div>
                    <input class="field-input password" type="password" id="password" name="password" placeholder="Password" required>
                    <div class="confirmPassword-wrapper">
                    <span class="errorMessage" style="color: red">${errorMessagePassword}</span>
                    <input class="field-input" type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirme a password" required>
                    </div>
                    </div>
                    <div class="checkbox">
                    <input class="field-input" type="checkbox" id="agreeTC" name="agreeTC" required>
                    <label for="agreeTC" class="tcText">Li e aceito os Termos e Condições e Política de Privacidade</label>
                    </div>
                    <button type="submit" class="submitButton">
                        <span class="submit-button">REGISTAR</span>
                    </button>
                </form>
                <div class="haveAccount">
                    Já está registado?<a class="haveAccount" href="login">Aceda à sua conta!</a>
                    
                </div>
            </div>
        </div>
</div>
</body>
</html>