/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtv.dtos.AccountErrorObject;
import longtv.dtos.CartDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DeleteCartServlet", urlPatterns = {"/DeleteCartServlet"})
public class DeleteCartServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "cart.jsp";
    private static final String NOTAVAILABLE = "index.jsp";
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
        String url = ERROR;
        try {
            String id = request.getParameter("idResource");
            int idResource = Integer.parseInt(id);
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CARTDETAIL");
            if(cart != null) {
                if(cart.getCart().containsKey(idResource)) {
                    cart.deleteCart(idResource);
                    url = SUCCESS;
                    request.setAttribute("CARTSTATUS", "Delete Item Successfully.");
                } else {
                    url = SUCCESS;
                    request.setAttribute("CARTSTATUS", "Can not delete, this Resource is not in your cart!");
                }
            } else {
                url = NOTAVAILABLE;
                request.setAttribute("LOGINSTATUS", "Your session has timed out, please login again!");
            }
        } catch (Exception e) {
            log("ERROR at DeleteCartServlet: " + e.getMessage());
            AccountErrorObject errorObject = new AccountErrorObject();
            errorObject.setErrorServlet("Deleting Item From Cart");
            errorObject.setErrorDetail("Can not delete item from cart at this moment, please try again later");
            request.setAttribute("ERROR_PAGE", errorObject);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
