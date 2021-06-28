package com.epam.intro.subtask1;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.intro.subtask1.Operation.*;

public enum Role {
    USER(Arrays.stream(new Operation []{GET_ALL, GET_BOOKS_BY_FILTER, ADD_DRAFT}).collect(Collectors.toList())),
    ADMINISTRATOR(Arrays.stream(new Operation[] {GET_ALL, GET_BOOKS_BY_FILTER, ADD_DRAFT, APPROVE_BOOK}).collect(Collectors.toList()));

    Role(List<Operation> operations) {
        this.operations = operations;
    }
    @Getter
    private List<Operation> operations;
    public static Role getRoleByStringValue(String value) {
        return Arrays.stream(values()).filter(it -> it.name().equals(value)).findAny().get();
    }
}
