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
import longtv.dtos.HistoryHeaderDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ViewHistoryHeaderServlet", urlPatterns = {"/ViewHistoryHeaderServlet"})
public class ViewHistoryHeaderServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewhistory.jsp";
    private static final String INVALIDROLE = "SearchResourceServlet";
    private static final String NOTLOGIN = "index.jsp";
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
                if(account.getRole().equals("Leader") || account.getRole().equals("Employee")) {
                    String userID = account.getUsername();
                    String dateFrom = request.getParameter("txtDateFrom");
                    String dateTo = request.getParameter("txtDateTo");
                    String date = "";
                    if(dateFrom == null) {
                        dateFrom = "";
                    }
                    
                    if(dateTo == null) {
                        dateTo = "";
                    }
                    HistoryDAO dao = new HistoryDAO();
                    List<HistoryHeaderDTO> historyDetail;
                    if(dateFrom.length() == 0 && dateTo.length() == 0) {
                        historyDetail = dao.loadHistoryHeader(userID, date);
                    } else if(dateFrom.length() == 0 || dateTo.length() == 0) {
                        if(dateFrom.length() > 0) {
                            date = dateFrom;
                        } else {
                            date = dateTo;
                        }
                        historyDetail = dao.loadHistoryHeader(userID, date);
                    } else {
                        historyDetail = dao.loadHistoryHeaderBetweenDate(userID, dateFrom, dateTo);
                    }
                    if(historyDetail.size() > 0) {
                        request.setAttribute("HISTORYHEADER", historyDetail);
                    }
                    url = SUCCESS;
                } else {
                    url = INVALIDROLE;
                }
            } else {
                url = NOTLOGIN;
            }
        } catch (Exception e) {
            log("ERROR at ViewHistoryHeaderServlet: " + e.getMessage());
            AccountErrorObject errorObject = new AccountErrorObject();
            errorObject.setErrorServlet("Showing Order History");
            errorObject.setErrorDetail("Can not show your history at this moment, please try again later");
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
