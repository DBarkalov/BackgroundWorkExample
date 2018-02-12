package ru.sberbank.async.command;

/**
 * листнер выполнения команды
 * @param <R>
 */
public interface CommandCompletedListener<R> {
    void onCommandComplete(Command<R> mCommand, R result);
    void onCommandCancelled(Command<R> cmd);
}
