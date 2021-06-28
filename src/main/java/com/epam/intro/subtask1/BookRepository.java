package com.epam.intro.subtask1;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class BookRepository {
    private File file = new File("src\\main\\resources\\books.txt");
    private List<Book> books = new ArrayList<>();
    private Lock lock = new ReentrantLock();
    public BookRepository() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        try {
            lock.lock();
            if (!file.exists()) {
                Files.createFile(file.toPath());
            }
            List<String> rawBooks = FileUtils.readLines(file, "UTF-8");
            books = rawBooks.stream().map(this::buildBook).collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    private Book buildBook(String s) {

        String[] rawBook =  s.split(" : ");
        return Book.builder()
                .name(rawBook[0])
                .author(rawBook[1])
                .description(rawBook[2])
                .code(Integer.valueOf(rawBook[3]))
                .isDraft(Boolean.getBoolean(rawBook[4]))
                .build();
    }

    public Page<Book> getAll(Integer pageNumber, Integer booksPerPage) {
        List<Book> bookList = books.stream()
                .filter(it -> !it.isDraft())
                .skip((long) pageNumber * booksPerPage)
                .limit(booksPerPage)
                .collect(Collectors.toList());
        long pageCount = books.stream().filter(it -> !it.isDraft()).count();
        return new Page<>(bookList, pageNumber, (int) pageCount, booksPerPage);
    }
    public Page<Book> getAllDrafts(Integer pageNumber, Integer booksPerPage) {
        List<Book> bookList = books.stream()
                .filter(Book::isDraft)
                .skip((long) pageNumber * booksPerPage)
                .limit(booksPerPage)
                .collect(Collectors.toList());
        long pageCount = books.stream().filter(Book::isDraft).count();
        return new Page<>(bookList, pageNumber, (int) pageCount, booksPerPage);
    }
    public Page<Book> getBooksByFilter(SearchFilter searchFilter) {
        List<Book> bookList = books.stream()
                .filter(it -> !it.isDraft())
                .filter(it -> equalsTo(it.getAuthor(), searchFilter.getAuthor()))
                .filter(it -> equalsTo(it.getName(), searchFilter.getName()))
                .filter(it -> equalsTo(it.getDescription(), searchFilter.getDescription()))
                .skip((long) searchFilter.getPageNumber() * searchFilter.getBooksPerPage())
                .limit(searchFilter.getBooksPerPage())
                .collect(Collectors.toList());
        long pageCount = books.stream().filter(it -> !it.isDraft()).count();
        return new Page<>(bookList, searchFilter.getPageNumber(), (int) pageCount, searchFilter.getBooksPerPage());
    }

    private boolean equalsTo(String expected, String actual) {
        return actual == null || (actual.length() == 0 || actual.equals(expected));
    }
    public Book addDraft(Book book) throws IOException {
        int code = books.stream().mapToInt(Book::getCode).max().orElse(0);
        book.setCode(code + 1);
        books.add(book);
        writeToFile();
        return book;
    }
    public Book activateBook(Book book) throws IOException {
        writeToFile();
        return book;
    }
    private void writeToFile() throws IOException {
        try {
            lock.lock();
            String result = books.stream().map(this::serializeBook).collect(Collectors.joining());
            FileUtils.write(file, result, "UTF-8");
        } finally {
            lock.unlock();
        }
    }

    private String serializeBook(Book it) {
        return it.getName() + " : " + it.getAuthor() + " : " + it.getDescription() + " : " + it.getCode() + " : " + it.isDraft() + "\n";
    }

    public Integer getBooksCount() {
        return (int)books.stream().filter(it -> !it.isDraft()).count();
    }

    public Book getBookById(Integer bookCode) {
        return books.stream().filter(it -> it.getCode().equals(bookCode)).findAny().get();
    }
}
