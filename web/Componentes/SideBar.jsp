
<%@page import="Entidades.Usuarios"%>
<%
    response.setHeader("Cache-Control", "no-Cache,no-store,must-revalidate");
    HttpSession sessionObtenida = request.getSession();
    Usuarios usuarioEntrante = new Usuarios();
    if (sessionObtenida.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
    } else {
        usuarioEntrante = (Usuarios) sessionObtenida.getAttribute("user");
    }

%>
<nav id="sidebar" class="sidebar js-sidebar ">
    <div class="sidebar-content js-simplebar ">
        <a class="sidebar-brand mt-3" href="">
            <span class="align-middle" style="font-family: sans-serif ">Gestion de Dotaciones</span>
        </a>
        <ul class="sidebar-nav">
            <li class="sidebar-header">
                Menu
            </li>
            <li class="sidebar-item active">
                <a class="sidebar-link" href="../Home/Home.jsp">
                    <i class="align-middle" data-feather="sliders"></i> <span class="align-middle">Menu Principal</span>
                </a>
            </li>
            <!-- Links de administradores -->
            <% if (usuarioEntrante.getRol() == 0) {
            %>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../ViewsAdministrador/HomeRegionaCentro.jsp">
                    <i class="align-middle" data-feather="user"></i> <span class="align-middle">Regional y Centro</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../ViewsAdministrador/GestionUsuarios.jsp" >
                    <i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">Gestion de usuarios</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../ViewsAdministrador/Restablecimientos.jsp" >
                    <i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">Recuperacion contraseña</span>
                </a>
            </li>
            <li class="sidebar-item mb-2">
            <li class="sidebar-header">
                Manual de dotacion
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../ViewsAdministrador/HomeRedArea.jsp">
                    <i class="align-middle" data-feather="user"></i> 
                    <span class="align-middle">Red y Area</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../ViewsAdministrador/HomeClimaSexo.jsp">
                    <i class="align-middle" data-feather="user-plus"></i> 
                    <span class="align-middle">Clima y Sexo</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../ViewsAdministrador/HomeElementos.jsp">
                    <i class="align-middle" data-feather="users"></i> 
                    <span class="align-middle">Elementos</span>
                </a>
            </li> 
            <li class="sidebar-item">
                <a class="sidebar-link" href="../ViewsAdministrador/HomeDotacion.jsp">
                    <i class="align-middle" data-feather="users"></i> 
                    <span class="align-middle">Manual de Dotacion</span>
                </a>
            </li> 
            <li class="sidebar-header">
                Reportes
            </li>
            <ul class="sidebar-nav">
                <li class="sidebar-item">
                    <a class="sidebar-link" href="#" data-bs-toggle="modal" data-bs-target="#ModalReporteInstructor">
                        <i class="align-middle" data-feather="user"></i> <span class="align-middle">Instructor</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link" href="../Reports/Consolidado_elementos.jsp" target="_blank">
                        <i class="align-middle" data-feather="user"></i> <span class="align-middle">Elementos</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link" href="#" data-bs-toggle="modal" data-bs-target="#ModalReporteArea">
                        <i class="align-middle" data-feather="user-plus"></i> 
                        <span class="align-middle">Red y Area</span>
                    </a>
                </li>
            </ul>
            <%  }%>
            <!--Links de Coordinadores -->
            <% if (usuarioEntrante.getRol() == 1) { %>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../ViewsCoordinador/HomeCaracterizacion.jsp" >
                    <i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">Asignacion de elementos</span>
                </a>
            </li>

            <li class="sidebar-item">
                <a class="sidebar-link" href="" >
                    <i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">Reporte de dotacion</span>
                </a>
            </li>
            <%}%>

            <!-- Links RH -->
            <% if (usuarioEntrante.getRol() == 3) { %>
            <li class="sidebar-item">
                <a class="sidebar-link" href="" >
                    <i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">Dotacion por Instructor</span>
                </a>
            </li>

            <li class="sidebar-item">
                <a class="sidebar-link" href="" >
                    <i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">Elementos y cantidades</span>
                </a>
            </li>
            <%}%>

            <li class="sidebar-item mb-2 mt-auto">
                <a class="sidebar-link" href="../CerradoSession.jsp">
                    <i class="align-middle" data-feather="log-in"></i> 
                    <span class="align-middle">Cerrar Sesion</span>
                </a>
            </li>
        </ul>
    </div> 
</nav>

