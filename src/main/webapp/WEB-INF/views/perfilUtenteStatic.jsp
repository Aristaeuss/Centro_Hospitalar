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
    <%@ include file="navBarFuncionario.jsp"%>
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
            <img class="profileImg" alt="profileImg" id="profileImg" src="../${patient.getUserImage().getPath()}">
            <div class="userName">${patient.getPreferredName()}</div>
            <div class="category">Utente</div>
        </div>

        <div class="perfilInfo">
            <div class="column1 raw1">Nome:</div>
            <div class="column2">${patient.getName()}</div>
            <div class="column1 raw3">Morada:</div>
            <div class="column2">${patient.getCompleteAddress()}</div>
            <div class="column1 raw4">E-mail:</div>
            <div class="column2">${patient.getEmail()}</div>
            <div class="column1 raw5">Telefone:</div>
            <div class="column2">${patient.getTelephone()}</div>
            <div class="column1 raw6">NIF:</div>
            <div class="column2">${patient.getNif()}</div>
            <div class="column1 raw7">S. Social:</div>
            <div class="column2">${patient.getSocialSecurity()}</div>
            <div class="column1 raw8">Nº Utente:</div>
            <div class="column2">${patient.getPatientNumber()}</div>
        </div>
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
</body>
</html>