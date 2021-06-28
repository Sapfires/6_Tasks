package com.epam.intro.subtask1;

import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class ConsoleUI {
    private static final Integer PAGE_SIZE = 3;
    private LoginService loginService;
    private SessionCache sessionCache;
    private BookService bookService;
    public void showLoginView () {
        String login = readVariable("Login");
        String password = readVariable("Password");
        String md5Hex = DigestUtils.md5Hex(password);
        System.out.println(md5Hex);
        loginService.login(login, md5Hex);
    }
    public void suggestBook() throws IOException {
        String name = readVariable("Name");
        String author = readVariable("Author");
        String description = readVariable("Description");
        Book book = Book.builder()
                .isDraft(true)
                .author(author)
                .name(name)
                .description(description)
                .build();
        bookService.addDraft(book);
    }
    public void mainMenu() throws IOException {
        while (true) {
            List<Operation> operations = sessionCache.getAvailableOperations();
            operations.stream().map(it -> it.getCode() + " : " + it.name()).forEach(System.out::println);
            Integer operationNumber = Integer.valueOf(readVariable("Select operation number"));
            switch (operationNumber) {
                case 0: {
                    getAll();
                    break;
                }
                case 1: {
                    getBooksByFilter();
                    break;
                }
                case 2: {
                    addDraft();
                    break;
                }
                case 3: {
                    approveBook();
                    break;
                }
                case 4: {
                    return;
                }
            }
        }
    }
    public void getAll() {
        while (true) {
            Integer pageCount = bookService.getPageCount(PAGE_SIZE);
            Integer pageNumber = Integer.valueOf(readVariable("Page number"));
            Page<Book> books = bookService.getAll(pageNumber, PAGE_SIZE);
            if (subMenu(books)) return;
        }
    }

    private boolean subMenu(Page<Book> books) {
        System.out.println(books);
        System.out.println("0 : SHOW ANOTHER PAGE");
        System.out.println("1 : MAIN MENU");
        String nextAction = readVariable("Operation");
        if (nextAction.equals("1")) {
            return true;
        }
        return false;
    }

    public void getBooksByFilter() {
        while (true) {
            String author = readVariable("Author");
            String name = readVariable("Name");
            String description = readVariable("Description");
            SearchFilter searchFilter = SearchFilter.builder()
                    .author(author)
                    .name(name)
                    .description(description)
                    .booksPerPage(PAGE_SIZE)
                    .pageNumber(0)
                    .build();
            Page<Book> result = bookService.getBookByFilter(searchFilter);
            if (subMenu(result)) return;
        }
    }
    public void addDraft() throws IOException {
        while (true) {
            String author = readVariable("Author");
            String name = readVariable("Name");
            String description = readVariable("Description");
            Book draft = Book.builder().author(author).name(name).description(description).build();
            bookService.addDraft(draft);
            System.out.println("Book request has been saved");
            System.out.println("0 : ADD ANOTHER SUGGESTION");
            System.out.println("1 : MAIN MENU");
            String nextAction = readVariable("Operation");
            if (nextAction.equals("1")) {
                return;
            }
        }
    }
    public void approveBook() throws IOException {
        while (true) {
            String codeForApproving = readVariable("Book code for approving");
            bookService.approveBook(codeForApproving);
            System.out.println("Book request has been approved");
            System.out.println("0 : APPROVE ANOTHER SUGGESTION");
            System.out.println("1 : MAIN MENU");
            String nextAction = readVariable("Operation");
            if (nextAction.equals("1")) {
                return;
            }
        }
    }
    public static String readVariable(String variableName) {
        System.out.println("Please input " + variableName + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
