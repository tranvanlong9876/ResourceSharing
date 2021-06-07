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
import longtv.dtos.AccountDTO;
import longtv.dtos.AccountErrorObject;
import longtv.dtos.BookDTO;
import longtv.dtos.CartDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "SearchResourceServlet";
    private static final String NOTLOGIN = "index.jsp";
    private static final String INVALID_ROLE = "SearchResourceServlet";
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
            if (account != null) {
                if (account.getRole().equals("Employee") || account.getRole().equals("Leader")) {
                    String itemNo = request.getParameter("txtItemNo");
                    String rentDate = request.getParameter("txtRentTime");
                    String itemID = request.getParameter("txtItemID");
                    String itemQuantity = request.getParameter("cboRentQuantity");
                    String itemName = request.getParameter("txtItemName");
                    CartDTO cart = (CartDTO) session.getAttribute("CARTDETAIL");
                    int resourceId = Integer.parseInt(itemID);
                    int resourceQuantity = Integer.parseInt(itemQuantity);
                    if(cart == null) {
                        cart = new CartDTO();
                    }
                    BookDTO bookDetail = new BookDTO(rentDate, resourceQuantity, resourceId, itemName);
                    cart.addToCart(bookDetail);
                    cart.setUserID(account.getUsername());
                    session.setAttribute("CARTDETAIL", cart);
                    url = SUCCESS;
                    request.setAttribute("ALREADY_SENT", "Item No: " + itemNo + " has been added to cart, check your renting resource in your cart.");
                } else {
                    url = INVALID_ROLE;
                    request.setAttribute("ALREADY_SENT", "Manager role can not rent resource.");
                }
            } else {
                url = NOTLOGIN;
                request.setAttribute("LOGINSTATUS", "You must login to use or rent our resource.");
            }
        } catch (Exception e) {
            log("ERROR at AddToCartServlet: " + e.getMessage());
            AccountErrorObject errorObject = new AccountErrorObject();
            errorObject.setErrorServlet("Adding Item To Cart");
            errorObject.setErrorDetail("Can not add to cart at this moment, please try again later");
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
