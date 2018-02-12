package ru.sberbank.async.command;

import java.util.LinkedHashMap;

public class MockSingleCommandExecutor implements CommandGroup.SingleCommandExecutor {

    private final LinkedHashMap<Class<?>, CommandStatus<?>> mockResults;

    public MockSingleCommandExecutor(LinkedHashMap<Class<?>, CommandStatus<?>> mockResults) {
        this.mockResults = mockResults;
    }

    @Override
    public CommandStatus<?> execute(CancelledCommand<?> command) {
        if (mockResults.containsKey(command.getClass())) {
            return mockResults.remove(command.getClass());
        } else {
            throw new IllegalStateException("command " + command.getClass().getSimpleName() + " not in mock resultd map");
        }
    }
}
