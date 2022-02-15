<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <%@ include file="navBarUtente.jsp"%>
    <link rel="stylesheet" href="/CSS/userListMedicosEstilos.css">
</head>
<body>
    <div class="main">
        <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
        <div id="headerText" class="fixedText">
            <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
            <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
        </div>
        <div  class="pageIdentifier">LISTA DE MÉDICOS</div>
         <form class="userSpecialityForm" name="userSpecialityForm" id="userSpecialityForm" method="post" action="/userListMedicosSpeciality" modelAttribute="User">
           <select class="speciality" id="speciality" name="specialityName" onchange="updateForm()">
             <option value="none">${specialityMessage}</option>
             <c:forEach var="speciality" items="${specialities}">
                   <option value="${speciality.getName()}">${speciality.getName()}</option>
             </c:forEach>
           </select>
         </form>
        <div class="doctor-cards-wrapper flex-container">
            <c:forEach var="doctor" items="${doctors}">
                <div class="doctor-card">
                    <div class="doctor-card-img"><img class="doctor-img" alt="doctor-img" src="${doctor.getUserImage().getPath()}"></div>
                    <div class="doctor-name">Dr. ${doctor.getPreferredName()}</div>
                    <div class="doctor-speciality">${doctor.getSpeciality().getName()}</div>
                    <div class="doctor-about">${doctor.getAboutMe()}</div>
                </div>
            </c:forEach>
        </div>
    </div>
    <script>
            function updateForm() {
            document.userSpecialityForm.submit();
            };
    </script>
</body>
</html>