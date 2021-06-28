package com.epam.intro.subtask1;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginService {
    private UserRepository userRepository;
    private SessionCache sessionCache;
    public void login(String login, String password) {
        User user = userRepository.getUserByLoginAndPassword(login, password);
        sessionCache.getOrCreateSessionForUser(user);
    }
}
