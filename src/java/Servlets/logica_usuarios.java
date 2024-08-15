/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.CentroJpaController;
import Controladores.CoordinadorJpaController;
import Controladores.InstructorJpaController;
import Controladores.UsuariosJpaController;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Centro;
import Entidades.Coordinador;
import Entidades.Instructor;
import Entidades.Usuarios;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
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
        String button = request.getParameter("accion");
        PrintWriter n = response.getWriter();

        switch (button) {
            case "guaradarUsuarios":
                SaveUsuario(request, response);
                break;
            case "actualizar":
                actualizarUsuario(request, response);
                break;

            case "eliminar":
                deleteUser(request, response);
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
            newUsuario.setNombreCompleto(nombre);
            newUsuario.setApelliodo(apellido);
            newUsuario.setRol(userRol);
            newUsuario.setClave(newUsuario.EncryptarClave(request.getParameter("CedulaUsuario")));
            /*En caso de administrador o RH*/
            if (userRol == 0 || userRol == 3) {
                try {
                    mensajeUrl = "usuarioguardado";
                    usuarioController.create(newUsuario);
                    enviarRespuestaExito(response, "¡Usuario Registrado correctamente!");
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
                    enviarRespuestaExito(response, "¡Coordinador Registrado correctamente!");
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
                    /* usuarioController.create(newUsuario);*/
                    ControlInstructor.create(newInstructor);
                    enviarRespuestaExito(response, "¡Instructor Registrado correctamente!");
                } catch (Exception ex) {
                    Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {

            try {
                enviarRespuestaError(response, "Usuario Registrado");
            } catch (Exception e) {
                Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuariosJpaController usuarioController = new UsuariosJpaController();
        CoordinadorJpaController controlCoordinador = new CoordinadorJpaController();
        InstructorJpaController controlInstructor = new InstructorJpaController();
        CentroJpaController controlCentro = new CentroJpaController();

        int userId = Integer.parseInt(request.getParameter("CedulaUsuarioOp"));
        String nombre = request.getParameter("nombreOp");
        String apellido = request.getParameter("apellidoOp");
        int userRol = Integer.parseInt(request.getParameter("rolUsuarioOp"));

        Usuarios usuarioEntranda = usuarioController.findUsuarios(userId);

        boolean cambios = false;
        Usuarios cambiosUsuarios = new Usuarios();
        if (!usuarioEntranda.getNombreCompleto().equals(nombre)
                || !usuarioEntranda.getApelliodo().equals(apellido)
                || usuarioEntranda.getRol() != userRol) {
            cambios = true;

        }
        cambiosUsuarios.setIdusuario(userId);
        cambiosUsuarios.setNombreCompleto(nombre);
        cambiosUsuarios.setApelliodo(apellido);
        cambiosUsuarios.setRol(userRol);
        cambiosUsuarios.setEstado(1);
        cambiosUsuarios.setClave(cambiosUsuarios.EncryptarClave(request.getParameter("CedulaUsuarioOp")));

        switch (userRol) {
            case 0:
            case 3:
                try {
                if (cambios) {
                    usuarioController.edit(cambiosUsuarios);
                    enviarRespuestaExito(response, "Usuario Modificaco exitosamente");
                } else {
                    enviarRespuestaError(response, "Sin Modificaciones");
                }
            } catch (Exception ex) {
                Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case 1:
                Coordinador coordinadorEntrada = controlCoordinador.findCoordinador(userId);
                // Coordinador CoordinadorModificar = new Coordinador();
                String email = request.getParameter("emailOp");
                int centroId = Integer.parseInt(request.getParameter("centroOp"));
                Centro centroSeleccionado = controlCentro.findCentro(centroId);
                if (!coordinadorEntrada.getCorreo().equals(email)
                        || !coordinadorEntrada.getCentroIdcentro().equals(centroId)) {
                    cambios = true;
                }
                if (cambios) {
                    coordinadorEntrada.setIdcoordinador(userId);
                    coordinadorEntrada.setNombres(nombre);
                    coordinadorEntrada.setApellidos(apellido);
                    coordinadorEntrada.setCorreo(email);
                    coordinadorEntrada.setCentroIdcentro(centroSeleccionado);
                    try {
                        controlCoordinador.edit(coordinadorEntrada);
                        usuarioController.edit(cambiosUsuarios);
                        enviarRespuestaExito(response, "Coordinador modificado exitosamente");
                    } catch (Exception ex) {
                        Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    enviarRespuestaError(response, "Sin Modificaciones");
                }
                break;
            case 2:
                Instructor instructorEntrada = controlInstructor.findInstructor(userId);
                String emailInsatructor = request.getParameter("emailOp");
                int idCoordinador = Integer.parseInt(request.getParameter("CoordinadorOp"));
                Coordinador cordinadorSeleccionado = controlCoordinador.findCoordinador(idCoordinador);
                String Telefono = request.getParameter("telefonoOp");

                if (!instructorEntrada.getCorreo().equals(emailInsatructor)
                        || !instructorEntrada.getCoordinadorIdcoordinador().equals(cordinadorSeleccionado)
                        || !instructorEntrada.getTelefono().equals(Telefono)) {
                    cambios = true;
                }

                if (cambios) {
                    instructorEntrada.setIdinstructor(userId);
                    instructorEntrada.setNombres(nombre);
                    instructorEntrada.setApellidos(apellido);
                    instructorEntrada.setTelefono(Telefono);
                    instructorEntrada.setCorreo(emailInsatructor);
                    instructorEntrada.setCentroIdcentro(cordinadorSeleccionado.getCentroIdcentro());
                    instructorEntrada.setCoordinadorIdcoordinador(cordinadorSeleccionado);
                    try {
                        controlInstructor.edit(instructorEntrada);
                        usuarioController.edit(cambiosUsuarios);
                        enviarRespuestaExito(response, "Instructor modificado exitosamente");
                    } catch (Exception ex) {
                        Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    enviarRespuestaError(response, "Sin Modificaciones");
                }
                break;
            default:
                break;
        }

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        UsuariosJpaController controlUsuario = new UsuariosJpaController();

        int id = Integer.parseInt(request.getParameter("IdEliminar"));
        Usuarios usuarioEncontrado = controlUsuario.findUsuarios(id);

        switch (usuarioEncontrado.getRol()) {
            case 0:
            case 3: {
                try {
                    controlUsuario.destroy(id);
                    enviarRespuestaExito(response, "Usuario eliminado");
                } catch (Exception ex) {
                    Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case 1:
                CoordinadorJpaController controlCoordinador = new CoordinadorJpaController();
                try {
                    controlCoordinador.destroy(id);
                    controlUsuario.destroy(id);
                    enviarRespuestaExito(response, "Coordinador Eliminado");
                } catch (Exception ex) {
                    Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                InstructorJpaController controlIns = new InstructorJpaController();

                try {
                    controlIns.destroy(id);
                    controlUsuario.destroy(id);
                    enviarRespuestaExito(response, "Instructor Eliminado");
                } catch (Exception ex) {
                    Logger.getLogger(logica_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            default:
                break;
        }

    }

    // Método para enviar una respuesta JSON de éxito
    private void enviarRespuestaExito(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("estado", "exito");
        respuesta.put("mensaje", mensaje);

        String json = new Gson().toJson(respuesta);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // Método para enviar una respuesta JSON de error
    private void enviarRespuestaError(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("estado", "error");
        respuesta.put("mensaje", mensaje);

        String json = new Gson().toJson(respuesta);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
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
