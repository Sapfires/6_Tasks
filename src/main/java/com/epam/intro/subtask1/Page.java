package com.epam.intro.subtask1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Page<T> {
    private List<T> elements = new ArrayList<>();
    private Integer pageNumber;
    private Integer pageCount;
    private Integer elementsPerPage;
}
