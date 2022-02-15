<!DOCTYPE html>
<html lang='en'>
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
    <link rel="stylesheet" href="../CSS/consultasUtente.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous">
    <%@ include file="navBarFuncionario.jsp"%>
  </head>
  <body>
    <div class="main">
      <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
      <div id="headerText" class="fixedText">
          <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
          <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
      </div>
      <div  class="pageIdentifier">UTENTES EM ESPERA</div>
      <div class="transparentCardBar">
      </div>
      <div class="scheduledAppointmentsDiv">
      <c:if test="${lateScheduledAppointments.isEmpty()}">
      <h2> Não há utentes atrasados</h2>
      </c:if>
      <c:if test="${!lateScheduledAppointments.isEmpty()}">
      <table>
        <thead>
            <tr>
                <td><b>Nome</b></td>
                <td><b>Idade</b></td>
                <td><b>Hora marcada</b></td>
                <td><b>Consulta marcada dia</b></td>
                <td><b>Email</b></td>
                <td><b>Telefone</b></td>
                <td></td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="scheduledAppointment" items="${lateScheduledAppointments}">
            <tr>
                <td>${scheduledAppointment.getPatient().getPreferredName()}</td>
                <td>${scheduledAppointment.getPatient().getAge()}</td>
                <td>${scheduledAppointment.getSlot().getDateTime().toLocalTime()}</td>
                <td>${scheduledAppointment.getCreationDate()}</td>
                <td>${scheduledAppointment.getPatient().getTelephone()}</td>
                <td>${scheduledAppointment.getPatient().getEmail()}</td>
                <td><a href="perfilUtenteStatic/${scheduledAppointment.getPatient().getNif()}">Ver Utente</a></td>
            </tr>
            </c:forEach>
        </tbody>
      </table>
      </c:if>
      </div>
    </div>

  </body>
</html>