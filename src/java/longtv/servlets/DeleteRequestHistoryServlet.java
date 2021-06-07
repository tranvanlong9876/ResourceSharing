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
import longtv.daos.HistoryDAO;
import longtv.dtos.AccountDTO;
import longtv.dtos.AccountErrorObject;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DeleteRequestHistoryServlet", urlPatterns = {"/DeleteRequestHistoryServlet"})
public class DeleteRequestHistoryServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "ViewHistoryHeaderServlet";
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
            HttpSession session = request.getSession();
            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNTDETAIL");
            if(account != null) {
                String userID = account.getUsername();
                String orderID = request.getParameter("orderID");
                String statusName = request.getParameter("statusName");
                if(statusName.equals("New") || statusName.equals("Denied")) {
                    HistoryDAO dao = new HistoryDAO();
                    if(dao.deleteHistoryHeader(userID, orderID)) {
                        request.setAttribute("STATUS_HISTORY", "Your OrderID: " + orderID + " has been deleted!");
                    } else {
                        request.setAttribute("STATUS_HISTORY", "Can not find request in your account.");
                    }
                } else {
                    request.setAttribute("STATUS_HISTORY", "You can not delete the order that is already approved!");
                }
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("ERROR at DeleteRequestHistoryServlet: " + e.getMessage());
            AccountErrorObject errorObject = new AccountErrorObject();
            errorObject.setErrorServlet("Deleting Request");
            errorObject.setErrorDetail("Can not delete request at this moment, please try again later");
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
