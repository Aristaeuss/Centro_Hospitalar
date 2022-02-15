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
    <%@ include file="navBarMedico.jsp"%>
    <link rel="stylesheet" href="/CSS/contactosUtenteEstilos.css">
</head>
<body>


<!--The part where the main content of the webpage is located:-->
<div class="main">
    <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
    <div  class="fixedText">
        <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
        <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
    </div>
    <div  class="pageIdentifier">CONTACTOS</div>
    <div class="full-contact-wrapper">
        <article class="contact-info"><h5>Contacte-nos directamente:</h5>Através do nosso e-mail <a href="mailto:upskill.clinic@gmail.com">upskill.clinic@gmail.com</a> a qualquer hora
        ou entre as 07h00 e as 22h00 pelos nosso serviço de atendimento telefónico:
          <ul>
            <li>213456789</li>
            <li>934567890</li>
            <li>912345678</li>
            <li>923456789</li>
          </ul>
        </article>

        <article class="contact-form-text"><h5>Prefere aguardar o nosso contacto?</h5>Introduza os dados solicitados e entraremos em
            contacto consigo assim que possível:
        </article>
        <form class="contact-form" action="MAILTO:upskill.clinic@gmail.com" method="post" enctype="text/plain">
        <div class="contact-wrapper">
            <input type="text" class="contact-name" id="contact-name" placeholder="Nome"></br>
            <input type="email" class="contact-email" id="contact-email" placeholder="E-mail"><input type="tel" class="contact-phone" id="contact-phone" placeholder="Telefone"></br>
        </div>
            <textarea class="contact-message" id="contact-message" maxlength="500" placeholder="Mensagem (Máx. 500 caracteres)"></textarea></br>
            <button type="submit" class="contact-submit-button">Enviar</button>
        </form>

    </div>
</div>
</body>
</html>
