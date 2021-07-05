package com.epam.intro.subtask2;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ConsoleUI {
    private NoteService noteService;

    public void getAll() {
        noteService.getTitles().forEach(System.out::println);
    }

    public void search() {
        String title = readVariable("Title");
        String email = readVariable("Email");
        String createdOn = readVariable("Created on");
        String message = readVariable("Message");
        SearchFilter searchFilter = SearchFilter.builder()
                .title(title.length() != 0 ? title : null)
                .email(email.length() != 0 ? email : null)
                .createdOn(createdOn.length() != 0 ? ZonedDateTime.parse(createdOn) : null)
                .message(message.length() != 0 ? message : null)
                .build();
        noteService.getByFilter(searchFilter).forEach(System.out::println);
    }

    public void addNote() {
        String title = readVariable("Title");
        String email = readVariable("Email");
        ZonedDateTime createdOn = ZonedDateTime.now();
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            System.out.println("Email is not correct");
            return;
        }
        String message = readVariable("Message");
        Note note = Note.builder()
                .title(title)
                .email(email)
                .message(message)
                .createdOn(createdOn)
                .build();
        noteService.add(note);
    }

    public void mainMenu() {
        while (true) {
            System.out.println("0 - EXIT");
            System.out.println("1 - GET_ALL");
            System.out.println("2 - SEARCH");
            System.out.println("3 - ADD");
            String command = readVariable("enter menu id: ");
            Integer intCommand = Integer.valueOf(command);
            switch (intCommand) {
                case 0:
                    return;
                case 1: {
                    getAll();
                    break;
                }
                case 2: {
                    search();
                    break;
                }
                case 3: {
                    addNote();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        NoteRepository noteRepository = new NoteRepository();
        Thread saveNotesToFile = new Thread(noteRepository::shutdown);
        Runtime.getRuntime().addShutdownHook(saveNotesToFile);
        NoteService noteService = new NoteService(noteRepository);
        ConsoleUI consoleUI = new ConsoleUI(noteService);
        consoleUI.mainMenu();
    }

    public static String readVariable(String variableName) {
        System.out.println("Please input " + variableName + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
