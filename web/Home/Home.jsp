<%@page import="Controladores.AreaJpaController"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.RedJpaController"%>
<%@page import="Entidades.Area"%>
<%@page import="Entidades.Red"%>
<%@page import="Controladores.UsuariosJpaController"%>
<%@page import="Entidades.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
    HttpSession sessionObtenida = request.getSession();
    Usuarios usuarioEntrante = new Usuarios();
    if (sessionObtenida.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
    } else {
        usuarioEntrante = (Usuarios) sessionObtenida.getAttribute("user");


%>
<html>   
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%--BOOTSTRAP--%>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <%--ESTILO-MAQUETA--%>
        <link rel="stylesheet" href="../css/app.css"/>
        <%--TIPOS-LETRAS--%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">
        <%--JQUERY--%>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    </head>
    <body>


        <%            String DocStr = String.valueOf(usuarioEntrante.getIdusuario());
            if (usuarioEntrante.DencryptarClave(usuarioEntrante.getClave(), DocStr)) {
        %>

        <script>
            $(document).ready(function () {
                $('#ModalCambioPassword').modal('show');
            });
        </script>
        <%}%>
        <!-- Modal de Cambio de contraseña si el la contraseña es el mismo numero de documento del usuario -->
        <div class="modal" id="ModalCambioPassword" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false">
            <form action="<%=request.getContextPath()%>/Restablecimientos" method="post">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Por seguridad cambie su contraseña</h5>
                        </div>
                        <div class="modal-body">
                            <div class="col-md-12">
                                <div class="form-floating mb-3">
                                    <input type="text" name="numeroDocumentoCambio" id="numeroDocumentoCambio" value="<%=usuarioEntrante.getIdusuario()%>" class="form-control" readonly max="99999999999">
                                    <label  class="text-black" for="numeroDocumentoCambio">N° Documento</label>
                                </div>

                                <div class="form-floating mb-3">
                                    <input type="password" name="PasswordNueva" id="PasswordNueva" class="form-control" maxlength="50" required>
                                    <label class="text-black" for="PasswordNueva">Contraseña nueva</label>
                                </div>

                                <div class="form-floating mb-3">
                                    <input type="password" id="ConfirmacionPassword" name="ConfirmacionPassword" class="form-control" maxlength="50" required>
                                    <label class="text-black" for="ConfirmacionPassword">Confirmación de contraseña</label>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button value="RestablcerPasswordHome" name="BtnRestablecer" class="btn text-white" style="background-color: #018E42">Cambiar contraseña</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>



        <div class="wrapper">
            <jsp:include page="../Componentes/SideBar.jsp" ></jsp:include>
                <div class="main">
                <jsp:include page="../Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                       
                </main>
                <jsp:include page="../Componentes/modalGuardar.jsp" ></jsp:include> 
                <jsp:include page="../Componentes/footer2.jsp" ></jsp:include>
                
                </div>
            </div>

        <%--MENU--%>       
        <script src="../js/scriptMenu.js"></script>
        <script src="../js/alertas.js"></script>
        <script src="../js/JsContainer.js"></script>
        <%--BOOTSTRAP--%>
        <!-- Bootstrap JavaScript Libraries 
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous">
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
                integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous">
        </script>
        -->
        <%--TRANSICIONES--%>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <%--ALERTAS--%>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <%
            String respuesta = request.getParameter("respuesta");
            if (respuesta != null) {
                switch (respuesta) {
                    case "confirmacionFallida":
        %>
        <script>
    mostrarAdvertencia("Confirmacion Incorrecta");
        </script>
        <%
                        break;
                    default:
                        break;
                }
            }
        %>
    </body>
</html>
<% }%>