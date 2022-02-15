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
    <link rel="stylesheet" href="/CSS/landingPage.css">

</head>
<body>

    <div class="container-fluid">

        <div class="headerBarFlex">
            <div class="headerLogo">
                 <img class="headerLogo" alt="logo" src="../Images/logo-cinza.svg">
            </div>
            <div class="medicos">
                <button onclick="window.location.href='listMedicos';" id="medicos-button"><img class="medicos-button"  alt="medicos" src="../Images/iconStetoscope.png"> Médicos</button>
            </div>
            <div class="areaUtente">
                <button onclick="window.location.href='login';" id="areaUtente-button"><img class="areaUtente-button"  alt="areaUtente" src="../Images/iconUserProfile.png"> LogIn</button>
            </div>
        </div>


        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
              <div class="carousel-item active" id="carousel1">
                <img class="d-block w-100" class="carouselImage" src="../Images/floorImage.png" alt="First slide">
              </div>
              <div class="carousel-item"id="carousel2">
                <img class="d-block w-100" class="carouselImage" src="../Images/doctorkid.png" alt="Second slide">
              </div>
              <div class="carousel-item" id="carousel3">
                <img class="d-block w-100" class="carouselImage" src="../Images/happypatients.png" alt="Third slide">
              </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="sr-only"></span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="sr-only"></span>
            </a>
        </div>


       <div class="footerBarFlex">

            <div class="upskill">
                <img alt="uspkillLogo" src="../Images/iconUpskill.png">
            </div>
            <div class="info">
                <button onclick="togglePopup(); topFunction()" id="info-button"><img class="info-button"  alt="info" src="../Images/iconInfo.png"> Informações Gerais</button>
            </div>
            <div class="contactos">
                <button  onclick="togglePopup1(); topFunction()"><img class="contactos-button"  alt="contactos" src="../Images/iconPhone.png"> Contactos</button>
            </div>

       </div>

       <div class="popup hidden flex-container-center">
         <article class="title"><img class="info-popup"  alt="info" src="../Images/iconInfo.png"> Informações Gerais</article>
         <article class="about"><h2>Conheça o Centro Hospitalar UpSkill</h2>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Odit dolor esse omnis dicta. 
          Voluptatem earum omnis optio aspernatur eum sed numquam. Cupiditate unde soluta mollitia odio quis. Rem, porro! Fugit. Lorem ipsum, dolor sit amet consectetur 
          adipisicing elit. Nobis molestiae ducimus labore pariatur odit aliquam, qui sapiente enim aliquid sint alias corrupti ipsam, a quidem? Nulla sunt repudiandae 
          neque sed.</article>
         <article class="partners"><h2>Parceiros:</h2>
          <div class="partners-logos">
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/ADSE_1.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/advancecare.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/AETNA.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/ageas.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/AICEP.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/AIG.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/allianz.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/AXA-PPP_0.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/bic-seguros.png">
            </div>
            <div class="partner-logo">
              <img class="parcerias"  alt="parceiro" src="../Images/parcerias/fidelidade.png">
            </div>
        </div>
        </article>
        <button onclick="togglePopup()">X</button>    
      </div>

      <div class="popup1 hidden flex-container-center">
        <article class="title"><img class="contacts-popup"  alt="contacts" src="../Images/iconPhone.png"> Contactos</article>
        <article class="contact-info"><h2>Contacte-nos directamente:</h2>Através do nosso e-mail <a href="mailto:upskill.clinic@gmail.com">upskill.clinic@gmail.com</a> a qualquer hora
        ou entre as 07h00 e as 22h00 pelos nosso serviço de atendimento telefónico:
          <ul>
            <li>213456789</li>
            <li>934567890</li>
            <li>912345678</li>
            <li>923456789</li>
          </ul></article>
          <div class="contact-wrapper">
            <form class="contact-form" action="MAILTO:upskill.clinic@gmail.com" method="post" enctype="text/plain"><h2>Prefere aguardar o nosso contacto?</h2>Introduza os dados solicitados e entraremos em
              contacto consigo assim que possível:
              <input type="text" class="contact-name" id="contact-name" placeholder="Nome"></br>
              <input type="email" class="contact-email" id="contact-email" placeholder="E-mail"><input type="tel" class="contact-phone" id="contact-phone" placeholder="Telefone"></br>
              <textarea class="contact-message" id="contact-message" maxlength="500">Mensagem (Máx. 500 caracteres)</textarea>
              <button type="submit" class="contact-submit-button">Enviar</button>
            </form>
       </div>
       <button class="close-popup" onclick="togglePopup1()">X</button>    
     </div>

    
    </div>
   
<script>
const popup = document.querySelector('.popup');
console.log(popup);

function togglePopup() {
    popup.classList.toggle('hidden');
}

const popup1 = document.querySelector('.popup1');
console.log(popup1);

function togglePopup1() {
    popup1.classList.toggle('hidden');
}

const popup2 = document.querySelector('.popup2');
console.log(popup2);

function togglePopup2() {
    popup2.classList.toggle('hidden');
}

//Get the button:
mybutton = document.getElementById("info-button");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}
</script>
</body>
</html>