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

    <div class="navbar-collapse">
        <ul class="navbar-nav navbar-align">

            <li class="nav-item">
                <a class="nav-link">
                    <span class="text-dark"><%=usuarioEntrante.getNombreCompleto()%></span>
                </a>
            </li>
        </ul>
    </div>
</nav>