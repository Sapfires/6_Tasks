package com.epam.intro.subtask1;

import lombok.AllArgsConstructor;

import java.io.IOException;

import static com.epam.intro.subtask1.Operation.*;

@AllArgsConstructor
public class BookService {
    private BookRepository bookRepository;
    private NotificationService notificationService;
    private SessionCache sessionCache;
    public Page<Book> getAll(Integer pageNumber, Integer booksPerPage) {
        sessionCache.validatePermission(GET_ALL);
        Page<Book> bookPage = bookRepository.getAll(pageNumber, booksPerPage);
        return bookPage;
    }
    public Page<Book> getBookByFilter(SearchFilter searchFilter) {
        sessionCache.validatePermission(GET_BOOKS_BY_FILTER);
        Page<Book> bookPage = bookRepository.getBooksByFilter(searchFilter);
        return bookPage;
    }
    public Book addDraft(Book book) throws IOException {
        sessionCache.validatePermission(ADD_DRAFT);
        book.setDraft(true);
        Book result = bookRepository.addDraft(book);
        return result;
    }
    public Book approveBook(String bookCode) throws IOException {
        sessionCache.validatePermission(APPROVE_BOOK);
        Book book = bookRepository.getBookById(Integer.valueOf(bookCode));
        book.setDraft(false);
        Book result = bookRepository.activateBook(book);
        return result;
    }

    public Integer getPageCount(int pageSize) {
        sessionCache.validatePermission(GET_ALL);
        Integer bookCount = bookRepository.getBooksCount();
        return bookCount % pageSize == 0 ? bookCount / pageSize : 1 + bookCount / pageSize;
    }
}
