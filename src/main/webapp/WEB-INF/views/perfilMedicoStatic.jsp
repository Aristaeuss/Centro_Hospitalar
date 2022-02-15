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
    <div  class="pageIdentifier">PERFIL DE MÉDICO</div>

    <div class="transparentBar">

    <div class="content-profile">
        <img class="profileImg" alt="profileImg" id="profileImg" src="/${doctor.getUserImage().getPath()}">
        <div class="userName">${doctor.getPreferredName()}</div>
        <div class="category">Médico</div>
    </div>

    <div class="perfilInfo">
        <div class="column1 raw1">Nome:</div>
        <div class="column2">${doctor.getName()}</div>
        <div class="column1 raw3">Morada:</div>
        <div class="column2">${doctor.getCompleteAddress()}</div>
        <div class="column1 raw4">E-mail:</div>
        <div class="column2">${doctor.getEmail()}</div>
        <div class="column1 raw5">Telefone:</div>
        <div class="column2">${doctor.getTelephone()}</div>
        <div class="column1 raw6">NIF:</div>
        <div class="column2">${doctor.getNif()}</div>
        <div class="column1 raw8">Nº Ordem:</div>
        <div class="column2">${doctor.getOrderNumber()}</div>
    </div>
    </div>

    <div class="perfilDetailLeft">
        <div class="detailTitle1">Especialidade:</div>
        <div class="detailContent">${doctor.getSpeciality().getName()}</div>
    </div>

    <div class="perfilDetailRight">
        <div class="detailTitle2">Sobre o Médico:</div>
        <div class="detailContent">${doctor.getAboutMe()}</div>
    </div>

</div>
</body>
</html>