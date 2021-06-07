/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtv.daos.BookingDAO;
import longtv.dtos.AccountDTO;
import longtv.dtos.AccountErrorObject;
import longtv.dtos.BookDTO;
import longtv.dtos.CartDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SendRequestServlet", urlPatterns = {"/SendRequestServlet"})
public class SendRequestServlet extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "SearchResourceServlet";
    private static final String BACKTOCART = "cart.jsp";
    private static final String BACKTOLOGIN = "index.jsp";

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
            AccountDTO accountDTO = (AccountDTO) session.getAttribute("ACCOUNTDETAIL");
            if (accountDTO != null) {
                if (accountDTO.getRole().equals("Employee") || accountDTO.getRole().equals("Leader")) {
                    String userId = accountDTO.getUsername();
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY-HHmmss");
                    Timestamp timestamp = new Timestamp(date.getTime());
                    String orderID = "VN-" + sdf.format(date);
                    CartDTO cart = (CartDTO) session.getAttribute("CARTDETAIL");
                    if (cart.getCart().size() > 0) {
                        BookingDAO dao = new BookingDAO();
                        if (dao.insertOrderHeader(userId, timestamp, orderID)) {
                            boolean check = true;
                            for (int i : cart.getCart().keySet()) {
                                BookDTO bookDTO = cart.getCart().get(i);
                                if (!dao.insertOrderDetail(bookDTO.getItemID(), bookDTO.getItemName(), bookDTO.getItemQuantity(), orderID, bookDTO.getRentDate())) {
                                    check = false;
                                }
                            }
                            if (check) {
                                url = SUCCESS;
                                request.setAttribute("ALREADY_SENT", "Request has been sent to Manager, see response in history!");
                                session.removeAttribute("CARTDETAIL");
                            }
                        }
                    } else {
                        url = BACKTOCART;
                        request.setAttribute("CARTSTATUS", "Can not send request, nothing is in your cart yet.");
                    }
                } else {
                    url = SUCCESS;
                    request.setAttribute("ALREADY_SENT", "Manager Can Not Rent Resource");
                }

            } else {
                url = BACKTOLOGIN;
                request.setAttribute("LOGINSTATUS", "Your session has timed out, login again!");
            }
        } catch (Exception e) {
            log("ERROR at SendRequestServlet: " + e.getMessage());
            AccountErrorObject errorObj = new AccountErrorObject();
            errorObj.setErrorServlet("Sending Your Rent Request");
            errorObj.setErrorDetail("Our system is busy, please try again later");
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
