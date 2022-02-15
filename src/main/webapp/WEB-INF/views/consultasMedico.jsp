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
                </div>
            </div>
            <div class="buttonCard">
                <a href="utentesEsperaMedico">VER UTENTES</a>
            </div>
        </div>

        <div class="card" id="card3"  >
            <img class="cardImages" id="cardImage1" src="../Images/iconStetoscope.png">
            <div class="updatedInfo">
                <h5>CONSULTAS DE HOJE</h5>
                <p>${date}</p>
                <div class="cardSubText">
                    <p>Tem ${appointments.size()} consultas marcadas para hoje</p>
                </div>
            </div>
            <div class="buttonCard">
                <a href="listaConsultasMedico">VER CONSULTAS</a>
            </div>
        </div>



    </div>
</body>
</html>