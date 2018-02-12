package ru.sberbank.async.command;

/**
 *  интерфейс команды
 */
public interface Command<R> {

    /** выполнить команду
     *
     * @return R - результат выполнения команды
     */
    R execute();

    /**
     * отменить выполнение команды
     */
    void cancel();

    /**
     * @return true - если выполнение команды было отменено
     */
    boolean isCancel();
}
