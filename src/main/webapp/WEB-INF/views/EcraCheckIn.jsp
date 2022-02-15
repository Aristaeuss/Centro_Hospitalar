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
    <link href='../CSS/ecraSenhasChamadas.css' rel='stylesheet' />
    <script>
    let timeFunction = ()=>{
       let date=new Date();
       document.getElementById("time").innerHTML=date.toISOString().split("T")[1].split(".")[0];
       };
    setInterval(timeFunction,1000);
    window.onload=timeFunction;
    </script>

</head>
<body>

    <div class="main" >
        <div class="top-flex-container">
            <div class="logoContainer">
                <img class="logo" alt="logoImage" src="../Images/logo.svg">
            </div>
            <div class="dateTime">
                <p> ${dateTime.toLocalDate()}</p>
                <p id="time">${time} </p>
            </div>
        </div>
        <div class="checkIn">
            <div class="updatedInfo">
                <p><b>CHECKIN</b></h3>
                    <div class="cardSubText">
                        <p>Introduza o seu NIF</p>
                    </div>
            </div>
            <span class="errorMessage" style="color: red">${errorCheckin}</span>
            <form class="inputInfoCard" method="post" action="/checkInMaquina">

                <input class="checkinField" type="number"  name="nif" placeholder="NIF" required>
                <br>
                <br>
                <div class="submitButtonCard">
                    <button class="submitButtonCard" type="submit" value="Efetuar checkin"><b>Fazer checkin    ></b></button>
                </div>
            </form>

        </div>

    </div>      

</body>
</html>