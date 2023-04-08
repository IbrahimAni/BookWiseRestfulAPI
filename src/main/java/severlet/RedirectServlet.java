package severlet;


import models.Book;
import database.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;

import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@SuppressWarnings("serial")
@WebServlet(name = "RedirectServlet", value = "/")
public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    Gson gson = new Gson();
    	
    	String path = request.getServletPath();

        switch (path){
            case "" :
//            	BookDAO dao2 = new BookDAO();
//				try {
//					request.setAttribute("books", dao2.getAllBooks(0, 100));
//					request.getRequestDispatcher("index.jsp").forward(request, response);
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				request.getRequestDispatcher("index.jsp").forward(request, response);
                break;

            case "/add" :
                Random random = new Random();
                BookDAO dao2 = new BookDAO();
                
				try {					
					int ranNum = random.nextInt(10000) + 1;
					request.setAttribute("books", dao2.getAllBooks(0, 100));
	                request.setAttribute("ranNum", ranNum);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                break;

            case "/update" :
                int id = Integer.parseInt(request.getParameter("id"));
                BookDAO dao = new BookDAO();
                try {
                    Book book = dao.getBook(id);
                    request.setAttribute("books", dao.getAllBooks(0, 100));
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("./html/update-form.jsp").forward(request, response);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "/delete":
            	response.setHeader("Cache-Control", "no-cache");
        		response.setHeader("Pragma", "no-cache");
        		response.setContentType("application/json");
        	    response.setCharacterEncoding("UTF-8");
        	    
                int book_id = Integer.parseInt(request.getParameter("id"));
                BookDAO dao1 = new BookDAO();
                try {               	
                    dao1.deleteBook(book_id);
                    String json = gson.toJson(dao1.getAllBooks(0, 100));
                    response.getWriter().write(json);                                        
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
