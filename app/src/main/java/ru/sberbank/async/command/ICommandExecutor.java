package ru.sberbank.async.command;


public interface ICommandExecutor {

    /**
     * выполнить команду
     * @param cmd - команда
     */
    void exeTask(Command<?> cmd);

    /**
     * выполнить команду
     * @param cmd - команда
     * @param listener - листнер вызовется когда завершится выполнение команды
     */
    void exeTask(Command<?> cmd, final CommandCompletedListener listener);

    /*
     * отменить выпонение всех команд
     */
    void cancelAll();

}
