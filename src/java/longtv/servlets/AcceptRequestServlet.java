/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longtv.daos.RequestDAO;
import longtv.dtos.AccountErrorObject;
import longtv.dtos.ItemDetailDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AcceptRequestServlet", urlPatterns = {"/AcceptRequestServlet"})
public class AcceptRequestServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "LoadRequestDetailServlet";
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
            String orderID = request.getParameter("orderID");
            RequestDAO dao = new RequestDAO();
            List<ItemDetailDTO> detail = dao.loadRequestDetailByID(orderID);
            boolean canAccept = true;
            if (detail.size() > 0) {
                for (int i = 0; i < detail.size(); i++) {
                    int resourceID = detail.get(i).getResourceID();
                    int totalQuantity = dao.getResourceBasedOnID(resourceID);
                    int quantityOnUse = dao.getResourceCurrentlyOnUse(resourceID, detail.get(i).getRentTime());
                    if (totalQuantity - quantityOnUse - detail.get(i).getQuantity() < 0) {
                        canAccept = false;
                    }
                }
                if (canAccept) {
                    if(dao.acceptRequest(orderID)) {
                        url = SUCCESS;
                    }
                } else {
                    url = SUCCESS;
                }
            }
        } catch (Exception e) {
            log("ERROR at AcceptRequestServlet: " + e.getMessage());
            AccountErrorObject errorObject = new AccountErrorObject();
            errorObject.setErrorServlet("Accepting Request");
            errorObject.setErrorDetail("Can not accept request at this moment, please try again later");
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
