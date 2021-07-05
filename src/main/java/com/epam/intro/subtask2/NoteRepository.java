package com.epam.intro.subtask2;

import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteRepository {
    private static final String SEPARATOR = " : ";

    private List<Note> notes = new ArrayList<>();
    private File file = new File("src\\main\\resources\\notes.txt");

    public NoteRepository() {
        try {
            if (!file.exists()) {
                Files.createFile(file.toPath());
            }
            List<String> rawBooks = FileUtils.readLines(file, "UTF-8");
            notes = rawBooks.stream().map(this::buildNote).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Note> getAll() {
        return notes;
    }
    public boolean add(Note note) {
        return notes.add(note);
    }

    private Note buildNote(String s) {
        String[] rawNote =  s.split(SEPARATOR);
        return Note.builder()
                .title(rawNote[0])
                .createdOn(buildZonedDataTime(rawNote[1]))
                .email(rawNote[2])
                .message(rawNote[3])
                .build();
    }
    private ZonedDateTime buildZonedDataTime(String value) {
        return ZonedDateTime.parse(value);
    }

     void shutdown() {
        String result = notes.stream().map(this::serializeNote).collect(Collectors.joining("\r"));
         try {
             FileUtils.write(file, result, "UTF-8");
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    private String serializeNote(Note note) {
        return note.getTitle() + SEPARATOR + note.getCreatedOn() + SEPARATOR + note.getEmail() + SEPARATOR + note.getMessage();
    }
}
