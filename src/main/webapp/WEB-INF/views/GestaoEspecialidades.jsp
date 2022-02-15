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
          <div  class="pageIdentifier">GESTÃO DE ESPECIALIDADES</div>
          <div class="transparentCardBar">
          </div>
          <div class="scheduledAppointmentsDiv">
            <c:if test="${successDeleteMessage!=null}">
                <div class="instructions" style="color:green;">
                    <p>${successDeleteMessage}</p>
                </div>
            </c:if>
            <c:if test="${failDeleteMessage!=null}">
                <div class="instructions" style="color:red;">
                    <p>${failDeleteMessage}</p>
                </div>
            </c:if>
            <c:if test="${successCreateMessage!=null}">
                <div class="instructions" style="color:green;">
                    <p>${successCreateMessage}</p>
                </div>
            </c:if>

            <div class="deleteFormContainter">
                <form class="deleteForm" id="register-form" method="post" action="/apagarEspecialidade">
                    <select id="specialityId" name="specialityId" required>
                        <option value="">Escolha a especialidade</option>
                     <c:forEach var="speciality" items="${specialities}">
                           <option value="${speciality.getId()}">${speciality.getName()}</option>
                     </c:forEach>
                    </select><br><br>
                    <button type="submit" class="submitButton">
                        <span class="submit-button">Apagar Especialidade</span>
                    </button>
                </form>
            </div>
            <div class="newSpecialityFormContainer">
                <form class="newSpecialityForm" id="newSpecialityForm" method="post" action="/criarEspecialidade">
                <label>Escreva o nome da nova especialidade</label>
                <input class="field-input" type="text" id="specialityName" name="specialityName" placeholder="Especialidade" required>
                <button type="submit" class="submitButton">Gravar Especialidade</button>
                </form>
            </div>
          </div>
    </div>


</body>

</html>