package com.dhruv.digitalbookshelf.controller;


import com.dhruv.digitalbookshelf.exception.ResourceNotFoundException;
import com.dhruv.digitalbookshelf.model.Book;
import com.dhruv.digitalbookshelf.service.BookService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // Post Method ( Add a new Book )
    @PostMapping
    public ResponseEntity<Book> add(@Valid @RequestBody Book book) {
        Book saved = service.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Get All Books ( Get Method )
//    @GetMapping
//    public ResponseEntity<List<Book>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        List<Book> books = service.getAll(author, page, sort);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Book>> getBookById(@PathVariable Long id) {

        Book book = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        EntityModel<Book> model = EntityModel.of(book);

        // self link: /books/{id}
        model.add(linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel());

        // all-books link: /books (you can also pass null/default params)
        model.add(linkTo(methodOn(BookController.class).getAll(null, 1, "asc")).withRel("all-books"));

        return ResponseEntity.ok(model);
    }


    // Put  ( Update a Book )
    @PutMapping("/{id}")
    public ResponseEntity<Book> update (
            @PathVariable Long id,
            @Valid @RequestBody Book book) {
        Book updated = service.update(id, book);
        return ResponseEntity.ok(updated);
    }

    // Delete ( Remove a book )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}