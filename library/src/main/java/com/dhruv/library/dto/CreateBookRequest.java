package com.dhruv.library.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateBookRequest {
    @NotBlank(message = "Title is required")
    private String title;


    public CreateBookRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
