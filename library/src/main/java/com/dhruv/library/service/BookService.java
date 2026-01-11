package com.dhruv.library.service;

import com.dhruv.library.model.Book;
import com.dhruv.library.model.BookStatus;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final ConcurrentMap<String, Book> books = new ConcurrentHashMap<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2)
    );

    public List<Book> getAllBooks() {
        return books.values()
                .stream()
                .sorted(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public Book createBookAsync(String title) {
        String id = UUID.randomUUID().toString();
        Book book = new Book(id, title, BookStatus.PROCESSING);

        books.put(id, book);

        executor.submit(() -> {
            try {
                Thread.sleep(3000);
                books.computeIfPresent(id, (k, existing) -> {
                    existing.setStatus(BookStatus.AVAILABLE);
                    return existing;
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return book;
    }

    public Map<String, Long> auditByStatus() {
        return books.values()
                .stream()
                .collect(Collectors.groupingBy(
                        b -> b.getStatus().name(),
                        Collectors.counting()
                ));
    }

    @PreDestroy
    public void shutdownExecutor() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}
