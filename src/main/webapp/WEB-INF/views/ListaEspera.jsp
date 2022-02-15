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
    <%@ include file="navBarFuncionario.jsp"%>
    <link rel="stylesheet" href="/CSS/ListaEspera.css">
    <script type="text/javascript">

        function updateForm() {
            var numUtente = document.getElementById("numUtente").value;
            var nome = document.getElementById("nome").value;
            var especialidade = document.getElementById("especialidade").value;
            var numEspera = document.getElementById("numEspera").value;
            var prioridade = document.getElementById("prioridade").value;


            var table=document.getElementById("lista");
            var row=table.insertRow(-1);
            var cell1=row.insertCell(0);
            var cell2=row.insertCell(1);
            var cell3=row.insertCell(2);
            var cell4=row.insertCell(3);
            var cell5=row.insertCell(4);
            cell1.innerHTML=numUtente;
            cell2.innerHTML=nome;
            cell3.innerHTML=especialidade;
            cell4.innerHTML=numEspera;
            cell5.innerHTML=prioridade;
            document.forms["espera"].reset();

        }
    </script>
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
            <p class="pageIdentifier">LISTA DE ESPERA</p>
        </div>

        <div class="listaEspera">
            <form name="Lista de Espera" id="espera">
                <table>
                    <tr>
                        <td>
                            <label for="numUtente">Número de Utente:</label>
                        </td>
                        <td>
                            <input id="numUtente" name="numUtente" title="número de utente do sistema nacional de saúde" type="text" size="11" />
                        </td>
                        <td>
                            <label for="nome">Nome:</label>
                        </td>
                        <td>
                            <input id="nome" name="nome" title="nome do utente" type="text" size="25" />
                        </td>
                        <td>
                            <label for="especialidade">Especialidade:</label>
                        </td>
                        <td>
                            <input id="especialidade" name="especialidade" title="Especialidade da consulta" type="text" size="14" />
                        </td>
                        <td>
                            <label for="numEspera">Número de Espera:</label>
                        </td>
                        <td>
                            <input id="numEspera" name="numEspera" title="posição na lista de espera" type="text" size="8" />
                        </td>
                        <td>
                            <label for="prioridade">Prioridade:</label>
                        </td>
                        <td>
                            <input id="prioridade" name="prioridade" title="prioridade" type="text" size="12" list="listaPrioridades"/>
                            <datalist id="listaPrioridades" name="prioridade">
                                <option value="ALTA">
                                <option value="Moderada">
                                <option value="Baixa">
                            </datalist>
                        </td>
                    </tr>
                </table>
                <div class="button">
                    <button type="button" onClick="updateForm()"/>Adicionar</button>
                </div>
            </form>
            <br>

            <table id="lista" width="700">
                <thead>
                <tr>
                    <th scope="col" width="100">Num Utente</th>
                    <th scope="col" width="250">Nome</th>
                    <th scope="col" width="150">Especialidade</th>
                    <th scope="col" width="100">Num Espera</th>
                    <th scope="col" width="100">Prioridade</th>
                </tr>
                </thead>
            </table>
        </div>

    </div>


</body>
</html>