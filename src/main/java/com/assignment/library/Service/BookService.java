package com.assignment.library.Service;

import com.assignment.library.Entity.Book;
import com.assignment.library.Entity.Category;
import com.assignment.library.Repository.BookRepository;
import com.assignment.library.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    //dependency injection using constructor
    private CategoryRepository categoryRepository;

    public BookService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Autowired
    private BookRepository bookRepository;

    public boolean findCategory( Book book){
        if(categoryRepository.existsById(book.getCategory().getId()))
            return true;
        else
            return false;
    }

    public Optional<Book> findBook(Long bookId){
        return bookRepository.findById(bookId);
    }

    public List<Category> getCategory(){ return categoryRepository.findAll(); }

    //create a response message
    public Map createMessage(String message){ return Collections.singletonMap("message",message); }
}
