/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package JsonTablas;

import Controladores.AreaJpaController;
import Controladores.UsuariosJpaController;
import Entidades.Usuarios;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ConsultaUsuarios", urlPatterns = {"/ConsultaUsuarios"})
public class ConsultaUsuarios extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        
        cargarTabla(request, response);
    }
    
    private void cargarTabla(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        UsuariosJpaController userController = new UsuariosJpaController();
        List<Usuarios> userList = userController.findUsuariosEntities();
        
        List<Map<String, String>> userJsonList = new ArrayList<>();
        for (Usuarios usuario : userList) {
            Map<String, String> jsonUsuario = new HashMap<>();
            jsonUsuario.put("NumeroCC", usuario.getIdusuario().toString());
            jsonUsuario.put("NombreCompleto", usuario.getNombreCompleto());
            jsonUsuario.put("Rol", String.valueOf(usuario.getRol()));
            jsonUsuario.put("Estado", String.valueOf(usuario.getEstado()));
            
            userJsonList.add(jsonUsuario);
        }
        
        String json = new Gson().toJson(userJsonList);

        // Imprimir JSON generado para depuración
        System.out.println("JSON generado: " + json);
        
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
