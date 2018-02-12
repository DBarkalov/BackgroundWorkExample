package ru.sberbank.async.command;

/**
 *  базовый класс команд выполнение которых может быть отменено
 */
public abstract class CancelledCommand<T> implements Command<CommandStatus<T>> {

    private volatile boolean  mCancel = false;

    @Override
    public void cancel() {
        mCancel = true;
    }

    public boolean isCancel() {
        return mCancel;
    }
}
