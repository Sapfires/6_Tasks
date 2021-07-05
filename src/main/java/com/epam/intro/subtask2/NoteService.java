package com.epam.intro.subtask2;

import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class NoteService {
    private NoteRepository noteRepository;

    public List<String> getTitles() {
        return noteRepository.getAll().stream().map(Note::getTitle).collect(Collectors.toList());
    }

    public List<Note> getByFilter(SearchFilter searchFilter) {
        return noteRepository.getAll().stream()
                .filter(it -> searchFilter.getTitle() == null || it.getTitle().contains(searchFilter.getTitle()))
                .filter(it -> searchFilter.getEmail() == null || it.getEmail().contains(searchFilter.getEmail()))
                .filter(it -> searchFilter.getMessage() == null || it.getMessage().contains(searchFilter.getMessage()))
                .filter(it -> searchFilter.getCreatedOn() == null || it.getCreatedOn().equals(searchFilter.getCreatedOn()))
                .sorted(Comparator.comparing(Note::getCreatedOn))
                .collect(Collectors.toList());
    }

    public boolean add(Note note) {
        return noteRepository.add(note);
    }
}
