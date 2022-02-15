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
    <%@ include file="navBarMedico.jsp"%>
    <link rel="stylesheet" href="../CSS/consultasUtente.css">

</head>
<body>

    <div class="main" >

        <figure >
            <img class="headerIcons" src="../Images/headerTrio.png">
        </figure>

        <div id="headerText" class="fixedText">
            <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
            <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
        </div>

        <div  class="pageIdentifier">
            <p class="pageIdentifier">UTENTES</p>
        </div>

        <div class="transparentCardBar">
        </div>

        <div class="card" id="card1"  >
            <div class="updatedInfo">
                <p><b>PROCURAR UTENTE</b></h3>
                    <div class="cardSubText">
                        <p>Pesquisa por nome</p>
                    </div>
            </div>
            <form class="inputInfoCard" method="post" action="/pesquisaUtenteM">
                <span class="errorMessage" style="color: red">${errorMessage}</span>
                <input type="text" id="name" name="name" placeholder="Nome">
                <div class="submitButtonCard">
                    <button class="submitButtonCard" type="submit" value="Pesquisar Utente">Pesquisar utente</button>
                </div>
            </form>
        </div>


        <div class="card" id="card2"  >
            <img class="cardImages" id="cardImage1" src="../Images/iconStetoscope.png">
            <div class="updatedInfo">
                <h5>UTENTES</h5>
                <p>${date}</p>
                <div class="cardSubText">
                    <p>Tem ${appointments.size()} consultas marcadas para hoje</p>
                </div>
            </div>
            <div class="buttonCard">
                <a href="listaUtentesMedicoHoje">VER UTENTES</a>
            </div>
        </div>

    </div>
</body>
</html>