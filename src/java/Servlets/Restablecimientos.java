/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.UsuariosJpaController;
import Entidades.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "Restablecimientos", urlPatterns = {"/Restablecimientos"})
public class Restablecimientos extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String btn = request.getParameter("BtnRestablecer");
        switch (btn) {
            case "RestablcerPasswordHome":
                restablecimientoHome(request, response);
                break;
            case "RestablercerAdmin":
                restablecimientoAdmin(request, response);
                break;
            default:
                break;
        }

    }

    private void restablecimientoHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuariosJpaController controlUsuarios = new UsuariosJpaController();
        Usuarios usuarioEncontrado = new Usuarios();

        /*Encontramos inputs*/
        int CedulaUser = Integer.parseInt(request.getParameter("numeroDocumentoCambio"));
        String passwordNueva = request.getParameter("PasswordNueva");
        String passwordConfirmar = request.getParameter("ConfirmacionPassword");

        usuarioEncontrado = controlUsuarios.findUsuarios(CedulaUser);
        String mensajeUrl;

        if (passwordNueva.equals(passwordConfirmar)) {
            try {
                usuarioEncontrado.setClave(usuarioEncontrado.EncryptarClave(passwordNueva));
                controlUsuarios.edit(usuarioEncontrado);
                mensajeUrl = "CambioClave";
                response.sendRedirect("index.jsp?respuesta=" + mensajeUrl);
            } catch (Exception ex) {
                Logger.getLogger(Restablecimientos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            mensajeUrl = "confirmacionFallida";
            response.sendRedirect("Home/Home.jsp?respuesta=" + mensajeUrl);
        }
    }

    private void restablecimientoAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UsuariosJpaController usuarioController = new UsuariosJpaController();
        Usuarios usuario = new Usuarios();

        String cedulaStr = request.getParameter("CC");
        String cedula = request.getParameter("cedula");
        String MensajeUrl;
        if (cedula == "" || cedulaStr == "") {
            MensajeUrl = "Vacios";
            response.sendRedirect("Views/Restablecimientos.jsp?respuesta=" + MensajeUrl);
        } else {
            int Cc = Integer.parseInt(cedulaStr);
            usuario = usuarioController.findUsuarios(Cc);
            if (usuario == null || usuario.getRol() == 2) {
                MensajeUrl = "UsuarioNoValido";
                response.sendRedirect("Views/Restablecimientos.jsp?respuesta=" + MensajeUrl);
            } else {
                //Extraemos CC
                String cedulaEncriptar = usuario.EncryptarClave(cedulaStr);
                //Asignamos contraseña
                usuario.setClave(cedulaEncriptar);
                try {
                    usuarioController.edit(usuario);
                    MensajeUrl = "restablecimientoExitoso";
                    response.sendRedirect("ViewsAdministrador/Restablecimientos.jsp?respuesta=" + MensajeUrl);
                } catch (Exception ex) {
                    Logger.getLogger(Restablecimientos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
