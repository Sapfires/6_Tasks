package com.epam.intro.subtask1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UserRepository userRepository = new UserRepository();
        SessionCache sessionCache = new SessionCache();
        LoginService loginService = new LoginService(userRepository, sessionCache);
        NotificationService notificationService = new NotificationService();
        BookRepository bookRepository = new BookRepository();
        BookService bookService = new BookService(bookRepository, notificationService, sessionCache);
        ConsoleUI consoleUI = new ConsoleUI(loginService, sessionCache, bookService);
        consoleUI.showLoginView();
        consoleUI.mainMenu();
    }
}
