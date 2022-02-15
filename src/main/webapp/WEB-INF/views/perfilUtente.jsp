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
    <%@ include file="navBarUtente.jsp"%>
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
    <div  class="pageIdentifier">PERFIL DE UTENTE</div>

    <div class="transparentBar">

        <div class="content-profile">
            <img class="profileImg" alt="profileImg" id="profileImg" src="${loggedInUser.getUserImage().getPath()}">
            <div class="userName">${loggedInUser.getPreferredName()}</div>
            <div class="category">Utente</div>
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
            <div class="column1 raw7">S. Social:</div>
            <div class="column2">${loggedInUser.getSocialSecurity()}</div>
            <div class="column1 raw8">Nº Utente:</div>
            <div class="column2">${loggedInUser.getPatientNumber()}</div>
        </div>
    </div>

    <div class="popup hidden">
        <div class="title">Altere os dados da sua morada:</div>
        <form id="addressUpdate-form" method="post" action="/alterarMoradaU" >
        <input class="field-input-address" type="text" id="address" name="address" placeholder="Morada" required>
        <input class="field-input-door" type="text" id="door" name="door" placeholder="Nº porta" required>
        <input class="field-input-floor" type="text" id="floor" name="floor" placeholder="Andar" required>
        <input class="field-input-postalCode" type="text" id="postalCode" name="postalCode" placeholder="Código Postal" required>
        <input class="field-input-city" type="text" id="city" name="city" placeholder="Concelho" required>
        <button type="submit" class="adress-submit-button">Enviar</button> 
        </form>
        <button class="close-popup" onclick="togglePopup()">X</button>
    </div>

    <div class="popup1 hidden">
        <div class="title">Altere o seu número de telefone:</div>
        <form id="telephoneUpdate-form" method="post" action="/alterarTelefoneU" >
        <input class="field-input-telephone" type="tel" id="telephone" name="telephone" placeholder="Telefone" required>
        <button type="submit" class="telephone-submit-button">Enviar</button>
        </form>
        <button class="close-popup" onclick="togglePopup1()">X</button> 
    </div>

    <div class="popup2 hidden">
        <div class="title">Altere a sua foto e nome de perfil:</div>
        <div class="profile-image-wrapper">
            <form id="upload-form" method="post" action="/alterarImagemU" enctype="multipart/form-data">
            <input class="image-upload-input" type="file" name="file" accept="image/png, image/jpeg" required>
        </div>
        <button type="submit" class="image-submit-button">Enviar</button>
        </form>
        <form id="preferredName-form" method="post" action="/alterarNomeU">
        <input class="field-input" type="text" id="name" name="name" placeholder="Nome preferencial" required>
        <button type="submit" class="username-submit-button">Enviar</button>
        </form>
        <button class="close-popup" onclick="togglePopup2()">X</button> 
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

</div>
<script type="text/javascript" src="/JS/app.js"></script>
</body>
</html>