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
import longtv.daos.AccountDAO;
import longtv.dtos.AccountErrorObject;

/**
 *
 * @author Admin
 */
@WebServlet(name = "VerifyEmailAccountServlet", urlPatterns = {"/VerifyEmailAccountServlet"})
public class VerifyEmailAccountServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "index.jsp";
    private static final String VERIFY_FAIL_SUBMIT = "verifyaccount.jsp";
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
            String receiveCode = request.getParameter("code");
            if(receiveCode == null || receiveCode.equals("")) {
                receiveCode = "000000";
            }
            int code = Integer.parseInt(receiveCode);
            String email = request.getParameter("email");
            String typeSubmit = request.getParameter("verifyType");
            String fullName = "";
            if(typeSubmit.equals("submitCode")) {
                fullName = request.getParameter("fullName");
            }
            AccountDAO dao = new AccountDAO();
            if(dao.checkVerificationCode(email, code)) {
                if(dao.activeAccount(email)) {
                    url = SUCCESS;
                    request.setAttribute("LOGINSTATUS", "Your account is verified, login now!");
                }
            } else {
                if(typeSubmit.equals("submitCode")) {
                    url = VERIFY_FAIL_SUBMIT;
                    request.setAttribute("INVALID_CODE", "Wrong code, try again!");
                    request.setAttribute("FULLNAME_VERIFY", fullName);
                    request.setAttribute("EMAIL_VERIFY", email);
                }
            }
        } catch (Exception e) {
            log("ERROR at VerifyEmailAccountServlet: " + e.getMessage());
            AccountErrorObject errorObj = new AccountErrorObject();
            errorObj.setErrorServlet("Verifying Email Account");
            errorObj.setErrorDetail("Your link is expire and can not be verified, please check your newest link");
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
