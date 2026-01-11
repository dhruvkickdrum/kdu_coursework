package com.dhruv.library.controller;

import com.dhruv.library.dto.CreateBookRequest;
import com.dhruv.library.model.Book;
import com.dhruv.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    @Operation(summary = "Starts the async book processing",
            description = "Creates a book in PROCESSING status and returns 202 Accepted immediately. " +
                    "A background task marks it AVAILABLE ~3 seconds later.")
    public ResponseEntity<Book> addBook(@Valid @RequestBody CreateBookRequest request) {
        Book created = bookService.createBookAsync(request.getTitle());

        return ResponseEntity
                .accepted()
                .location(URI.create("/books"))
                .body(created);
    }
}
