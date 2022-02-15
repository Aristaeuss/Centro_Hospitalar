<!DOCTYPE html>
<html lang="en">

<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <%@ include file="navBarAdmin.jsp"%>
    <link rel="stylesheet" href="/CSS/consultasUtente.css">

</head>

<body>

    <div class="main">
          <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
          <div id="headerText" class="fixedText">
              <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
              <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
          </div>
          <div  class="pageIdentifier">GESTÃO DE HORÁRIOS</div>
          <div class="transparentCardBar">
          </div>
          <div class="scheduledAppointmentsDiv">
            <c:if test="${success==true}">
                <div class="instructions" style="color:green;">
                    <p>Horário alterado com sucesso.</p>
                </div>
            </c:if>
            <div class="registerForm">
                <form class="registerForm" id="register-form" method="post" action="/gestaoHorarios" modelAttribute="Schedule">
                    <select id="doctorOrderNumber" name="doctorOrderNumber" required>
                        <option value="">Escolha o médico</option>
                     <c:forEach var="doctorx" items="${doctors}">
                           <option value="${doctorx.getOrderNumber()}">${doctorx.getPreferredName()}</option>
                     </c:forEach>
                    </select><br><br>
                    <input type="checkbox" name="monday" value="true">
                    <label for="monday"> Segunda-feira</label><br>
                    <input type="checkbox" name="tuesday" value="true">
                    <label for="tuesday"> Terça-feira</label><br>
                    <input type="checkbox" name="wednesday" value="true">
                    <label for="wednesday"> Quarta-feira</label><br>
                    <input type="checkbox" name="thursday" value="true">
                    <label for="thursday"> Quinta-feira</label><br>
                    <input type="checkbox" name="friday" value="true">
                    <label for="friday"> Sexta-feira</label><br>
                    <input type="checkbox" name="saturday" value="true">
                    <label for="saturday"> Sábado </label><br>
                    <input type="checkbox" name="sunday" value="true">
                    <label for="sunday"> Domingo</label><br><br>
                    <span style="color:red;" class="errorMessage" >${errorMessage}</span>
                    <input class="field-input" type="number" id="startTime" name="startTime" placeholder="Hora de início (Ex:8)" required>
                    <input class="field-input" type="number" id="startLunch" name="startLunch" placeholder="Hora de Almoço (Ex:13)" required>

                    <button type="submit" class="submitButton">
                        <img class="submit-button-arrow-image" src="../Images/iconArrow.svg">
                        <span class="submit-button">GRAVAR</span>
                    </button>
                </form>

            </div>
          </div>
    </div>


</body>

</html>