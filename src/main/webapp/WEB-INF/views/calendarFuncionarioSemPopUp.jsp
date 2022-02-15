<!DOCTYPE html>
<html lang='en'>
  <head>
    <base href="/">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Centro Hospitalar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <%@ include file="navBarFuncionario.jsp"%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous">
    <link href='../CSS/calendarEstilos.css' rel='stylesheet' />
  </head>
  <body>
    <div class="main">
      <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
      <div id="headerText" class="fixedText">
          <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
          <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
      </div>
      <div  class="pageIdentifier">CALENDÁRIO <br>
            <c:if test="${scheduledAppointmentToCancel!=null}">
              <p>Está a remarcar a consulta de ${scheduledAppointmentToCancel.getPatient().getPreferredName()} no dia ${scheduledAppointmentToCancel.getSlot().getDate()} às ${scheduledAppointmentToCancel.getSlot().getDateTime().toLocalTime()}
              para o Dr. ${scheduledAppointmentToCancel.getSlot().getDoctor().getName()}.
              </p>
            </c:if>
      </div>

      <div class="calendar-container">
        <div class="calendar-in-container">
          <div class="calendar">
            <div class="month">
              <i class="fas fa-angle-left prev"></i>
              <div class="date">
                <h1></h1>
                <p></p>
              </div>  
              <i class="fas fa-angle-right next"></i>
            </div>
            <div class="weekdays">
              <div>Dom</div>
              <div>Seg</div>
              <div>Ter</div>
              <div>Qua</div>
              <div>Qui</div>
              <div>Sex</div>
              <div>Sab</div>
            </div>
            <div class="days">
              
            </div>
          </div>
        </div>

        <c:if test="${doctorsAvailableForDay!=null && scheduledAppointmentToCancel==null}">
            <!--Lista de médicos por especialidade-->
                <div class="doctor-list-container">
                    <div class="doctor-list">
                        <div class="doctor-list-head">
                            <h1>Lista de Médicos:</h1>
                        </div>
                        <div class="discriminated-doctor-list">
                        <ul style="list-style-type:none;padding-left: 0rem;">
                            <c:forEach var="doctor" items="${doctorsAvailableForDay}">
                                <li class="doctorItem"><a href="/calendarioFuncionarioL/${dateSelected}D${doctor.getOrderNumber()}"> Dr. ${doctor.getPreferredName()}</a></li>
                            </c:forEach>
                        </ul>
                        </div>
                    </div>
                </div>
        </c:if>


          <!--Horário diário-->
          <div class="day-schedule-container">
            <div class="day-schedule">
              <div class="day-date">
                <p>${dateSelected}</p>
                <c:if test="${!slotsForDoctor.isEmpty()}">
                <p>Dr. ${slotsForDoctor.get(0).getDoctor().getPreferredName()}</p>
                </c:if>
              </div>
              <div class="hours">
                <c:forEach var="slot" items="${slotsForDoctor}">
                  <c:if test="${scheduledAppointmentToCancel==null}"><a class="hourButton" href="/marcarConsultaFuncionario/${slot.getId()}">${slot.getDateTime().toLocalTime()}</a></c:if>
                  <c:if test="${scheduledAppointmentToCancel!=null}"><a class="hourButton" href="/remarcarConsultaFuncionario/${scheduledAppointmentToCancel.getId()}/${slot.getId()}">${slot.getDateTime().toLocalTime()}</a></c:if>
                </c:forEach>
              </div>
            </div>
          </div>
      </div>




    </div>
    <script>
        function updateForm() {
        document.scheduleForm.submit();
        };
    </script>
    <script>
        window.daysThis=${jsonDates};
        window.daysNext=${jsonDatesNext};
        window.speciality=${speciality};
        window.scheduledAppointmentToCancelId=${jsonScheduledAppointmentToCancelId};
        window.doctor=${jsonDoctor};
    </script>
    <script type="text/javascript" src="/JS/calendarFuncionario.js">
    </script>
  </body>
</html>