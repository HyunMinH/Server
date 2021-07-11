package com.example.bookreservationserver.book.controller;

import com.example.bookreservationserver.book.domain.aggregate.Book;
import com.example.bookreservationserver.book.dto.AddRequest;
import com.example.bookreservationserver.book.dto.BookResponse;
import com.example.bookreservationserver.book.service.AddService;
import com.example.bookreservationserver.book.service.DeleteService;
import com.example.bookreservationserver.book.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {
    private AddService addService;
    private DeleteService deleteService;
    private InfoService infoService;


    @PostMapping(value = "/api/book/add", produces = "application/json; charset=utf8")
    public void addBook(@RequestBody @Valid AddRequest addRequest) {addService.addBook(addRequest);}

    @DeleteMapping("/api/book/delete/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        deleteService.deleteBook(bookId);
    }

    @GetMapping("/api/book/info")
    public List<BookResponse> infoBooks(){
        return  infoService.infoBooks();
    }

    @GetMapping("/api/book/info/id/{bookId}")
    public List<Book> infoBook(@PathVariable("bookId") Long bookId){
        return infoService.infoBook(bookId);
    }


    @Autowired
    public BookController(AddService addService, DeleteService deleteService, InfoService infoService){
        this.addService = addService;
        this.deleteService = deleteService;
        this.infoService = infoService;
    }
}
