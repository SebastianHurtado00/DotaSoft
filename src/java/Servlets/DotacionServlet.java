/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controladores.AreaJpaController;
import Controladores.ClimaJpaController;
import Controladores.DotacionJpaController;
import Controladores.ElementosJpaController;
import Controladores.SexoJpaController;
import Entidades.Area;
import Entidades.Clima;
import Entidades.Dotacion;
import Entidades.Elementos;
import Entidades.Sexo;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "DotacionServlet", urlPatterns = {"/DotacionServlet"})
public class DotacionServlet extends HttpServlet {

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
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "guardar":
                    botonGuardar(request, response);
                    break;
                case "actualizar":
                    botonEditar(request, response);
                    break;
                case "eliminar":
                    botonElimina(request, response);
                    break;
                default:
                    // Acción no válida
                    break;
            }
        }
    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int ListaAreas = Integer.parseInt(request.getParameter("AreaListaGdDo"));
        int ListaElementos = Integer.parseInt(request.getParameter("ElementoListaGdDo"));
        int ListaSexo = Integer.parseInt(request.getParameter("SexoListaGdDo"));
        int ListaClima = Integer.parseInt(request.getParameter("ClimaListaGdDo"));
        int cantidad = Integer.parseInt(request.getParameter("cantidadDo"));

        DotacionJpaController Controlador = new DotacionJpaController();
        Dotacion guarda = new Dotacion();
        AreaJpaController areaControlador = new AreaJpaController();
        ElementosJpaController elemControlador = new ElementosJpaController();
        SexoJpaController sexoControlador = new SexoJpaController();
        ClimaJpaController climaControlodor = new ClimaJpaController();

        try {

                Area area = areaControlador.findArea(ListaAreas);
                guarda.setAreaIdarea(area);
                Elementos elem = elemControlador.findElementos(ListaElementos);
                guarda.setElementosIdelemento(elem);
                Sexo sexos = sexoControlador.findSexo(ListaSexo);
                guarda.setSexoIdsexo(sexos);
                Clima clima = climaControlodor.findClima(ListaClima);
                guarda.setClimaIdclima(clima);
                guarda.setCantidad(cantidad);
                Controlador.create(guarda);
                enviarRespuestaExito(response, "¡Registro guardado exitosamente!");

        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonElimina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoDotacionElm"));
        DotacionJpaController Controlador = new DotacionJpaController();

        try {
            Controlador.destroy(codigo);
            enviarRespuestaExito(response, "¡Registro Eliminado exitosamente!");
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
        }

    }
    
     public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        int codigo = Integer.parseInt(request.getParameter("codigoDotacionElm")); 
        int ListaAreas = Integer.parseInt(request.getParameter("AreaListaGdDotacion"));
        int ListaElementos = Integer.parseInt(request.getParameter("ElementoListaGdDotacion"));
        int ListaSexo = Integer.parseInt(request.getParameter("SexoListaGdDotacion"));
        int ListaClima = Integer.parseInt(request.getParameter("ClimaListaGdDotacion"));
        int cantidad = Integer.parseInt(request.getParameter("cantidadDotacionEl"));
        
        DotacionJpaController Controlador = new DotacionJpaController();
        Dotacion existe = Controlador.findDotacion(codigo);
        AreaJpaController areaControlador = new AreaJpaController();
        ElementosJpaController elemControlador = new ElementosJpaController();
        SexoJpaController sexoControlador = new SexoJpaController();
        ClimaJpaController climaControlodor = new ClimaJpaController();
       
        try {
            if (existe != null) {
                Area area = areaControlador.findArea(ListaAreas);
                existe.setAreaIdarea(area);
                Elementos elem = elemControlador.findElementos(ListaElementos);
                existe.setElementosIdelemento(elem);
                Sexo sexos = sexoControlador.findSexo(ListaSexo);
                existe.setSexoIdsexo(sexos);
                Clima clima = climaControlodor.findClima(ListaClima);
                existe.setClimaIdclima(clima);
                existe.setCantidad(cantidad);
               
                Controlador.edit(existe);
                
                enviarRespuestaExito(response, "¡Registro Editado exitosamente!");
            }
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
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
