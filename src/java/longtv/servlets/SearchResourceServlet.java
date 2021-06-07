/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtv.daos.RequestDAO;
import longtv.daos.ResourceDAO;
import longtv.dtos.AccountDTO;
import longtv.dtos.AccountErrorObject;
import longtv.dtos.CategoryDTO;
import longtv.dtos.ResourceDTO;
import longtv.dtos.SearchDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SearchResourceServlet", urlPatterns = {"/SearchResourceServlet"})
public class SearchResourceServlet extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "search.jsp";
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
            HttpSession session = request.getSession(false);
            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNTDETAIL");
            if (account != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String page = request.getParameter("txtPage");
                String searchName = request.getParameter("txtSearchItem");
                String rentTime = request.getParameter("txtRentTime");
                String categoryID = request.getParameter("categoryParam");
                if (page == null || page.equals("")) {
                    page = "1";
                }
                if (rentTime == null || rentTime.equals("")) {
                    Date date = new Date();
                    rentTime = sdf.format(date);
                }
                if (categoryID == null || categoryID.equals("")) {
                    categoryID = "0";
                }
                if (searchName == null) {
                    searchName = "";
                }
                int currentPage = Integer.parseInt(page);
                int ID = Integer.parseInt(categoryID);
                ResourceDAO dao = new ResourceDAO();
                int totalRows = dao.loadAllRows(ID, searchName, rentTime);
                int totalPage = (int) (Math.ceil(totalRows / 20) + 1);
                if (currentPage > totalPage) {
                    currentPage = totalPage;
                } else if (currentPage < 1) {
                    currentPage = 1;
                }
                List<CategoryDTO> categoryList = dao.loadAllCategory();
                List<ResourceDTO> resourceList = dao.loadAllResource(ID, searchName, rentTime, currentPage);
                SearchDTO dto = new SearchDTO(categoryList, currentPage, ID, rentTime, totalPage);
                if (categoryList.size() > 0) {
                    request.setAttribute("SEARCH_DETAIL", dto);
                    if ((resourceList.size() > 0)) {
                        RequestDAO reDAO = new RequestDAO();
                        for(int i = 0; i < resourceList.size();i++) {
                            int totalQuantity = resourceList.get(i).getItemQuantity();
                            int resourceID = resourceList.get(i).getItemID();
                            int usingQuantity = reDAO.getResourceCurrentlyOnUse(resourceID, rentTime);
                            int leftQuantity = totalQuantity - usingQuantity;
                            resourceList.get(i).setItemQuantity(leftQuantity);
                        }
                        request.setAttribute("RESOURCE_ITEM", resourceList);
                    }
                }
                url = SUCCESS;
            } else {
                url = NOTLOGIN;
                request.setAttribute("LOGINSTATUS", "You must login to use or rent our resource.");
            }

        } catch (Exception e) {
            log("Error At SearchResourceServlet: " + e.getMessage());
            AccountErrorObject errorObj = new AccountErrorObject();
            errorObj.setErrorServlet("Getting Resource Detail");
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
