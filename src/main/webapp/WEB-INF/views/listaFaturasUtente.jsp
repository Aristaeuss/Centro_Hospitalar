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
    <link rel="stylesheet" href="../CSS/consultasUtente.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous">
    <%@ include file="navBarUtente.jsp"%>
  </head>
  <body>
    <div class="main">
      <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
      <div id="headerText" class="fixedText">
          <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
          <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
      </div>
      <div class="pageIdentifier">FATURAS</div>

      <div class="filterButtonsContainer">
           <div class="linkContainer"><a href="/listaFaturasUtente/pendentes"><img class="button-image" alt="icon" src="/Images/iconPendingInvoice.png"> Pendentes</a></div>
           <div class="linkContainer"><a href="/listaFaturasUtente/pagas"><img class="button-image" alt="icon" src="/Images/iconPayedInvoice.png"> Pagas</a></div>
           <div class="linkContainer"><a href="/listaFaturasUtente/todas"><img class="button-image" alt="icon" src="/Images/iconInvoices.png"> Todas</a></div>
      </div>

      <div class="transparentCardBar">
      </div>
      <div class="invoicesDiv">
      <c:if test="${invoices.isEmpty()}">
      <h2>Não ha faturas para mostrar. Tenta outro filtro.</h2>
      </c:if>
      <c:if test="${!invoices.isEmpty()}">
      <table>
        <thead>
            <tr>
                <td><b>Especialidade:</b></td>
                <td><b>Doutor:</b></td>
                <td><b>Data de Emissão:</b></td>
                <td><b>Data de Vencimento:</b></td>
                <td><b>Data de pagamento:</b></td>
                <td></td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="invoice" items="${invoices}">
            <tr>
                <td>${invoice.getAppointment().getDoctor().getSpeciality().getName()}</td>
                <td>Dr. ${invoice.getAppointment().getDoctor().getPreferredName()}</td>
                <td>${invoice.getLocalDatePartOfIssuedDate()}</td>
                <td>${invoice.getLocalDatePartOfDueDate()}</td>
                <c:if test="${invoice.getPaidDate()!=null}">
                <td><span style="color:green">${invoice.getLocalDatePartOfPaidDate()}</span></td>
                </c:if>
                <c:if test="${invoice.getPaidDate()==null}">
                <td><span style="color:red">Pagamento pendente</span></td>
                </c:if>
                <td><a href="${invoice.getUrl()}">Ver Fatura</a></td>
            </tr>
            </c:forEach>
        </tbody>
      </table>
      </c:if>
      </div>
    </div>

  </body>
</html>