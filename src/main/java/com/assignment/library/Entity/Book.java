package com.assignment.library.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
public class Book extends AuditModel{


    @NotNull
    @Column(name="TITLE")
    private String title;

    @NotNull
    @Column(name="AUTHOR_NAME")
    private String author;

    @Size(min=3,message="Language should have atleast 3 characters")
    @Column(name="LANGUAGE")
    private String language;

    @Positive
    @Column(name="AMOUNT")
    private float price;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "id")
    @JsonProperty(value = "category")
    private Category category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Book() {
    }
}
