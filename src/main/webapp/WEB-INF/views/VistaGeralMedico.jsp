<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="navBarMedico.jsp"%>
    <link href="CSS/VGMedico.css" rel="stylesheet">

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
            <p class="pageIdentifier">VISTA GERAL</p>
        </div>

        <div class="transparentCardBar">

        </div>

        <div class="card" id="card1"  >
            <img class="cardImages" id="cardImage1" src="../Images/iconStetoscope.png">
            <c:choose>
                <c:when test="${doctorTicket!=null}">
                   <div class="updatedInfo">
                        <h5>SENHA ATUAL</h5>
                        <p>Chamada: ${doctorTicket.getCalledTime().toLocalTime()}</p>
                        <div class="cardSubText">
                            <p>${doctorTicket.getScheduledAppointment().getPatient().getPreferredName()}</p>
                        </div>
                    </div>
                    <div class="buttonCard">
                        <a href="MedicoConsulta">VOLTAR PARA CONSULTA</a>
                    </div>
                </c:when>
                <c:when test="${nextTicket!=null}">
                   <div class="updatedInfo">
                        <h5>PRÓXIMA SENHA</h5>
                        <p>Início Previsto: ${nextTicket.getScheduledAppointment().getSlot().getDateTime().toLocalTime()}</p>
                        <div class="cardSubText">
                            <c:if test="${isNextTicketLate==false}">
                            <p>${nextTicket.getScheduledAppointment().getPatient().getPreferredName()} chegou a horas</p>
                            </c:if>
                            <c:if test="${isNextTicketLate==true}">
                                <p>${nextTicket.getScheduledAppointment().getPatient().getPreferredName()} está atrasado</p>
                            </c:if>
                        </div>
                    </div>
                    <div class="buttonCard">
                        <a href="MedicoConsulta">CHAMAR SENHA</a>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="updatedInfo">
                        <h5>PRÓXIMA SENHA</h5>
                        <p>Não tem utentes em espera</p>
                        <div class="cardSubText">
                            <p></p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="card" id="card2"  >
            <img class="cardImages" id="cardImage1" src="../Images/iconStetoscope.png">
            <div class="updatedInfo">
                <h5>UTENTES EM ESPERA</h5>
                <p></p>
                <div class="cardSubText">
                    <p>Tem ${tickets.size()} utente(s) em espera</p>
                    <br>
                </div>
            </div>
            <div class="buttonCard">
                <a href="utentesEsperaMedico">VER UTENTES</a>
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