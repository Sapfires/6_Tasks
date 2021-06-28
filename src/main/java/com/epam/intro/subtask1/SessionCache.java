package com.epam.intro.subtask1;

import java.util.ArrayList;
import java.util.List;

public class SessionCache {
    private ThreadLocal<Session> currentSession = new ThreadLocal<>();
    public Session getOrCreateSessionForUser(User user) {
        if (currentSession.get() == null) {
            currentSession.set(new Session(user));
        }
        return currentSession.get();
    }
    public void validatePermission(Operation operation) {
    }

    public List<Operation> getAvailableOperations() {

        List<Operation> operations = new ArrayList<>(getCurrentSession().getUser().getRole().getOperations());
        operations.add(Operation.QUIT);
        return operations;
    }

    private Session getCurrentSession() {
        return currentSession.get();
    }
}
