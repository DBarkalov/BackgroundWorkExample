package ru.sberbank.async.command;

import java.io.IOException;

/**
 * статус выполнения команды
 * @param <T> - результат выполнения команды
 */
public abstract class CommandStatus<T> {
    /**
     *  полезные данные
     */
    private final T data;
    /*
    *  информация о ошибке
    * */
    private final Throwable error;

    protected CommandStatus(T data, Throwable error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public Throwable getError(){
        return error;
    }

    public abstract boolean isOK();

    /**
     *  статус - команда выполнена без ошибок
     * @param <T> - результат выполния команды
     */
    public static class OK<T> extends CommandStatus<T> {
        public OK(T result) {
            super(result, null);
        }

        public OK() {
            super(null, null);
        }

        @Override
        public boolean isOK() {
            return true;
        }
    }

    /**
     * статус -ошибка в процессе выполнения
     * @param <T> - информация о ошибке
     */
    public static class ERROR<T> extends CommandStatus<T> {
        public ERROR(Throwable error) {
            super(null, error);
        }

        @Override
        public boolean isOK() {
            return false;
        }
    }

    @Override
    public String toString() {
        return "class = " + this.getClass().getSimpleName() + " data= " + String.valueOf(getData());
    }
}
