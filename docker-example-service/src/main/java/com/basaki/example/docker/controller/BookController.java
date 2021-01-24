package com.basaki.example.docker.controller;

import com.basaki.example.docker.model.Book;
import com.basaki.example.docker.model.BookRequest;
import com.basaki.example.docker.model.Genre;
import com.basaki.example.docker.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * {@code BookController} is the spring REST controller for book API. Exposes
 * all CRUD operations on book.
 * <p/>
 *
 * @author Indra Basak
 * @since 3/13/17
 */
@RestController
@Slf4j
@Api(value = "Book API",
        produces = "application/json", tags = {"API"})
public class BookController {

    public static final String BOOK_URL = "/books";

    public static final String BOOK_BY_ID_URL = BOOK_URL + "/{id}";

    @Autowired
    private BookService service;

    @ApiOperation(
            value = "Creates a book.",
            notes = "Requires a book title, genre, publisher, star, and author.",
            response = Book.class)
    @ApiResponses({
            @ApiResponse(code = 201, response = Override.class,
                    message = "Override override created successfully")})
    @PostMapping(value = BOOK_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody BookRequest request) {
        return service.create(request);
    }

    @ApiOperation(
            value = "Retrieves a book by ID.",
            notes = "Requires a book identifier",
            response = Book.class)
    @GetMapping(value = BOOK_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Book getById(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @ApiOperation(
            value = "Retrieves all books associated with a title, genre, publisher or combination of them.",
            notes = "In absence of any parameter, it will return all authors",
            response = Book.class, responseContainer = "List")
    @GetMapping(value = BOOK_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Book> get(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "genre", required = false) Genre genre,
            @RequestParam(value = "publisher", required = false) String publisher) {
        return service.get(title, genre, publisher);
    }

    @ApiOperation(value = "Deletes a book by ID.")
    @DeleteMapping(value = BOOK_BY_ID_URL)
    @ResponseBody
    public void deleteById(@PathVariable("id") UUID id) {
        service.delete(id);
    }

    @ApiOperation(value = "Deletes all books.")
    @DeleteMapping(value = BOOK_URL)
    @ResponseBody
    public void deleteAll() {
        service.deleteAll();
    }
}
