package com.dhruv.digitalbookshelf.repository;

import com.dhruv.digitalbookshelf.model.Book;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final Map<Long , Book> store = new HashMap<>();
    private  final AtomicLong idGenerator = new AtomicLong();

    public Book save(Book book) {
        Long id = idGenerator.incrementAndGet();
        book.setId(id);
        store.put(id, book);
        return book;
    }

    public List<Book> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Book> findById (Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public boolean existById(Long id) {
        return store.containsKey(id);
    }

    public List<Book> findByAuthorContainingIgnoreCase(String author) {
        if (author == null || author.isBlank()) {
            return findAll();
        }

        String search = author.toLowerCase();

        return store.values()
                .stream()
                .filter(book ->
                        book.getAuthor() != null &&
                                book.getAuthor().toLowerCase().contains(search))
                .collect(Collectors.toList());
    }
}
