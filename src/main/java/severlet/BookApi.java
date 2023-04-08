package severlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.BookDAO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import models.Book;
import models.BookListWrapper;
import models.SuccessMessage;

/**
 * Servlet implementation class BookApi
 */
@WebServlet("/bookpi")
public class BookApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BookDAO dao = new BookDAO();
        String title = request.getParameter("query");
        String format = request.getParameter("format");
        String id = request.getParameter("id");

        try {
            if (id != null) {
                int bookId = Integer.parseInt(id);
                Book book = dao.getBook(bookId);

                if ("xml".equalsIgnoreCase(format)) {
                    respondWithXmlSingle(response, book);
                } else if ("text".equalsIgnoreCase(format)) {
                    respondWithRawText(response, book.toString());
                } else {
                    respondWithJson(response, gson.toJson(book));
                }  
            } else {
                ArrayList<Book> books = (title != null)
                        ? dao.getAllBooksByTitle(title)
                        : dao.getAllBooks(0, 100);

                if ("xml".equalsIgnoreCase(format)) {
                    respondWithXml(response, books);
                } else if ("text".equalsIgnoreCase(format)) {
                    respondWithRawText(response, books.toString());
                } else {
                    respondWithJson(response, gson.toJson(books));
                }
            }
        } catch (SQLException | JAXBException e) {
            throw new ServletException("Error processing request", e);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BookDAO dao = new BookDAO();
        String format = request.getParameter("format");

        try {
            String requestBody = readRequestBody(request);
            Book newBook = gson.fromJson(requestBody, Book.class);
            dao.insertBook(newBook);

            if ("xml".equalsIgnoreCase(format)) {
            	respondWithXmlSingle(response, newBook);
            } else if ("text".equalsIgnoreCase(format)) {
                respondWithRawText(response, newBook.toString());
            } else {
                respondWithJson(response, gson.toJson(newBook));
            }
        } catch (SQLException | JAXBException e) {
            throw new ServletException("Error processing request", e);
        }
    }

	
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BookDAO dao = new BookDAO();
        String format = request.getParameter("format");

        try {
            String requestBody = readRequestBody(request);
            Book updatedBook = gson.fromJson(requestBody, Book.class);
            dao.updateBook(updatedBook);

            if ("xml".equalsIgnoreCase(format)) {
            	respondWithXmlSingle(response, updatedBook);
            } else if ("text".equalsIgnoreCase(format)) {
                respondWithRawText(response, updatedBook.toString());
            } else {
                respondWithJson(response, gson.toJson(updatedBook));
            }
        } catch (SQLException | JAXBException e) {
            throw new ServletException("Error processing request", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BookDAO dao = new BookDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        String format = request.getParameter("format");        

        try {
            dao.deleteBook(id);
            SuccessMessage successMessage = new SuccessMessage(true);

            if ("xml".equalsIgnoreCase(format)) {
            	respondWithXmlSingle(response, successMessage);
            } else if ("text".equalsIgnoreCase(format)) {
                respondWithRawText(response, "success: true");
            } else {
                respondWithJson(response, gson.toJson(Collections.singletonMap("success", true)));
            }
        } catch (SQLException | JAXBException e) {
            throw new ServletException("Error processing request", e);
        }
    }

    
    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    private void respondWithJson(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
    
    private void respondWithXmlSingle(HttpServletResponse response, Object object) throws IOException, JAXBException {
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);     
        
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        out.print(writer.toString());
        out.flush();
    }

    
    private void respondWithXml(HttpServletResponse response, ArrayList<Book> books) throws IOException, JAXBException {
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JAXBContext jaxbContext = JAXBContext.newInstance(BookListWrapper.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        BookListWrapper bookListWrapper = new BookListWrapper(books);

        StringWriter writer = new StringWriter();
        marshaller.marshal(bookListWrapper, writer);
        out.print(writer.toString());
        out.flush();
    }


    private void respondWithRawText(HttpServletResponse response, String text) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(text);
        out.flush();
    }

}
