<!DOCTYPE html>
<html lang='en'>
  <head>

    <base href="/">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <link href="../CSS/VGUtente.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous">
    <%@ include file="navBarUtente.jsp"%>

  </head>
  <body>
    <div class="main">
      <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
      <div id="headerText" class="fixedText">
          <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
          <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
      </div>
      <div  class="pageIdentifier">PÁGINA DE REMARCAÇÃO DE CONSULTA</div>
      <div class="transparentCardBar">
              </div>
      <div class="confirmedMessage">
      <c:if test="${errorMessage!=null}">
        <h3><span style="color:red;">${errorMessage}</span></h3>
      </c:if>
      <c:if test="${errorMessage==null}">
        <h3>${loggedInUser.getName()}, a sua consulta foi remarcada</h3>
        <div class="consultas-container">
        <div class="consulta-container">
        <p><b>Detalhes da marcação anterior:</b></p>
        <p>Médico: Dr.${canceledScheduledAppointment.getSlot().getDoctor().getPreferredName()}</p>
        <p>Especialidade: ${canceledScheduledAppointment.getSlot().getSpecialityName()}</p>
        <p>Data: ${canceledScheduledAppointment.getSlot().getDate()}</p>
        <p>Horário: ${canceledScheduledAppointment.getSlot().getDateTime().toLocalTime()}</p>
        </div>
        <div class="consulta-container">
        <p><b>Detalhes da nova marcação:</b></p>
        <p>Médico: Dr.${newlyBookedScheduledAppointment.getSlot().getDoctor().getPreferredName()}</p>
        <p>Especialidade: ${newlyBookedScheduledAppointment.getSlot().getSpecialityName()}</p>
        <p>Data: ${newlyBookedScheduledAppointment.getSlot().getDate()}</p>
        <p>Horário: ${newlyBookedScheduledAppointment.getSlot().getDateTime().toLocalTime()}</p>
        </div>
        </div>
      </c:if>
      </div>
    </div>

  </body>
</html>