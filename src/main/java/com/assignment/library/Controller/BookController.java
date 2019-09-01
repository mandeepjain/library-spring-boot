package com.assignment.library.Controller;

import com.assignment.library.Entity.Book;
import com.assignment.library.Exception.BadRequestException;
import com.assignment.library.Exception.InternalServerErrorException;
import com.assignment.library.Exception.NotFoundException;
import com.assignment.library.Repository.BookRepository;
import com.assignment.library.Service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("books")
public class BookController{

    //dependency injection
    private BookService bookService;
    public BookController(BookService bookService){
        this.bookService=bookService;
    }

    @Autowired
    BookRepository bookRepository;

    @GetMapping(value = "/get-all-books")
    public ResponseEntity<?> getAllBooks(){

        List<Book> books=bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @PostMapping(value="/add-book")
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book){
        if(bookService.findCategory(book))
        {
            try {
            bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createMessage("Entity created successfully"));
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
            }
        }
        else{
            throw new BadRequestException("Category id :"+book.getCategory().getId()+ " does not exist ");
        }
    }

    @PutMapping(value = "/edit/{bookId}")
    public ResponseEntity<?> editBook (@Valid @RequestBody Book book, @PathVariable long bookId){
        Optional<Book> existingBookOpt= bookService.findBook(bookId);

        if(!existingBookOpt.isPresent()){
            throw new NotFoundException("Book with specified id:"+bookId +" doesn't exist");
        }
        else{
            if(bookService.findCategory(book)) {
                book.setId(bookId);
                Book existingBook = existingBookOpt.get();
                BeanUtils.copyProperties(book, existingBook);
                bookRepository.save(existingBook);
                return ResponseEntity.status(HttpStatus.OK).body(bookService.createMessage("Entity modified successfully"));
            }
            throw new BadRequestException("Category id :"+book.getCategory().getId()+ " does not exist ");
        }

    }
    @DeleteMapping(value = "/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId){
        Optional<Book> existingBookOpt= bookService.findBook(bookId);
        if(existingBookOpt.isPresent()){
            try {
                bookRepository.deleteById(bookId);
                return ResponseEntity.status(HttpStatus.OK).body(bookService.createMessage("Book Deleted Successfully"));
            }catch (Exception e){
                throw new InternalServerErrorException(e.getMessage());
            }
        }
        else
            throw new NotFoundException("Book with specified id:"+bookId +" doesn't exist");

    }
    @GetMapping(value = "/get-all-category")
    public ResponseEntity<?> getAllCategory(){
        return ResponseEntity.ok(bookService.getCategory());
    }

}
