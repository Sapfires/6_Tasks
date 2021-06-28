package com.epam.intro.subtask1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
    private Integer code;
    private String name;
    private String description;
    private String author;
    private boolean isDraft;
}
