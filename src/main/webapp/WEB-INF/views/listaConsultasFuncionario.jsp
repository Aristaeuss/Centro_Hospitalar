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
      <div  class="pageIdentifier">CONSULTAS PENDENTES E CANCELADAS DO UTENTE: ${patient.getName().toUpperCase()}</div>

      <div class="transparentCardBar">
      </div>
      <div class="scheduledAppointmentsDiv">

      <!--Tabela de marcações-->
      <c:if test="${scheduledAppointments.isEmpty()}">
      <h2>Não tem nenhuma consulta para mostrar</h2>
      </c:if>
      <c:if test="${!scheduledAppointments.isEmpty()}">
      <table>
        <thead>
            <tr>
                <td><b>Data:</b></td>
                <td><b>Hora início:</b></td>
                <td><b>Médico</b></td>
                <td><b>Especialidade</b></td>
                <td><b>Estado</b></td>
                <td></td>
                <td></td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="scheduledAppointment" items="${scheduledAppointments}">
            <tr>
                <td>${scheduledAppointment.getSlot().getDate()}</td>
                <td>${scheduledAppointment.getSlot().getDateTime().toLocalTime()}</td>
                <td>Dr.${scheduledAppointment.getSlot().getDoctor().getName()}</td>
                <td>${scheduledAppointment.getSlot().getSpecialityName()}</td>
                <td>
                <c:if test="${scheduledAppointment.isCanceled()}">
                <span style="color:red">
                </c:if>
                <c:if test="${!scheduledAppointment.isCanceled()}">
                <span style="color:green">
                </c:if>
                    ${scheduledAppointment.getState()}
                </span>
                </td>
                <td>
                <c:if test="${!scheduledAppointment.isCanceled()}">
                <a href="/cancelarConsultaFuncionario/${scheduledAppointment.getId()}">Cancelar</a>
                </c:if>
                </td>
                <td><a href="/remarcarConsultaFuncionario/${scheduledAppointment.getId()}">Re-agendar</a></td>
            </tr>
            </c:forEach>
        </tbody>
      </table>
      </c:if>

      </div>
    </div>

  </body>
</html>