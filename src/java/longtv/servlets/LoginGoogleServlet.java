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
import longtv.utils.LoginGoogle;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/LoginGoogleServlet"})
public class LoginGoogleServlet extends HttpServlet {

    private static final String SUCCESS = "DispatchServlet?action=Search";
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
            String code = request.getParameter("code");
            if (code != null) {
                String accessToken = LoginGoogle.getToken(code);
                String email = LoginGoogle.getUserInfo(accessToken);
                if (email != null) {
                    AccountDAO dao = new AccountDAO();
                    AccountDTO dto = dao.loginWithGoogle(email);
                    if (dto != null) {
                        if (dto.getStatus().equals("New")) {
                            if (dao.activeAccount(email)) {
                                dto.setStatus(null);
                            }
                        }
                        HttpSession session = request.getSession();
                        session.setAttribute("ACCOUNTDETAIL", dto);
                        url = SUCCESS;
                    } else {
                        request.setAttribute("LOGINSTATUS", "Email " + email + " is not available in the system!");
                        url = FAILED;
                    }
                } else {
                    request.setAttribute("LOGINSTATUS", "Login cancelled!");
                    url = FAILED;
                }
            }
        } catch (Exception e) {
            log("ERROR at LoginGoogleServlet: " + e.getMessage());
            AccountErrorObject errorObj = new AccountErrorObject();
            errorObj.setErrorServlet("Login Using Google Account");
            errorObj.setErrorDetail("Error occur while login account");
            request.setAttribute("ERROR_PAGE", errorObj);
        } finally {
            if (url.equals(SUCCESS)) {
                response.sendRedirect(SUCCESS);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
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
