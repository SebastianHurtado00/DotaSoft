/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.CentroJpaController;
import Controladores.CoordinadorJpaController;
import Controladores.InstructorJpaController;
import Controladores.UsuariosJpaController;
import Entidades.Centro;
import Entidades.Coordinador;
import Entidades.Instructor;
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
@WebServlet(name = "logica_usuarios", urlPatterns = {"/logica_usuarios"})
public class logica_usuarios extends HttpServlet {

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
        String button = request.getParameter("action");
        PrintWriter n = response.getWriter();

        switch (button) {
            case "guaradarUsuarios":
                SaveUsuario(request, response);
                break;
            default:
                break;
        }

    }

    private void SaveUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuariosJpaController usuarioController = new UsuariosJpaController();
        CoordinadorJpaController controlCoordinador = new CoordinadorJpaController();
        InstructorJpaController ControlInstructor = new InstructorJpaController();
        CentroJpaController controlCentro = new CentroJpaController();

        Usuarios existenceValid;

        int userId = Integer.parseInt(request.getParameter("CedulaUsuario"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        int userRol = Integer.parseInt(request.getParameter("rolUsuario"));

        existenceValid = usuarioController.findUsuarios(userId);
        String urlDeRetorno = request.getHeader("referer");
        String mensajeUrl;

        if (existenceValid == null) {
            Usuarios newUsuario = new Usuarios();
            newUsuario.setIdusuario(userId);
            newUsuario.setEstado(1);
            newUsuario.setNombreCompleto(nombre + " " + apellido);
            newUsuario.setRol(userRol);
            newUsuario.setClave(newUsuario.EncryptarClave(request.getParameter("CedulaUsuario")));
            /*En caso de administrador o RH*/
            if (userRol == 0 || userRol == 3) {
                try {
                    mensajeUrl = "usuarioguardado";
                    usuarioController.create(newUsuario);
                    redirectWithMessage(response, urlDeRetorno, mensajeUrl);
                } catch (Exception ex) {
                    Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (userRol == 1) {
                Coordinador newCoordinado = new Coordinador();

                String email = request.getParameter("email");
                int centroId = Integer.parseInt(request.getParameter("centro"));
                Centro centroSeleccionado = controlCentro.findCentro(centroId);

                newCoordinado.setIdcoordinador(userId);
                newCoordinado.setNombres(nombre);
                newCoordinado.setApellidos(apellido);
                newCoordinado.setCorreo(email);
                newCoordinado.setCentroIdcentro(centroSeleccionado);

                try {
                    mensajeUrl = "usuarioguardado";
                    usuarioController.create(newUsuario);
                    controlCoordinador.create(newCoordinado);
                    redirectWithMessage(response, urlDeRetorno, mensajeUrl);
                } catch (Exception ex) {
                    Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (userRol == 2) {
                Instructor newInstructor = new Instructor();

                String email = request.getParameter("email");
                int idCoordinador = Integer.parseInt(request.getParameter("Coordinador"));
                Coordinador cordinadorSeleccionado = controlCoordinador.findCoordinador(idCoordinador);
                String Telefono = request.getParameter("telefono");
           
                newInstructor.setIdinstructor(userId);
                newInstructor.setNombres(nombre);
                newInstructor.setApellidos(apellido);
                newInstructor.setTelefono(Telefono);
                newInstructor.setCorreo(email);
                newInstructor.setCentroIdcentro(cordinadorSeleccionado.getCentroIdcentro());
                newInstructor.setCoordinadorIdcoordinador(cordinadorSeleccionado);
                try {
                    mensajeUrl = "usuarioguardado";
                    usuarioController.create(newUsuario);
                    ControlInstructor.create(newInstructor);
                    redirectWithMessage(response, urlDeRetorno, mensajeUrl);
                } catch (Exception ex) {
                    Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            mensajeUrl = "usuarioregistrado";
            try {
                redirectWithMessage(response, urlDeRetorno, mensajeUrl);
            } catch (Exception e) {
                Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void redirectWithMessage(HttpServletResponse response, String urlDeRetorno, String mensajeUrl) throws IOException {

        // Verifica si la URL ya contiene un parámetro de respuesta
        if (urlDeRetorno.contains("respuesta=")) {
            // Elimina el parámetro de respuesta existente de la URL
            urlDeRetorno = urlDeRetorno.replaceAll("[?&]respuesta=.*?(?:&|$)", "");
        }

        // Ahora agrega el nuevo parámetro de respuesta
        if (urlDeRetorno.contains("?")) {
            // La URL ya tiene una cadena de consulta, agrega el parámetro adecuadamente
            response.sendRedirect(urlDeRetorno + "&respuesta=" + mensajeUrl);
        } else {
            // La URL no tiene una cadena de consulta, agrega el parámetro con "?"
            response.sendRedirect(urlDeRetorno + "?respuesta=" + mensajeUrl);
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
