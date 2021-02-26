package com.example.demojadsjdbc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;



@RestController
public class BookController {


    @GetMapping("/getBooks")
    public List<com.example.demojadsjdbc.Book> getAllBooks() throws SQLException{
      return DBOperations.getBooks();
    }


    @PostMapping("/createBook")
    @ResponseStatus(HttpStatus.CREATED)
     public void createBook(@RequestBody Book book) throws SQLException{
        DBOperations.createBook(book);
    }

    @GetMapping("/getBookById")
    public Book getBookByID(@RequestParam(value = "id") int id) throws SQLException { // or simply --> (@RequestParam String id)
      return DBOperations.getBookById(id);
    }

    @PostMapping("/createTable")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTable(@RequestParam(value = "table_name")String name) throws SQLException {
        DBOperations.createTable(name);

    }

}