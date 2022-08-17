<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Personas</title>
    <jsp:include page="../../templates/head.jsp"/>
</head>
<body style="background-color: aqua">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="get-personas">
            <i data-feather="home"></i> Inicio
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="get-personas">
                        <i data-feather="align-left"></i> Personas
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="get-pokemons">Pokemons</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="get-abilities">Habilidades</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="get-users"><i data-feather="users"></i>Usuarios</a>
                </li>
            </ul>
        </div>
        <form method="post" action="logout">
            <button type="submit" class="btn btn-outline-danger mt-1">
                <i data-feather="log-out"></i> Cerrar sesión
            </button>
        </form>
    </div>
</nav>

<script>
    feather.replace();
</script>

<div class="container mt-5" >
    <div class="row">
        <div class="col-12">
            <c:if test="${param['result']}">
                <p><c:out value="${param['message']}"></c:out></p>
            </c:if>
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col-6">Personas</div>
                        <div class="col-6 text-end">
                            <a href="create-persona" class="btn btn-outline-success btn-sm">
                                <i data-feather="plus"></i> Registrar persona
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <table class="table table-sm table-hover datatable">
                        <thead>
                        <th>#</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>CURP</th>
                        <th>birthday</th>
                        </thead>
                        <tbody>
                        <c:forEach var="persona" items="${personas}" varStatus="status">
                            <tr>
                                <td>
                                    <c:out value="${status.count}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${persona.name}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${persona.surname}"></c:out>
                                </td>
                                <td><c:out value="${persona.curp}"/></td>
                                <td><c:out value="${persona.birthday}"/></td>
                                <td>
                                    <a href="get-persona?id=${persona.id}" class="btn btn-warning
                                    btn-sm"><i data-feather="edit"></i></a>
                                    <form action="delete-persona" method="post">
                                        <input type="hidden" value="${persona.id}" name="id"/>
                                        <button type="submit" class="btn btn-danger btn-sm">
                                            <i data-feather="trash-2"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../../templates/footer.jsp"/>
<script>
    $(document).ready(() => {

        document.getElementById("loaderDiv").style.display = "none";
        let $datatable = $('.datatable')
        if ($datatable.DataTable) {
            $datatable.DataTable({
                language: {
                    url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/es-MX.json',
                },
            });
        }
    })
    feather.replace();
</script>
</body>
</html>
