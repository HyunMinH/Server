package com.example.bookreservationserver.borrow.controller;

import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.borrow.dto.BorrowResponse;
import com.example.bookreservationserver.borrow.service.BorrowService;
import com.example.bookreservationserver.borrow.service.ReturnService;
import com.example.bookreservationserver.borrow.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BorrowApi {
    private final SearchService searchService;
    private final ReturnService returnService;
    private final BorrowService borrowService;

    @PostMapping(value = "/api/borrow/create", produces = "application/json; charset=utf8")
    public BorrowResponse borrow(@RequestBody @Valid BorrowRequest borrowRequest){
        return borrowService.borrowBook(borrowRequest);
    }

    @PostMapping("/api/borrow/{bookId}/return")
    public String returnBook(@PathVariable("bookId") Long bookId){
        returnService.returnBook(bookId);
        return "반납이 완료되었습니다.";
    }

    // all user's borrows

    @GetMapping("/api/borrow/borrowing")
    public List<BorrowResponse> getAllBorrowing(){
        return searchService.getAllBorrowing();
    }

    @GetMapping("/api/borrow/expired")
    public List<BorrowResponse> getAllExpired(){
        return searchService.getAllExpired();
    }

    // one user borrows
    @GetMapping("/api/borrow/all/{userId}")
    public List<BorrowResponse> getMyBorrow(@PathVariable("userId") Long userId){
        return searchService.getMyReservations(userId);
    }

    @GetMapping("/api/borrow/borrowing/{userId}")
    public List<BorrowResponse> getMyBorrwing(@PathVariable("userId") Long userId){
        return searchService.getMyBorrowingReservations(userId);
    }

    @GetMapping("/api/borrow/expired/{userId}")
    public List<BorrowResponse> seaarchMyExpired(@PathVariable("userId") Long userId){
        return searchService.getMyExpired(userId);
    }
}
