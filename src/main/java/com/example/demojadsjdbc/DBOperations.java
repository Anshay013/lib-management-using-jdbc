package com.example.demojadsjdbc;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOperations{

    private static Connection connection;

    public static void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root","anshay1308@");

    }




    public static void createTable(String name) throws SQLException {
        getConnection();
        Statement statement = connection.createStatement();

        boolean result = statement.execute("CREATE TABLE " + name + " (id INT PRIMARY KEY AUTO_INCREMENT,book_name VARCHAR(30), " +
                "author_name VARCHAR(30), cost INT)"); // manually mapping/putting.

        if(result)
            System.out.println("Table: "+ name + "successfully created");
        else
            System.out.println("Table: "+ name + "not created");
        // statement.executeQuery(....); --> it will return us the no of rows/column affected i.e the result set instead of boolean.
        connection.close();
    }



     public  static void createBook(Book book) throws SQLException{
        getConnection();

         PreparedStatement statement = connection.prepareStatement("INSERT INTO my_books(book_name,author_name,cost)"+
                         "VALUES(?,?,?)");
         statement.setString(1,book.getBook_name());   
         statement.setString(2,book.getAuthor_name()); 
         statement.setInt(3,book.getCost());
         
         int rows = statement.executeUpdate();
              
         if(rows >= 1) System.out.println("-->" + "rows returned : " + rows);
         else System.out.println("Unable to insert into the table");

        connection.close();
     }


    public static List<Book> getBooks() throws SQLException { // returns all the books in the DB. (not based on some id).
        getConnection();

        Statement statement = connection.createStatement();
        // we have seen statement.execute, .executeUpdate now we see another type.
        ResultSet resultSet = statement.executeQuery("select * from my_books");


        List<Book> book_list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String book_name = resultSet.getString(2);
            String author_name = resultSet.getString(3);  // getInt,getString are all inbuilt function under resultSet interface.
            int cost = resultSet.getInt(4);

            Book book = new Book(id,book_name,author_name,cost);
            book_list.add(book);
        }

        connection.close();
        return book_list;
    }
    // therefore its it better to use framework(JPA Hibernate) which handles all the things automatically rather manually mapping it.
    public static Book getBookById(int id) throws  SQLException{
        getConnection();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from my_books where id = " + id);
        Book book = null;
        while(resultSet.next()){
            int book_id = resultSet.getInt(1);
            String book_name = resultSet.getString(2);
            String author_name = resultSet.getString(3);
            int cost = resultSet.getInt(4);
             book = new Book(book_id,book_name,author_name,cost);
        }
         connection.close();
        return book;
    }


}

