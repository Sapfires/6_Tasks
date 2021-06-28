package com.epam.intro.subtask1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {
    private File file = new File("src\\main\\resources\\users.txt");
    private List<User> users = new ArrayList<>();
    public UserRepository() {
        try {
            List<String> rawUsers = FileUtils.readLines(file, "UTF-8");
            users = rawUsers.stream().map(this::buildUser).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User buildUser(String it) {
        String[] rawUser =  it.split(" : ");
        return User.builder()
                .login(rawUser[0])
                .name(rawUser[3])
                .password(rawUser[1])
                .role(Role.getRoleByStringValue(rawUser[2]))
                .build();
    }

    public User getUserByLoginAndPassword(String login, String password) {
        return users.stream()
                .filter(it -> it.getLogin().equals(login))
                .filter(it -> it.getPassword().equals(password))
                .findAny()
                .get();
    }
}
