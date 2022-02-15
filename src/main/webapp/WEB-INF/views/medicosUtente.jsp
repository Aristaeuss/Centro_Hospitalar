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
    <%@ include file="navBarUtente.jsp"%>

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
            <p class="pageIdentifier">MÉDICOS</p>
        </div>

        <div class="transparentCardBar">

        </div>

            <div class="card" id="card1"  >
                <img class="cardImages" id="cardImage1" src="../Images/iconStetoscope.png">
                <div class="updatedInfo">
                    <h2><b>X</b> MÉDICOS</h2>
                    <p>presentes no centro</p>
                    <div class="cardSubText">
                        <p>Y médicos estão em pausa</p>
                    </div>
                </div>
                <div class="buttonCard">
                    <a href="temp">LISTA DE MÉDICOS</a>
                </div>
            </div>

            <div class="card" id="card2">
                <img class="cardImages" id="cardImage2" src="../Images/iconCalendar.png">
                <div class="updatedInfo">
                    <h2><b>X</b> VAGAS</h2>
                    <p>disponiveis para hoje</h3>
                        <div class="cardSubText">
                            <p></p>
                        </div>
                </div>
                <div class="buttonCard">
                    <a href="temp">LISTA DE VAGAS</a>
                </div>
            </div>

    </div>

</body>
</html>