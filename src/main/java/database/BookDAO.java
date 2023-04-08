package database;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import models.Book;

public class BookDAO {
    public BookDAO(){}
    
    private Connection getDBConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://database.cvtgg4klvh7c.eu-west-2.rds.amazonaws.com:3306/assessment?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
            conn = DriverManager.getConnection(dbURL, "admin", "adminpassword");
//            String dbURL = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/anifowoi";
//            conn = DriverManager.getConnection(dbURL, "anifowoi", "gerTunge7");            
            System.out.println("Database connected!!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Not connected");
        }
        return conn;
    }

    public ArrayList<Book> getAllBooks(int offset, int pageSize) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM books LIMIT "+ pageSize +" OFFSET "+ offset +";";
        ArrayList<Book> books = new ArrayList<>();

        // Create a SimpleDateFormat object for parsing the input date format
//        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yy");

        // Create a SimpleDateFormat object for formatting the output date format
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));

                // Parse the input date string using the input format
//                Date date = inputFormat.parse(rs.getString("date"));
//
//                // Format the date as a string in the output format
//                String formattedDate = outputFormat.format(date);
//                book.setDate(formattedDate);
                book.setDate(rs.getString("date"));
                book.setGenres(rs.getString("genres"));
                book.setCharacters(rs.getString("characters"));
                book.setSynopsis(rs.getString("synopsis"));
                books.add(book);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return books;
    }

    public Book getBook(int id) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM books WHERE id="+ id +";";
        Book book = new Book();
        
//        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yy");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
//                Date date = inputFormat.parse(rs.getString("date"));
//                String formattedDate = outputFormat.format(date);
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setDate(rs.getString("date"));
                book.setGenres(rs.getString("genres"));
                book.setCharacters(rs.getString("characters"));
                book.setSynopsis(rs.getString("synopsis"));
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return book;
    }

    public Boolean insertBook(Book book) throws SQLException {
        Connection connection = getDBConnection();
        Boolean bookInserted = false;
        PreparedStatement ps = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yy");
        try{
//        	Date date = inputFormat.parse(book.getDate());
//            String formattedDate = outputFormat.format(date);        	
            String query = "insert into books (id, title, author, date, genres, characters, synopsis ) VALUE ('"+ book.getId() +"', '"+ book.getTitle() +"', '"+ book.getAuthor() +"', '"+ book.getDate() +"', '"+ book.getGenres() +"', '"+ book.getCharacters() +"', '"+ book.getSynopsis() + "')";

            ps = connection.prepareStatement(query);
            ps.execute();
            bookInserted = true;
            System.out.println("Book Inserted");
        }catch (Exception e){
            System.out.println(e.toString());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return  bookInserted;
    }

    public Boolean updateBook(Book book) throws SQLException {
        Connection connection = getDBConnection();
        Boolean  bookUpdated = false;
        PreparedStatement ps = null;
        String query = "UPDATE books SET title=?, author=?, date=?, genres=?, characters=?, synopsis=? WHERE id=?";
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yy");

        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
//            Date date = inputFormat.parse(book.getDate());
//            String formattedDate = outputFormat.format(date);
            ps.setString(3, book.getDate());
            ps.setString(4, book.getGenres());
            ps.setString(5, book.getCharacters());
            ps.setString(6, book.getSynopsis());
            ps.setInt(7, book.getId());
            ps.executeUpdate();
            bookUpdated = true;
            System.out.println("Book Updated");
        }catch (Exception e){
            System.out.println(e.toString());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return  bookUpdated;
    }

    public Boolean deleteBook(int id) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement ps = null;
        boolean bookDeleted = false;
        String query = "delete from books where id= '"+ id +"'";

        try{
            ps = connection.prepareStatement(query);
            ps.execute();
            System.out.println("Books Deleted");
            bookDeleted = true;
        }catch (Exception e){
            System.out.println(e.toString());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return bookDeleted;
    }

    public ArrayList<Book> getAllBooksByTitle(String searchName) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE (title LIKE '%" + searchName + "%' OR date LIKE '%" + searchName + "%' OR genres LIKE '%" + searchName + "%')";

        
//        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yy");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
//                Date date = inputFormat.parse(rs.getString("date"));
//                String formattedDate = outputFormat.format(date);
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setDate(rs.getString("date"));
                book.setGenres(rs.getString("genres"));
                book.setCharacters(rs.getString("characters"));
                book.setSynopsis(rs.getString("synopsis"));
                books.add(book);
            }
            System.out.println(books);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return books;
    }

    public int getTotalBooks() throws SQLException {
    	Connection connection = getDBConnection(); 
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	int totalBooks = 0;
    	String query = "SELECT count(*) FROM books";
    	try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs.next()) {
				totalBooks = rs.getInt(1);
			}
		} catch (Exception e){
            System.out.println(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return totalBooks;
    }
}

