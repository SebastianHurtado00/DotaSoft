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
<nav class="navbar navbar-expand navbar-light navbar-bg p-2">
    <a class="sidebar-toggle js-sidebar-toggle">
        <i class="hamburger align-self-center"></i>
    </a>

    <div class="navbar-collapse collapse">
        <ul class="navbar-nav navbar-align">

            <li class="nav-item dropdown">
                <a class="nav-icon dropdown-toggle d-inline-block d-sm-none" href="#"
                   data-bs-toggle="dropdown">
                    <i class="align-middle" data-feather="settings"></i>
                </a>

                <a class="nav-link dropdown-toggle d-none d-sm-inline-block" href="#"
                   data-bs-toggle="dropdown">
                    <span class="text-dark"><%=usuarioEntrante.getNombreCompleto()%></span>
                </a>
                <div class="dropdown-menu dropdown-menu-end">
                    <a class="dropdown-item"><i
                            class="align-middle me-1" data-feather="user"></i>Perfil</a>

                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href=""><i class="align-middle me-1" data-feather="log-in"></i> Cerrar sesion</a>
                </div>
            </li>
        </ul>
    </div>
</nav>