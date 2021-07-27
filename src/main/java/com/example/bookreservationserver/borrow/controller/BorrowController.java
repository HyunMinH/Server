package com.example.bookreservationserver.borrow.controller;

import com.example.bookreservationserver.borrow.dto.BorrowRequest;
import com.example.bookreservationserver.borrow.dto.BorrowBookResponse;
import com.example.bookreservationserver.borrow.dto.ReturnResponse;
import com.example.bookreservationserver.borrow.service.BorrowService;
import com.example.bookreservationserver.borrow.service.ReturnService;
import com.example.bookreservationserver.borrow.service.BorrowSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowSearchService borrowSearchService;
    private final ReturnService returnService;
    private final BorrowService borrowService;

    @PostMapping(value = "/api/borrow", produces = "application/json; charset=utf8")
    public BorrowBookResponse borrow(@RequestBody @Valid BorrowRequest borrowRequest){
        return borrowService.borrowBook(borrowRequest);
    }

    @PostMapping(value = "/api/borrow/{bookId}/return" , produces = "application/json; charset=utf8")
    public ReturnResponse returnBook(@PathVariable("bookId") Long bookId){
        returnService.returnBook(bookId);
        return new ReturnResponse("반납이 완료되었습니다.");
    }

    // all user's borrows

    @GetMapping("/api/borrow/borrowing")
    public List<BorrowBookResponse> getAllBorrowing(){
        return borrowSearchService.getAllBorrowing();
    }

    @GetMapping("/api/borrow/expired")
    public List<BorrowBookResponse> getAllExpired(){
        return borrowSearchService.getAllExpired();
    }

    // one user borrows
    @GetMapping("/api/borrow/all/{userId}")
    public List<BorrowBookResponse> getMyBorrow(@PathVariable("userId") Long userId){
        return borrowSearchService.getMyReservations(userId);
    }

    @GetMapping("/api/borrow/borrowing/{userId}")
    public List<BorrowBookResponse> getMyBorrwing(@PathVariable("userId") Long userId){
        return borrowSearchService.getMyBorrowings(userId);
    }

    @GetMapping("/api/borrow/expired/{userId}")
    public List<BorrowBookResponse> seaarchMyExpired(@PathVariable("userId") Long userId){
        return borrowSearchService.getMyExpired(userId);
    }
}
