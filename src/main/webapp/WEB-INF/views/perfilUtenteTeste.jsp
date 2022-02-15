<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/CSS/profileEstilos.css">
</head>
<body>

<!--The side navigation bar of the webpage:-->
<div class="sidenav">
    <img class="logo" alt="logo" src="/Images/logo.svg">
    <div class="welcome">
        <div class="welcome-text">Olá de novo,</div>
        <div class="user">${loggedInUser.getPreferredName()}</div>
        <div class="user_type">Categoria</div>
    </div>
    <div class="nav-menu">
        <div class="main-menu">
            <a href="index"><img class="button-image" alt="icon" src="/Images/iconHome.png">Início</a>
            <a href="perfilUtente"><img class="button-image" alt="icon" src="/Images/iconUserProfile.png">Perfil</a>
            <a href="faturas"><img class="button-image" alt="icon" src="/Images/iconReceipt.png">Faturas</a>
            <a href="medicos"><img class="button-image" alt="icon" src="/Images/iconStetoscope.png">Médicos</a>
            <a href="consultas"><img class="button-image" alt="icon" src="/Images/iconHeartRate.png">Consultas</a>
        </div>
        <div class="sub-menu">
            <div class="nav-title">Acessos Rápidos</div>
            <a href="informacoesGerais.html"><img class="button-image" alt="icon" src="/Images/iconInfo.png">Informações Gerais</a>
            <a href="calendarioVagas.html"><img class="button-image" alt="icon" src="/Images/iconCalendar.png">Calendário de Vagas</a>
            <a href="contactos.html"><img class="button-image" alt="icon" src="/Images/iconPhone.png">Contactos</a>
        </div>
    </div>
    <div class="lower-part">
        <img class="upskill-logo-image" src="../Images/iconUpskill.png">
        <hr>
        <div><a href="landingPage.html"><img src="../Images/iconLogout.png"/><span>Terminar Sessão</span></a></div>
    </div>

</div>

<!--The part where the main content of the webpage is located:-->
<div class="main">
    <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
    <div id="headerText" class="fixedText">
        <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
        <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
    </div>
    <div  class="pageIdentifier">PERFIL DE UTENTE</div>

    <div class="content-profile">
        <img class="profileImg" alt="profileImg" id="profileImg" src="../UploadedImages/${loggedInUser.getUserImage().getPath()}">

        <div class="userName">${loggedInUser.getPreferredName()}</div>
        <div class="category">Utente</div>
        <button class="button-container">
            <a href="#" class="button">Alterar perfil</a>
        </button>
    </div>

    <!--Test div-->
    <div id="upload-image-test-div">
        <form id="upload-form" method="post" action="/perfilUtente" enctype="multipart/form-data">
            <input class="image-upload-input" type="file" name="file" accept="image/png, image/jpeg"><br>
            <button type="submit" class="submit-button-container">
                <img class="submit-button-arrow-image" src="../Images/iconArrow.svg">
                <span class="submit-button">SUBMIT</span>
            </button>
            <span class="errorMessage" style="color: red">${maxFileSizeErrorMessage}</span>
        </form>
    </div>

    <div class="perfilInfo">
        <div class="column1 raw1">Nome:</div>
        <div class="column2">${loggedInUser.getName()}</div>
        <div class="column1 raw2">Apelido:</div>
        <div class="column2">Henriques Mafamude</div>
        <div class="column1 raw3">Morada:</div>
        <div class="column2">${loggedInUser.getFullAddress()}</div>
        <div class="column1 raw4">E-mail:</div>
        <div class="column2">${loggedInUser.getEmail()}</div>
        <div class="column1 raw5">Telefone:</div>
        <div class="column2">${loggedInUser.getTelephone()}</div>
        <div class="column1 raw6">NIF:</div>
        <div class="column2">${loggedInUser.getNif()}</div>
        <div class="column1 raw7">S. Social:</div>
        <div class="column2">${loggedInUser.getSocialSecurity()}</div>
        <div class="column1 raw8">Nº Utente:</div>
        <div class="column2">${loggedInUser.getPatientNumber()}</div>
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