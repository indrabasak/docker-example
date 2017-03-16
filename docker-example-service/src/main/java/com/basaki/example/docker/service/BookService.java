package com.basaki.example.docker.service;

import com.basaki.example.docker.error.InvalidSearchException;
import com.basaki.example.docker.model.Book;
import com.basaki.example.docker.model.BookRequest;
import com.basaki.example.docker.model.Genre;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * {@code BookService} service provides data access service for {@code Book}.
 * <p/>
 *
 * @author Indra Basak
 * @sice 3/13/17
 */
@Service
@Slf4j
public class BookService {

    private Mapper mapper;

    private Map<UUID, Book> bookMap;

    @Autowired
    public BookService(Mapper mapper) {
        this.mapper = mapper;
        bookMap = new HashMap<>();
    }

    public Book create(BookRequest request) {
        Assert.notNull(request.getTitle(), "Title should not be null.");
        Assert.notNull(request.getGenre(), "Genre should not be null.");
        Assert.notNull(request.getPublisher(), "Publisher should not be null.");
        Assert.notNull(request.getAuthor(), "Author should not be null.");
        Assert.state((request.getStar() > 0 && request.getStar() <= 5),
                "Star should be between 1 and 5");

        Book book = mapper.map(request, Book.class);
        book.setId(UUID.randomUUID());
        bookMap.put(book.getId(), book);

        return book;
    }

    public Book getById(UUID id) {
        Book book = bookMap.get(id);
        if (book == null) {
            throw new InvalidSearchException(
                    "Book with ID " + id + " not found!");
        }

        return book;
    }

    public List<Book> get(String title, Genre genre, String publisher) {

        if (title == null && genre == null && publisher == null) {
            return bookMap.values().stream().collect(Collectors.toList());
        }

        List<Book> books = bookMap.values().stream().filter(b -> {
            if (title != null && title.equalsIgnoreCase(b.getTitle())) {
                return true;
            } else {
                return true;
            }
        }).filter(b -> {
            if (genre != null && genre.equals(b.getGenre())) {
                return true;
            } else {
                return true;
            }
        }).filter(b -> {
            if (publisher != null && publisher.equalsIgnoreCase(
                    b.getPublisher())) {
                return true;
            } else {
                return true;
            }
        }).collect(Collectors.toList());

        if (books == null || books.size() == 0) {
            throw new InvalidSearchException("No books found!");
        }

        return books;
    }

    public void delete(UUID id) {
        Book book = bookMap.remove(id);

        if (book == null) {
            throw new InvalidSearchException("No book found with ID " + id);
        }
    }

    public void deleteAll() {
        bookMap.clear();
    }
}
