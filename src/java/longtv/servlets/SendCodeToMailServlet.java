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
import longtv.utils.SendEmail;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SendCodeToMailServlet", urlPatterns = {"/SendCodeToMailServlet"})
public class SendCodeToMailServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "verifyaccount.jsp";
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
            String email = request.getParameter("txtID");
            String fullName = request.getParameter("txtFullname");
            int code = (int) (100000 + (Math.random() * 899999));
            AccountDAO dao = new AccountDAO();
            if(dao.setCodeToVerifyAccount(code, email)) {
                SendEmail.sendMail(email, code, fullName);
                request.setAttribute("EMAIL_VERIFY", email);
                request.setAttribute("FULLNAME_VERIFY", fullName);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("ERROR at SendCodeToMailServlet: " + e.getMessage());
            AccountErrorObject errorObject = new AccountErrorObject();
            errorObject.setErrorServlet("Sending Code To Email");
            errorObject.setErrorDetail("Error occur while sending code to your email account.");
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
