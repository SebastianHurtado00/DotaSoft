<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <%--BOOTSTRAP--%>
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <%--ESTILO-MAQUETA--%>
        <link rel="stylesheet" href="../css/app.css"/>
        <%--TIPOS-LETRAS--%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">
        <%--JQUERY--%>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <%--TRANSICIONES--%>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">

    </head>
    <body>
        <script>
            function consultarUsuario() {
                var cedulaScript = document.getElementById("usuarioCedula").value;
                $.ajax(
                        {
                            type: "POST",
                            url: "../Busquedas/RestablecimientoPasswords.jsp",
                            data: "cedula=" + cedulaScript,
                            dataType: "html",
                            success: function (data) {
                                $("#DatosUsuario").empty();
                                $("#DatosUsuario").append(data);
                            }
                        });

            }
        </script>
        <div class="wrapper">
            <jsp:include page="../Componentes/SideBar.jsp" ></jsp:include>
                <div class="main">
                <jsp:include page="../Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                        <div class="container-fluid p-0">
                            <section class="section-0 d-flex justify-content-between">
                                <h1><strong>Recuperacion de contraseña</strong></h1>
                                <img src="../assests/LogoSena.webp" width="150px" height="150px" class="align-self-end  img-fluid" style="margin-top: -45px"/> 
                            </section>
                            <div class="row">
                                <div class="col-12 col-md-12 col-xxl-12 d-flex order-3 order-xxl-2">
                                    <div class="card flex-fill w-100">
                                        <div class="card-header">
                                            <h5 class="card-title mb-0">Formulario</h5>
                                        </div>
                                        <div class="card-body px-4">
                                            <form action="<%=request.getContextPath()%>/Restablecimientos" method="post">
                                            <div class="input-group mb-3">
                                                <input required="" id="usuarioCedula" class="form-control" type="number" name="CC" placeholder="Ingrese un numero de cedula">
                                                <button type="button" class="btn btn-outline-success" style="background: #018E42 ; color: white" onclick="consultarUsuario()">Buscar</button>
                                            </div>
                                            <div class="table-responsive mb-2" >
                                                <div class="table-wrapper-scroll-y my-custom-scrollbar p-2" style="height: 180px">
                                                    <table class="table table-striped-columns " style="height: 100px">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">N° Cedula</th>
                                                                <th scope="col">Nombre</th>
                                                                <th scope="col">Apellido</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <!-- Campos del usuario encontrado -->
                                                            <tr id="DatosUsuario"></tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="d-grid gap-2 mx-auto mb-5">
                                                <button name="BtnRestablecer" value="RestablercerAdmin" class="btn btn-outline-warning mx-auto" >Restablecer Contraseña</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <jsp:include page="../Componentes/modalGuardar.jsp" ></jsp:include> 
                <jsp:include page="../Componentes/footer2.jsp" ></jsp:include>
                </div>
            </div>

        <%--MENU--%>       
        <script src="../js/scriptMenu.js"></script>
        <script src="../js/alertas.js"></script>
        <%--BOOTSTRAP--%>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <%--TRANSICIONES--%>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <%--ALERTAS--%>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <%
            String res = request.getParameter("respuesta");
            if (res != null) {

                switch (res) {
                    case "Vacios":
        %>
        <script>
                                                    mostrarAdvertencia("Campos Vacios");
        </script>
        <%
                break;

            case "UsuarioNoValido":
        %>
        <script>
            mostrarAdvertencia("Usuario no valido");
        </script>
        <%
                break;

            case "restablecimientoExitoso":
        %>
        <script>
            mostrarExito("Restablecimiento exitoso");
        </script>
        <%
                        break;
                    default:
                        throw new AssertionError();
                }

            }

        %>

    </body>
</html>
