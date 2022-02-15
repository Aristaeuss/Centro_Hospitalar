<!DOCTYPE html>
<html lang='en'>
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
    <link href='../CSS/scheduleEstilos.css' rel='stylesheet' />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous">
  </head>
  <body>

    <div class="main">
        <img class="headerIcons" alt="headImg" src="../Images/headerTrio.png">
        <div id="headerText" class="fixedText">
            <h3 id="headerTitle">CUIDE DE SI E DOS SEUS</h3>
            <p id="headerSubText">O Centro Hospitalar UPskill inspira-se nos seus clientes para estar na vanguarda na proteção das famílias. Estamos mais inovadores na prestação de cuidados de saúde, mais próximos e mais ágeis.</p>
        </div>
        <div  class="pageIdentifier">HORÁRIO</div>
        
        <div class="table-content">
            <div class="schedule">
                <div class="dom raw1 tableTitle">Dom</div>
                <div class="seg raw1 tableTitle">Seg</div>
                <div class="ter raw1 tableTitle">Ter</div>
                <div class="qua raw1 tableTitle">Qua</div>
                <div class="qui raw1 tableTitle">Qui</div>
                <div class="sex raw1 tableTitle">Sex</div>
                <div class="sab raw1 tableTitle">Sab</div>
                <div class="hora raw2 tableTitle">Hora de entrada</div>
                <div class="hora raw3 tableTitle">Hora de almoço</div>
                <div class="hora raw4 tableTitle">Hora de saída</div>
                <c:choose>
                    <c:when test = "${schedule.isSunday()==true}">
                        <div class="dom raw2 hora-entrada">${schedule.getStartTime()}</div>
                        <div class="dom raw3 hora-almoco">${schedule.getStartLunch()}</div>
                        <div class="dom raw4 hora-saida">${schedule.getEndTime()}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="dom raw2 hora-entrada"> - </div>
                        <div class="dom raw3 hora-almoco"> - </div>
                        <div class="dom raw4 hora-saida"> - </div>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test = "${schedule.isMonday()==true}">
                        <div class="seg raw2 hora-entrada">${schedule.getStartTime()}</div>
                        <div class="seg raw3 hora-almoco">${schedule.getStartLunch()}</div>
                        <div class="seg raw4 hora-saida">${schedule.getEndTime()}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="seg raw2 hora-entrada"> - </div>
                        <div class="seg raw3 hora-almoco"> - </div>
                        <div class="seg raw4 hora-saida"> - </div>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test = "${schedule.isTuesday()==true}">
                        <div class="ter raw2 hora-entrada">${schedule.getStartTime()}</div>
                        <div class="ter raw3 hora-almoco">${schedule.getStartLunch()}</div>
                        <div class="ter raw4 hora-saida">${schedule.getEndTime()}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="ter raw2 hora-entrada"> - </div>
                        <div class="ter raw3 hora-almoco"> - </div>
                        <div class="ter raw4 hora-saida"> - </div>
                    </c:otherwise>
                </c:choose>
                 <c:choose>
                    <c:when test = "${schedule.isWednesday()==true}">
                        <div class="qua raw2 hora-entrada">${schedule.getStartTime()}</div>
                        <div class="qua raw3 hora-almoco">${schedule.getStartLunch()}</div>
                        <div class="qua raw4 hora-saida">${schedule.getEndTime()}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="qua raw2 hora-entrada"> - </div>
                        <div class="qua raw3 hora-almoco"> - </div>
                        <div class="qua raw4 hora-saida"> - </div>
                    </c:otherwise>
                 </c:choose>
                 <c:choose>
                    <c:when test = "${schedule.isThursday()==true}">
                        <div class="qui raw2 hora-entrada">${schedule.getStartTime()}</div>
                        <div class="qui raw3 hora-almoco">${schedule.getStartLunch()}</div>
                        <div class="qui raw4 hora-saida">${schedule.getEndTime()}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="qui raw2 hora-entrada"> - </div>
                        <div class="qui raw3 hora-almoco"> - </div>
                        <div class="qui raw4 hora-saida"> - </div>
                    </c:otherwise>
                 </c:choose>
                 <c:choose>
                    <c:when test = "${schedule.isFriday()==true}">
                        <div class="sex raw2 hora-entrada">${schedule.getStartTime()}</div>
                        <div class="sex raw3 hora-almoco">${schedule.getStartLunch()}</div>
                        <div class="sex raw4 hora-saida">${schedule.getEndTime()}</div>
                 </c:when>
                    <c:otherwise>
                        <div class="sex raw2 hora-entrada"> - </div>
                        <div class="sex raw3 hora-almoco"> - </div>
                        <div class="sex raw4 hora-saida"> - </div>
                    </c:otherwise>
                 </c:choose>
                 <c:choose>
                    <c:when test = "${schedule.isSaturday()==true}">
                        <div class="sab raw2 hora-entrada">${schedule.getStartTime()}</div>
                        <div class="sab raw3 hora-almoco">${schedule.getStartLunch()}</div>
                        <div class="sab raw4 hora-saida">${schedule.getEndTime()}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="sab raw2 hora-entrada"> - </div>
                        <div class="sab raw3 hora-almoco"> - </div>
                        <div class="sab raw4 hora-saida"> - </div>
                    </c:otherwise>
                 </c:choose>
            </div>
        </div>
    </div>    

  </body> 
  </html> 