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
import longtv.dtos.RegisterDTO;
import longtv.utils.ValidateInput;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String FAILED = "signup.jsp";
    private static final String SUCCESS = "SendCodeToMailServlet";
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
            String phone = request.getParameter("txtPhone");
            String fullName = request.getParameter("txtFullname");
            String password = request.getParameter("txtPassword");
            String confirmPassword = request.getParameter("txtConfirm");
            String address = request.getParameter("txtAddress");
            
            RegisterDTO dto = new RegisterDTO(email, phone, fullName, password, confirmPassword, address);
            ValidateInput checkInput = new ValidateInput();
            AccountErrorObject errorObj = checkInput.statusValidate(dto);
            if(errorObj.isValidateStatus()) {
                AccountDAO dao = new AccountDAO();
                if(!dao.checkExistEmail(email)) {
                    if(dao.createAccount(dto)) {
                        url = SUCCESS;
                    }
                } else {
                    errorObj.setInvalidEmail("Email is already existed!");
                    request.setAttribute("INVALID_CREATEACCOUNT", errorObj);
                    url = FAILED;
                }
            } else {
                request.setAttribute("INVALID_CREATEACCOUNT", errorObj);
                url = FAILED;
            }
        } catch (Exception e) {
            log("ERROR at CreateAccountServlet: " + e.getMessage());
            AccountErrorObject errorObj = new AccountErrorObject();
            errorObj.setErrorServlet("Creating new account");
            errorObj.setErrorDetail("Error occur while creating account, please try again later");
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
