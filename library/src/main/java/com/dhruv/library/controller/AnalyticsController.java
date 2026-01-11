package com.dhruv.library.controller;

import com.dhruv.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final BookService bookService;

    public AnalyticsController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/audit")
    public ResponseEntity<Map<String, Long>> audit() {
        return ResponseEntity.ok(bookService.auditByStatus());
    }
}
