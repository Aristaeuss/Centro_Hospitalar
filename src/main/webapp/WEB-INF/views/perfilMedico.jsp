<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <%@ include file="navBarMedico.jsp"%>
    <link rel="stylesheet" href="/CSS/profileEstilos.css">

</head>
<body>


<!--The part where the main content of the webpage is located:-->
<div class="main">
    <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
    <div id="headerText" class="fixedText">
        <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
        <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
    </div>
    <div  class="pageIdentifier">PERFIL DE MÉDICO</div>

    <div class="transparentBar">

    <div class="content-profile">
        <img class="profileImg" alt="profileImg" id="profileImg" src="${loggedInUser.getUserImage().getPath()}">
        <div class="userName">${loggedInUser.getPreferredName()}</div>
        <div class="category">Médico</div>
        <button class="button-container" onclick="togglePopup2()">ALTERAR</button>
    </div>

    <div class="perfilInfo">
        <div class="column1 raw1">Nome:</div>
        <div class="column2">${loggedInUser.getName()}</div>
        <div class="column1 raw3">Morada:</div>
        <div class="column2">${loggedInUser.getCompleteAddress()}<button class="change-button" onclick="togglePopup()">Alterar</button></div>
        <div class="column1 raw4">E-mail:</div>
        <div class="column2">${loggedInUser.getEmail()}</div>
        <div class="column1 raw5">Telefone:</div>
        <div class="column2">${loggedInUser.getTelephone()}<button class="change-button" onclick="togglePopup1()">Alterar</button></div>
        <div class="column1 raw6">NIF:</div>
        <div class="column2">${loggedInUser.getNif()}</div>
        <div class="column1 raw8">Nº Ordem:</div>
        <div class="column2">${loggedInUser.getOrderNumber()}</div>
    </div>
    </div>
    <div class="popup-wrapper">
    <div class="popup hidden">
        <div class="title">Altere os dados da sua morada:</div>
        <form id="addressUpdate-form" method="post" action="/alterarMoradaM" >
        <input class="field-input-address" type="text" id="address" name="address" placeholder="Morada" required>
        <input class="field-input-door" type="text" id="door" name="door" placeholder="Nº porta" required>
        <input class="field-input-floor" type="text" id="floor" name="floor" placeholder="Andar" required>
        <input class="field-input-postalCode" type="text" id="postalCode" name="postalCode" placeholder="Código Postal" required>
        <input class="field-input-city" type="text" id="city" name="city" placeholder="Concelho" required>
        <button type="submit" class="adress-submit-button">Enviar</button>
        </form>
        <button class="close-popup" onclick="togglePopup()">X</button>
    </div>
    </div>
    <div class="popup1 hidden">
        <div class="title">Altere o seu número de telefone:</div>
        <form id="telephoneUpdate-form" method="post" action="/alterarTelefoneM" >
        <input class="field-input-telephone" type="tel" id="telephone" name="telephone" placeholder="Telefone" required>
        <button type="submit" class="telephone-submit-button">Enviar</button>
        </form>
        <button class="close-popup" onclick="togglePopup1()">X</button>
    </div>

    <div class="popup2 hidden">
        <div class="title">Altere a sua foto e nome de perfil:</div>
        <div class="profile-image-wrapper">
            <form id="upload-form" method="post" action="/alterarImagemM" enctype="multipart/form-data">
            <input class="image-upload-input" type="file" name="file" accept="image/png, image/jpeg" required>
        </div>
        <button type="submit" class="image-submit-button">Enviar</button>
        </form>
        <form id="preferredName-form" method="post" action="/alterarNomeM">
        <input class="field-input" type="text" id="name" name="name" placeholder="${loggedInUser.getPreferredName()}" required>
        <button type="submit" class="username-submit-button">Enviar</button>
        </form>
        <button class="close-popup" onclick="togglePopup2()">X</button>
    </div>

    <div class="popup3 hidden">
        <div class="title">Altere a sua descrição:</div>
        <form id="aboutMe-form" method="post" action="/alterarSobre">
        <textarea class="field-input-aboutMe" id="about-me" name="aboutMe" maxlength="500" placeholder="${loggedInUser.getAboutMe()}"></textarea>
        <button type="submit" class="aboutMe-submit-button">Enviar</button>
        </form>
        <button class="close-popup" onclick="togglePopup3()">X</button>
    </div>


    <div class="perfilDetailLeft">
        <div class="detailTitle1">Especialidade:</div>
        <div class="detailContent">${loggedInUser.getSpeciality().getName()}</div>
    </div>

    <div class="perfilDetailRight">
        <div class="detailTitle2">Sobre o Médico:</div>
        <div class="detailContent">${loggedInUser.getAboutMe()} <button class="change-button" onclick="togglePopup3()">Alterar</button></div>
    </div>

</div>
<script type="text/javascript" src="/JS/app.js"></script>
</body>
</html>