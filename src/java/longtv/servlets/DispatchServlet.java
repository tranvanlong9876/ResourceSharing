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
import longtv.dtos.AccountErrorObject;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DispatcherServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {
    private static final String ERROR_PAGE = "error.jsp";
    private static final String LOGIN = "LoginServlet";
    private static final String LOGOUT = "LogOutServlet";
    private static final String SIGNUP = "signup.jsp";
    private static final String LOGIN_GOOGLE = "LoginGoogleServlet";
    private static final String SIGNUPACTION = "CreateAccountServlet";
    private static final String BACKTOLOGIN = "index.jsp";
    private static final String SENDMAIL = "SendCodeToMailServlet";
    private static final String VERIFYACCOUNT = "VerifyEmailAccountServlet";
    private static final String LOAD_SEARCHDATA = "SearchResourceServlet";
    private static final String SEND_RENT_REQUEST = "RentRequestServlet";
    private static final String LOAD_REQUEST = "LoadRequestServlet";
    private static final String ADDTOCART = "AddToCartServlet";
    private static final String DELETECART = "DeleteCartServlet";
    private static final String SENDREQUEST = "SendRequestServlet";
    private static final String LOADREQUESTDETAIL = "LoadRequestDetailServlet";
    private static final String ACCEPTREQUEST = "AcceptRequestServlet";
    private static final String DELETEREQUEST = "DeleteRequestServlet";
    private static final String VIEWHISTORYHEADER = "ViewHistoryHeaderServlet";
    private static final String DELETEHISTORYHEADER = "DeleteRequestHistoryServlet";
    private static final String VIEWHISTORYDETAIL = "ViewHistoryDetailServlet";
    private static final String VIEWCART = "cart.jsp";

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
        String url = ERROR_PAGE;
        try {
            String action = request.getParameter("action");
            if(action == null) {
                action = "";
            }
            if(action.equals("Login")) {
                url = LOGIN;
            } else if(action.equals("Log Out")) {
                url = LOGOUT;
            } else if(action.equals("Register Account")) {
                url = SIGNUP;
            } else if(action.equals("loginGoogle")) {
                url = LOGIN_GOOGLE;
            } else if(action.equals("Create Account")) {
                url = SIGNUPACTION;
            } else if(action.equals("BackToLogin")){
                url = BACKTOLOGIN;
            } else if(action.equals("SendMail")) {
                url = SENDMAIL;
            } else if(action.equals("Submit Code") || action.equals("VerifyEmailAccount")) {
                url = VERIFYACCOUNT;
            } else if(action.equals("Search") || action.equals("Back To Search Page") || action.equals("Rent Resource")) {
                url = LOAD_SEARCHDATA;
            } else if(action.equals("Book")) {
                url = SEND_RENT_REQUEST;
            } else if(action.equals("")) {
                url = BACKTOLOGIN;
            } else if(action.equals("Search Request") || action.equals("Manage Request")) {
                url = LOAD_REQUEST;
            } else if(action.equals("Add To Cart")) {
                url = ADDTOCART;
            } else if(action.equals("Delete Cart")) {
                url = DELETECART;
            } else if(action.equals("Send Request")) {
                url = SENDREQUEST;
            } else if(action.equals("Accept Request")) {
                url = ACCEPTREQUEST;
            } else if(action.equals("Delete Request")) {
                url = DELETEREQUEST;
            } else if(action.equals("View Request Details")) {
                url = LOADREQUESTDETAIL;
            } else if(action.equals("View Request History") || action.equals("Fill Request") || action.equals("Get All Request")) {
                url = VIEWHISTORYHEADER;
            } else if(action.equals("Delete History Request")) {
                url = DELETEHISTORYHEADER;
            } else if(action.equals("View History Detail")) {
                url = VIEWHISTORYDETAIL;
            } else if(action.equals("viewCart")) {
                url = VIEWCART;
            } else {
                AccountErrorObject errorObject = new AccountErrorObject();
                errorObject.setErrorServlet("Filtering Controller");
                errorObject.setErrorDetail("Your action is invalid");
                request.setAttribute("ERROR_PAGE", errorObject);
            }
        } catch (Exception e) {
            log("ERROR at DispatchServlet: " + e.getMessage());
            AccountErrorObject errorObject = new AccountErrorObject();
            errorObject.setErrorServlet("Filtering Controller");
            errorObject.setErrorDetail("Your action is invalid");
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
