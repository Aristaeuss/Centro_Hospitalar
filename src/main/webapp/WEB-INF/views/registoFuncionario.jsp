<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link rel="stylesheet" href="/CSS/nunoEstilos.css"> 

</head>
<body>
    
    <div class="register-grid">
        <div class="register-flex">
            <figure class="heartLogo">
                <img class="heartLogo" alt="hearLogo" src="/Images/mini-logo.svg">
            </figure>
            <div class="instructions">
                <p>Bem-vindo colaborador(a), faça aqui o registo na nossa plataforma</p>
            </div>
            <div class="registerForm">
                <form class="registerForm" id="register-form" method="post" action="/registoF" modelAttribute="User">
                    <input class="field-input" type="text" id="name" name="name" placeholder="Nome completo">
                    <input class="field-input" type="text" id="preferredName" name="preferredName" placeholder="Indique os 2 nomes por que deseja ser tratado">
                    <span class="errorMessage" style="color: red">${errorMessageDob}</span>
                    <label for="dob" id="dob-label">Data de Nascimento</label>
                    <input class="field-input" type="date" id="dob" name="dob" placeholder="Data de Nascimento">
                    <span class="errorMessage" style="color: red">${errorMessageNif}</span>
                    <input class="field-input" type="number" id="nif" name="nif" placeholder="NIF">
                    <input class="field-input" type="number" id="iban" name="iban" placeholder="IBAN">
                    <input class="field-input" type="number" id="telephone" name="telephone" placeholder="Telefone">
                    <input class="field-input" type="text" id="address" name="address" placeholder="Morada">
                    <input class="field-input" type="text" id="doorNumber" name="doorNumber" placeholder="Nº porta">
                    <input class="field-input" type="text" id="floor" name="floor" placeholder="Andar">
                    <input class="field-input" type="text" id="postalCode" name="postalCode" placeholder="Código Postal">
                    <input class="field-input" type="text" id="city" name="city" placeholder="Concelho">
                    <span class="errorMessage" style="color: red">${errorMessageEmail}</span>
                    <input class="field-input" type="email" id="email" name="email" placeholder="Email">
                    <div class="separator">
                        - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                    </div>
                    <span class="errorMessage" style="color: red">${errorMessageUsername}</span>
                    <input class="field-input" type="text" id="username" name="username" placeholder="Nome de Utilizador">
                    <input class="field-input" type="password" id="password" name="password" placeholder="Password">
                    <span class="errorMessage" style="color: red">${errorMessagePassword}</span>
                    <input class="field-input" type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirme a password">
                    <div class="checkbox">
                    <input class="field-input" type="checkbox" id="agreeTC" name="agreeTC">
                    <label for="agreeTC" class="tcText">Li e aceito os Termos e Condições e Política de Privacidade</label>
                    </div>
                    <button type="submit" class="submitButton">
                        <img class="submit-button-arrow-image" src="../Images/iconArrow.svg">
                        <span class="submit-button">REGISTAR</span>
                    </button>
                </form>
                <div class="haveAccount">
                    Já está registado?<a class="haveAccount" href="login">Acesse à sua conta!</a>
                    
                </div>
            </div>


        </div>


    </div>
    

</body>
</html>