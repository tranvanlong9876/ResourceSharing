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
import longtv.daos.AccountDAO;
import longtv.dtos.AccountDTO;
import longtv.dtos.AccountErrorObject;
import longtv.utils.VerifyCaptcha;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private static final String SUCCESS = "SearchResourceServlet";
    private static final String ERROR = "error.jsp";
    private static final String FAILED = "index.jsp";
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
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            boolean checkCaptcha = VerifyCaptcha.verify(gRecaptchaResponse);
            if(checkCaptcha) {
                String username = request.getParameter("txtID").trim();
                String password = request.getParameter("txtPassword").trim();
                
                if(username == null) {
                    username = "";
                }
                if(password == null) {
                    password = "";
                }
                
                AccountDAO dao = new AccountDAO();
                AccountDTO dto = dao.checkLogin(username, password);
                if(dto != null) {
                    if(dto.getStatus().equals("Active")) {
                        HttpSession session = request.getSession();
                        session.setAttribute("ACCOUNTDETAIL", dto);
                        url = SUCCESS;
                    } else {
                        request.setAttribute("LOGINSTATUS", "Email: " + username + " is not verified yet, check your mailbox or <a href=\"DispatchServlet?action=SendMail&txtID=" + username + "&txtFullname=" + dto.getName() + "\">Resend Code</a>.");
                        url = FAILED;
                    }
                } else {
                    request.setAttribute("LOGINSTATUS", "Invalid UserId or Password");
                    url = FAILED;
                }
            } else {
                request.setAttribute("LOGINSTATUS", "Captcha is not correct!");
                url = FAILED;
            }
            
        } catch (Exception e) {
            log("ERROR at LoginServlet: " + e.getMessage());
            AccountErrorObject errorObj = new AccountErrorObject();
            errorObj.setErrorServlet("Login Account Controller");
            errorObj.setErrorDetail("Error occur while login account");
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
