<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <%@ include file="navBarAdmin.jsp"%>
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
            <p class="pageIdentifier">REGISTO DE MÉDICO</p>
        </div>

        <div class="transparentCardBar">

        </div>

        <form class="register-flex" id="register-form" method="post" action="/registoFuncionarioM" modelAttribute="User">
            <input class="field-input" type="text" id="name" name="name" placeholder="Nome completo" required>
            <input class="field-input" type="text" id="preferredName" name="preferredName" placeholder="Indique os 2 nomes por que deseja ser tratado" required>
            <span class="errorMessage" style="color: red">${errorMessageDob}</span>
            <label for="dob" id="dob-label">Data de Nascimento</label>
            <input class="field-input" type="date" id="dob" name="dob" placeholder="Data de Nascimento" required>
            <span class="errorMessage" style="color: red">${errorMessageNif}</span>
            <input class="field-input" type="number" id="nif" name="nif" placeholder="NIF" required>
            <input class="field-input" type="number" id="iban" name="iban" placeholder="IBAN" required>
            <input class="field-input" type="number" id="orderNumber" name="orderNumber" placeholder="Numero de inscrinção na ordem" required>
            <select id="speciality" name="specialityName" required>
              <c:forEach var="speciality" items="${specialities}">
                    <option value="${speciality.getName()}">${speciality.getName()}</option>
              </c:forEach>
            </select>
            <input class="field-input" type="number" id="telephone" name="telephone" placeholder="Telefone" required>
            <input class="field-input" type="text" id="address" name="address" placeholder="Morada">
            <input class="field-input" type="text" id="doorNumber" name="doorNumber" placeholder="Nº porta">
            <input class="field-input" type="text" id="floor" name="floor" placeholder="Andar">
            <input class="field-input" type="text" id="postalCode" name="postalCode" placeholder="Código Postal">
            <input class="field-input" type="text" id="city" name="city" placeholder="Concelho">
            <span class="errorMessage" style="color: red">${errorMessageEmail}</span>
            <input class="field-input" type="email" id="email" name="email" placeholder="Email" required>
            <span class="errorMessage" style="color: red">${errorMessageUsername}</span>
            <input class="field-input" type="text" id="username" name="username" placeholder="Nome de Utilizador" required>
            <button type="submit" class="submitButton">
                <img class="submit-button-arrow-image" src="../Images/iconArrow.svg">
                <span class="submit-button">REGISTAR</span>
            </button>
        </form>
    </div>      

</body>
</html>