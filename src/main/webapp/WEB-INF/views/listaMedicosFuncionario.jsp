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
    <link rel="stylesheet" href="../CSS/consultasUtente.css">
    <%@ include file="navBarFuncionario.jsp"%>

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
            <p class="pageIdentifier">Lista de Médicos</p>
        </div>

        <div class="transparentCardBar">
        </div>
        <div class="scheduledAppointmentsDiv">
            <c:if test="${doctorsToday!=null}">
            <form class="specialityForm" name="specialityForm" id="specialityForm" method="post" action="/listaMedicosPresentesFuncionario" >
            </c:if>
            <c:if test="${doctorsToday==null}">
            <form class="specialityForm" name="specialityForm" id="specialityForm" method="post" action="/listaMedicosFuncionario" >
            </c:if>

                      <select class="speciality" id="speciality" name="specialityName" onchange="updateForm()">
                        <option value="none">${specialityMessage}</option>
                        <c:forEach var="speciality" items="${specialities}">
                              <option value="${speciality.getName()}">${speciality.getName()}</option>
                        </c:forEach>
                      </select>
                    </form>
            <table class="specialityForm" name="specialityForm" id="specialityForm">
                <thead>
                    <tr>
                        <td><b>Nome</b></td>
                        <td><b>Especialidade</b></td>
                        <td><b>Entrada</b></td>
                        <td><b>Almoço</b></td>
                        <td><b>Saída</b></td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="doctor" items="${doctors}">
                        <tr>
                            <td>${doctor.getPreferredName()}</td>
                            <td>${doctor.getSpeciality().getName()}</td>
                            <td>${doctor.getSchedule().getStartTime()}</td>
                            <td>${doctor.getSchedule().getStartLunch()}</td>
                            <td>${doctor.getSchedule().getEndTime()}</td>
                            <td><a href="/perfilMedicoStatic/${doctor.getNif()}">Ver perfil</a></td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <script>
        function updateForm() {
            document.specialityForm.submit();
        };
    </script>

</body>
</html>