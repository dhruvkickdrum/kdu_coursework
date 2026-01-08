package com.dhruv.digitalbookshelf.service;

import com.dhruv.digitalbookshelf.exception.ResourceNotFoundException;
import com.dhruv.digitalbookshelf.model.Book;
import com.dhruv.digitalbookshelf.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final int PAGE_SIZE = 10;
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Book add(Book book) {
        return repo.save(book);
    }

    public Optional<Book> getById(Long id) {
        return repo.findById(id);
    }

    public List<Book> getAll(String author,     int page, String sortDIr) {
        // Step 1: Fetch data

        List<Book> books = (author != null && !author.isBlank())
                ? repo.findByAuthorContainingIgnoreCase(author)
                : repo.findAll();

        // Step 2 : Sort

        Comparator<Book> comparator = Comparator.comparing(Book::getTitle);
        if("desc".equalsIgnoreCase(sortDIr)) {
            comparator = comparator.reversed();
        }

        books = books.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        // Step 3 : Pagination
        int fromIndex = (page - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, books.size());

        if(fromIndex >= books.size() || fromIndex < 0) {
            return List.of(); // empty page
        }

        return books.subList(fromIndex, toIndex);
    }

    public Book update (Long id, Book updated) {
        return repo.findById(id)
                .map(b -> {
                    b.setTitle(updated.getTitle());
                    b.setAuthor(updated.getAuthor());
                    return b;
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id " + id));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}