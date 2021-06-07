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
import javax.servlet.http.HttpSession;
import longtv.daos.HistoryDAO;
import longtv.dtos.AccountDTO;
import longtv.dtos.AccountErrorObject;
import longtv.dtos.FullHistoryDetailDTO;
import longtv.dtos.HistoryDetailDTO;
import longtv.dtos.HistoryHeaderDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ViewHistoryDetailServlet", urlPatterns = {"/ViewHistoryDetailServlet"})
public class ViewHistoryDetailServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "historydetail.jsp";
    private static final String BACKTOLOGIN = "index.jsp";
    private static final String FAILED = "viewhistory.jsp";
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
                HistoryDAO dao = new HistoryDAO();
                HistoryHeaderDTO statusOrder = dao.loadHistoryHeaderByOrderID(userID, orderID);
                List<HistoryDetailDTO> listItem = dao.loadHistoryDetail(userID, orderID);
                if(statusOrder != null && listItem.size() > 0) {
                    FullHistoryDetailDTO fullDetail = new FullHistoryDetailDTO(listItem, statusOrder);
                    request.setAttribute("HISTORY_DETAIL", fullDetail);
                    url = SUCCESS;
                } else {
                    url = FAILED;
                    request.setAttribute("STATUS_HISTORY", "Can not find detail of this order in your account.");
                }
            } else {
                request.setAttribute("LOGINSTATUS", "Your session has timed out, please login again!");
                url = BACKTOLOGIN;
            }
        } catch (Exception e) {
            log("ERROR at ViewHistoryDetailServlet: " + e.getMessage());
            AccountErrorObject errorObject = new AccountErrorObject();
            errorObject.setErrorServlet("Viewing Order Detail");
            errorObject.setErrorDetail("Can not show your order detail at this moment, please try again later");
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
