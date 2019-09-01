package com.assignment.library.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_category")
@JsonIgnoreProperties(value = "books")
@EntityListeners(AuditingEntityListener.class)
public class Category extends AuditModel{

    @NotNull
    @Column(name="NAME")
    private String name;

    @OneToMany(targetEntity=Book.class, mappedBy="category",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Category(){}
}
