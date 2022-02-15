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

      <link href='../CSS/listMedicosEstilos.css' rel='stylesheet' />
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous">
    </head>
    <body>
        <div class="main">
        <div class="headerBG">
        <div class="headerBar">
        <div class="voltar">
            <button onclick="window.location.href='landingPage';" id="voltar-button"><img class="voltar-button"  alt="Início" src="../Images/iconLogout.png"> Início</button>
        </div>

        <form class="specialityForm" name="specialityForm" id="specialityForm" method="post" action="/listMedicosSpeciality" modelAttribute="User">
          <select class="speciality" id="speciality" name="specialityName" onchange="updateForm()">
            <option value="none">${specialityMessage}</option>
            <c:forEach var="speciality" items="${specialities}">
                  <option value="${speciality.getName()}">${speciality.getName()}</option>
            </c:forEach>
          </select>
        </form>
        <div class="LogIn">
            <button onclick="window.location.href='login';" id="areaUtente-button"><img class="areaUtente-button"  alt="areaUtente" src="../Images/iconUserProfile.png"> LogIn</button>
        </div>
        </div>
        </div>

            <div id="header" class="fixedText">
                <img id="ch-logo" all="CentroHospitalar" src="../Images/mini-logo.svg">
                <c:if test="${speciality==null}">
                    <h3 id="headerTitle">CONHEÇA OS NOSSOS ${doctors.size()} MÉDICOS:</h3>
                </c:if>
                <c:if test="${speciality!=null}">
                    <h3 id="headerTitle">CONHEÇA OS NOSSOS ${doctors.size()} MÉDICOS DE ${speciality}:</h3>
                </c:if>
            </div>

        <div class="doctor-cards-wrapper flex-container">
            <c:forEach var="doctor" items="${doctors}">
                <div class="doctor-card">
                    <div class="doctor-card-img"><img class="doctor-img" alt="doctor-img" src="../${doctor.getUserImage().getPath()}"></div>
                    <div class="doctor-name">Dr. ${doctor.getPreferredName()}</div>
                    <div class="doctor-speciality">${doctor.getSpeciality().getName()}</div>
                    <div class="doctor-about">${doctor.getAboutMe()}</div>
                </div>
            </c:forEach>
        </div>

        </div>
        <script>
            function updateForm() {
            document.specialityForm.submit();
            };
        </script>
    </body>
</html>