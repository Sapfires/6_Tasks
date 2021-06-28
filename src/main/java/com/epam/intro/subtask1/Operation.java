package com.epam.intro.subtask1;

import lombok.Getter;

public enum Operation {
    GET_ALL(0),
    GET_BOOKS_BY_FILTER(1),
    ADD_DRAFT(2),
    APPROVE_BOOK(3),
    QUIT(4);
    @Getter
    private Integer code;

    Operation(Integer code) {
        this.code = code;
    }
}
