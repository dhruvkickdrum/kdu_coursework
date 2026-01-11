package com.dhruv.library.model;

import java.util.Objects;

public class Book {
    private String id;
    private String title;
    private BookStatus status;


    public Book(String id, String title, BookStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Book book)) return false;
        return Objects.equals(id, book.id);
    }

    @Override
    public  int hashCode() {
        return Objects.hash(id);
    }
}
