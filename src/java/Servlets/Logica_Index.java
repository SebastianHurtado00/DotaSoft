/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.UsuariosJpaController;
import Entidades.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "Logica_Ingreso", urlPatterns = {"/Logica_Ingreso"})
public class Logica_Index extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String btn = request.getParameter("BtnIngreso");
        switch (btn) {
            case "action":
                logica_ingreso(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void logica_ingreso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuariosJpaController usuarioController = new UsuariosJpaController();
        int cedula = Integer.parseInt(request.getParameter("CC"));
        String password = request.getParameter("contrasenha");

        Usuarios usuarioEntrante = usuarioController.findUsuarios(cedula);
        HttpSession sessionAdmin = request.getSession();
        String mensaje;
        if (usuarioEntrante != null) {
            if (usuarioEntrante.DencryptarClave(usuarioEntrante.getClave(), password)) {
                switch (usuarioEntrante.getRol()) {
                    /*Administrador*/
                    case 0:
                        sessionAdmin.setAttribute("administrador", usuarioEntrante);
                        sessionAdmin.setAttribute("user", usuarioEntrante);
                        response.sendRedirect("Home/Home.jsp");
                        break;
                    case 1:
                        sessionAdmin.setAttribute("coordinador", usuarioEntrante);
                        sessionAdmin.setAttribute("user", usuarioEntrante);
                        response.sendRedirect("Home/Home.jsp");
                        break;
    
                    case 2:
                        sessionAdmin.setAttribute("Instructor", usuarioEntrante);
                        sessionAdmin.setAttribute("user", usuarioEntrante);
                        response.sendRedirect("Home/Home.jsp");
                        break;
                    case 3:
                        sessionAdmin.setAttribute("RecursosHumanos", usuarioEntrante);
                        sessionAdmin.setAttribute("user", usuarioEntrante);
                        response.sendRedirect("Home/Home.jsp");
                        break;
                    default:
                        break;
                }

            } else {
                mensaje = "Contrasenha incorrecta";
                response.sendRedirect("index.jsp?respuesta=" + mensaje);
            }

        } else {
            mensaje = "No encontrado";
            response.sendRedirect("index.jsp?respuesta=" + mensaje);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
