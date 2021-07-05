package com.epam.intro.subtask2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Note {
    private String title;
    private ZonedDateTime createdOn;
    private String email;
    private String message;
}
