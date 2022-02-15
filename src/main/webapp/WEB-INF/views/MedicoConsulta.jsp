<!DOCTYPE html>
<html lang="en">
<head>
    
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <%@ include file="navBarMedico.jsp"%>
    <link rel="stylesheet" href="/CSS/consultaMedico.css">

</head>
<body>

    <div class="main" >

        <figure >
            <img class="headerIcons" src="/Images/headerTrio.png">
        </figure>

        <div id="headerText" class="fixedText">
            <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
            <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
        </div>

        <div class="smallTransparentCardBar">
        </div>

        <div class="topCards">
            <div class="topCard">
                <div class="numberOfPatients">
                    ${appointmentsToday.size()}
                </div>
                <div class="smallText">Consultas de hoje</div>
                <div class="topCardButton">
                                <a href="listaConsultasMedico">VER CONSULTAS</a>
                </div>
            </div>
            <div class="topCard">
                <div class="numberOfPatients">
                    ${ticketsWaiting.size()}
                </div>
                <div class="smallText">Em espera</div>
                <div class="topCardButton">
                                <a href="utentesEsperaMedico">VER UTENTES</a>
                </div>
            </div>
            <c:if test="${ticket==null}">
            <div class="topCard">
                <form class="getTicket" method="post" action="/getNextTicket">
                <button type="submit" class="topCardButton">Chamar senha</button>
                </form>
            </div>
            </c:if>
            <c:if test="${ticket!=null}">
                <div class="topCard">
                <button class="topCardButton" type="submit" form="appointment">Terminar consulta</button>
                </div>
            </c:if>
             <c:if test="${ticket!=null}">
            <div class="topCard">
                <form class="getTicket" method="post" action="/callTicketAgain">
                    <input type="hidden" id="ticket" name="ticket" value="${ticket.getId()}">
                    <button type="submit" class="topCardButton">Chamar utente novamente </button>
                </form>
            </div>
            <div class="topCard">
                <form class="getTicket" method="post" action="/absentPatient">
                    <input type="hidden" id="ticket" name="ticket" value="${ticket.getId()}">
                    <button type="submit" class="topCardButton">Utente não compareceu </button>
                </form>
            </div>
            </c:if>
        </div>

        <c:if test="${lateTicket==true}">
            <div class="lateTicketBar" style="color:red;">
                <p>O utente ${ticket.getScheduledAppointment().getPatient().getPreferredName()} tinha consulta marcada para as ${ticket.getScheduledAppointment().getSlot().getTime()} e chegou às ${ticket.getCheckInDateTime().toLocalTime()}</p>
                <form class="getTicket" method="post" action="/acceptTicket">
                    <input type="hidden" id="ticket" name="ticket" value="${ticket.getId()}">
                    <button type="submit" class="topCardButton">Chamar utente</button>
                </form>
                <form class="getTicket" method="post" action="/waitTicket">
                    <input type="hidden" id="ticket" name="ticket" value="${ticket.getId()}">
                    <button type="submit" class="topCardButton">Chamar proximo utente</button>
                </form>
                <form class="getTicket" method="post" action="/rejectTicket">
                    <input type="hidden" id="ticket" name="ticket" value="${ticket.getId()}">
                    <button type="submit" class="topCardButton">Rejeitar utente</button>
                </form>
            </div>
        </c:if>

        <c:if test="${noTickets==true}">
            <div class="lateTicketBar">
                <p>Não há utentes em espera.</p>
            </div>
        </c:if>

        <c:if test="${lateTicketRejected==true}">
            <div class="lateTicketBar" style="color:green;">
                <p>Utente rejeitado devido ao atraso e ao volume de trabalho para hoje.</p>
            </div>
        </c:if>

        <c:if test="${ticketCalledAgain==true}">
            <div class="lateTicketBar" style="color:green;">
                <p>A senha foi chamada novamente.</p>
            </div>
        </c:if>

        <c:if test="${absentPatient==true}">
            <div class="lateTicketBar" style="color:green;">
                <p>Foi registada a ausência do utente.</p>
            </div>
        </c:if>

        <c:if test="${appointmentSaved==true}">
            <div class="lateTicketBar" style="color:green;">
                <p>A consulta foi salva e encerrada com sucesso.</p>
            </div>
        </c:if>
        <c:if test="${ticketMessage==true}">
            <div class="lateTicketBar" style="color:red;">
                <p>Para chamar outra senha, termine a consulta e grave as notas, ou indique que o utente não compareceu</p>
            </div>
        </c:if>

        <c:if test="${ticket!=null}">
            <div class="bigTransparentCardBar">
            </div>
            <div class="mainCards">
                <div class="patientCard">
                <div class="pictureAndInfo">
                <img class="profileImg" alt="profileImg" id="profileImg" src="../${ticket.getScheduledAppointment().getPatient().getUserImage().getPath()}">
                <div class="patientInfo">
                    <div class="patientName">${ticket.getScheduledAppointment().getPatient().getPreferredName()}</div>
                    <div class="patientAge"> ${ticket.getScheduledAppointment().getPatient().getAge()}</div>
                </div>
                </div>
                <div class="lastAppointments">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Data</th>
                            <th>Dr</th>
                            <th>Info Geral</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${patientsAppointments}">
                                <tr>
                                    <td>${appointment.getId()}</td>
                                    <td>${appointment.getStartTime().toLocalDate()}</td>
                                    <td>${appointment.getDoctor().getPreferredName()}</td>
                                    <td>${appointment.getTitle()}</td>
                                    <td><a type="button" class="tableButton" href="detalhesConsultaMedico/${appointment.getId()}">Ver</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="appointmentInfoCard">
                <form name="appointment" id="appointment" class="appointment" method="post" action="/saveAppointment">
                    <input type="hidden" id="ticket" name="ticket" value="${ticket.getId()}">
                    <input class="notesTitle" type="text" id="title" name="title" placeholder="Motivo da consulta">
                    <textarea placeholder="Notas sobre a consulta" class="note" type="text" id="note" name="note"></textarea>
                    <textarea placeholder="Receitas médicas:" class="prescription" type="text" id="prescription" name="prescription"></textarea>
                    <button type="submit" class="saveButton">Gravar e encerrar consulta</button>
                </form>
            </div>
        </div>
        </c:if>
        
    </div>

</body>
</html>