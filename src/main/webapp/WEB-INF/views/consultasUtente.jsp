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
    <%@ include file="navBarUtente.jsp"%>
    <link href="CSS/consultasUtente.css" rel="stylesheet">
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
            <p class="pageIdentifier">CONSULTAS</p>
        </div>

        <div class="transparentCardBar">

        </div>

        <div class="card" id="card1"  >
            <img class="cardImages" id="cardImage1" src="../Images/iconSchedule.png">
            <div class="updatedInfo">
                <h5>PRÓXIMA CONSULTA</h5>
                <c:if test="${nextAppointment!=null}"><p>${nextAppointment.getSlot().getDate()} - ${nextAppointment.getSlot().getDateTime().toLocalTime()}</p></c:if>
                <c:if test="${nextAppointment==null}"><p>Não tem consulta marcada</p></c:if>
                <div class="cardSubText">
                    <p>${numAppointments} consulta(s) marcadas</p>
                </div>
            </div>
            <div class="buttonCard">
                <a href="listaConsultasUtente">VER CONSULTAS</a>
            </div>
        </div>

        <div class="card" id="card2"  >
            <img class="cardImages" id="cardImage1" src="../Images/iconSchedule.png">
            <div class="updatedInfo">
                <br>
                <h5>MARCAR CONSULTA</h5>
                <p> </p>
                <div class="cardSubText">
                    <p> </p>
                    <br>
                    <br>
                </div>
            </div>
            <div class="buttonCard">
                <a href="calendarioUtente">VER VAGAS</a>
            </div>
        </div>

        <div class="card" id="card3"  >
             <img class="cardImages" id="cardImage1" src="../Images/iconSchedule.png">
             <div class="updatedInfo">
                 <h5>CONSULTAS CANCELADAS</h5>
                 <div class="cardSubText">
                     <p>${canceledAppointments.size()} consulta(s) cancelada(s)</p>
                 </div>
             </div>
             <div class="buttonCard">
                 <a href="listaConsultasCanceladasUtente">VER CANCELAMENTOS</a>
             </div>
         </div>

         <div class="card" id="card4"  >
             <img class="cardImages" id="cardImage1" src="../Images/iconSchedule.png">
             <div class="updatedInfo">
                 <h5>HISTÓRICO DE CONSULTAS</h5>
                 <div class="cardSubText">
                     <p>${previousAppointments.size()} consulta(s) realizada(s)</p>
                 </div>
             </div>
             <div class="buttonCard">
                 <a href="listaConsultasPreviasUtente">VER HISTÓRICO</a>
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