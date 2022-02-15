<!DOCTYPE html>
<html lang='en'>
  <head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href='../CSS/ecraSenhasChamadas.css' rel='stylesheet' />
    <script>
    setTimeout(()=>{
        location.reload();
        },5000);
     let timeFunction = ()=>{
        let date=new Date();
        document.getElementById("time").innerHTML=date.toISOString().split("T")[1].split(".")[0];
        };
     setInterval(timeFunction,1000);
     window.onload=timeFunction;
     </script>
  </head>
  <body>
    <div class="main">
        <div class="top-flex-container">
            <div class="logoContainer">
                <img class="logo" alt="logoImage" src="../Images/logo.svg">
            </div>
            <div class="dateTime">
                <div class="localDate"> ${dateTime.toLocalDate()}</div>
                <div class="time" id="time">${time} </div>
            </div>
        </div>
        <div class="chamada-flex-container">
            <c:forEach var="senha" items="${senhas}">
                <div class="ticket ${senha.isRejectedForLateness()?'rejected':''}">
                    <div class="numeroSenha">
                        <div class="title">Senha</div>
                         <div class="numero-senha">${senha.getId()}</div>
                    </div>
                    <div class="utente">
                        <div class="title">Utente</div>
                         <div class="utente">${senha.getScheduledAppointment().getPatient().getPreferredName()}</div>
                    </div>
                    <div class="doctor">
                        <div class="title">Médico</div>
                        <div class="utente">Dr ${senha.getScheduledAppointment().getSlot().getDoctor().getPreferredName()}</div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
  </body>
</html>