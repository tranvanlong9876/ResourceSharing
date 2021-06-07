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
import longtv.dtos.RequestDTO;
import longtv.dtos.RequestFillDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoadRequestServlet", urlPatterns = {"/LoadRequestServlet"})
public class LoadRequestServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "managerequest.jsp";

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
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            String search = request.getParameter("txtManageItem");
            String status = request.getParameter("requestStatusCBO");
            String page = request.getParameter("txtPage");
            if(search == null) {
                search = "";
            }
            if(page == null || page.equals("")) {
                page = "1";
            }
            if(status == null || status.equals("")) {
                status = "1";
            }
            
            int statusID = Integer.parseInt(status);
            int currentPage = Integer.parseInt(page);
            
            RequestDAO dao = new RequestDAO();
            int totalRows;
            if(statusID == 1) {
                totalRows = dao.loadAllRowsNew(statusID, search);
            } else {
                totalRows = dao.loadAllRowsNotNew(statusID, search);
            }
            int totalPage = (int) (Math.ceil(totalRows) / 20 + 1);
            if (currentPage > totalPage) {
                currentPage = totalPage;
            } else if (currentPage < 1) {
                currentPage = 1;
            }
            List<RequestDTO> allRequest;
            if(statusID == 1) {
                allRequest = dao.loadRequestByStatusNew(statusID, currentPage, search); 
            } else {
                allRequest = dao.loadRequestByStatusNotNew(statusID, currentPage, search); 
            }           
            RequestFillDTO fill = new RequestFillDTO(currentPage, statusID, totalPage);
            if(allRequest.size() > 0) {
                request.setAttribute("REQUEST_LIST", allRequest);
            }
            request.setAttribute("FILL_DETAIL", fill);
            url = SUCCESS;
        } catch (Exception e) {
            log("ERROR at LoadRequestServlet: " + e.getMessage());
            AccountErrorObject errorObj = new AccountErrorObject();
            errorObj.setErrorServlet("Loading Request");
            errorObj.setErrorDetail("Error occur while loading request list");
            request.setAttribute("ERROR_PAGE", errorObj);
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
