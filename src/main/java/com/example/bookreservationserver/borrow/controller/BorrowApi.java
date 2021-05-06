package com.example.bookreservationserver.borrow.controller;

import com.example.bookreservationserver.borrow.domain.aggregate.Borrow;
import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.borrow.service.BorrowService;
import com.example.bookreservationserver.borrow.service.ReturnService;
import com.example.bookreservationserver.borrow.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BorrowApi {
    private SearchService searchService;
    private ReturnService returnService;
    private BorrowService borrowService;

    @RequestMapping(value = "/api/borrow/create", produces = "application/json; charset=utf8")
    public void borrow(@RequestBody @Valid BorrowRequest borrowRequest){
        borrowService.borrowBook(borrowRequest);
    }

    @RequestMapping("/api/borrow/return/{bookId}")
    public void returnBook(@PathVariable("bookId") Long bookId){
        returnService.returnBook(bookId);
    }
    
    @RequestMapping("/api/borrow/search/{userId}")
    public List<Borrow> searchMyBorrow(@PathVariable("userId") Long userId){
        return searchService.getMyReservations(userId);
    }

    @Autowired
    public BorrowApi(SearchService searchService, ReturnService returnService, BorrowService borrowService) {
        this.searchService = searchService;
        this.returnService = returnService;
        this.borrowService = borrowService;
    }
}
