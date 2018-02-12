package ru.sberbank.async.command;

import android.util.Log;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *  базовый класс группы команд (композиция команд)
 */
public abstract class CommandGroup<T> extends CancelledCommand<T> {

    private final String TAG = "CommandGroup";

    /**
     * список команд
     */
    private final LinkedList<CancelledCommand<?>> mCommands;

    /**
     * флаг что выполнение отменено
     */
    private volatile CancelledCommand<?> mCurrentCommand;

    /**
     *  результат выполнения
     */
    private CommandStatus<?> mResult;

    /**
     *  определяет в каком потоке выполняется единичная команда
     */
    protected static SingleCommandExecutor sSingleCommandExecutor;

    static {
        setSingleCommandExecutor(new DefaultSingleCommandExecutor());
    }

    /**
     * наследники могут изменить где выполнять единичные команды
     */
    protected static void setSingleCommandExecutor(SingleCommandExecutor exeсutor){
        sSingleCommandExecutor = exeсutor;
    }

    public CommandGroup(CancelledCommand<?>... command) {
        mCommands = new LinkedList<>();
        mCommands.addAll(Arrays.asList(command));
    }

    /**
     * добавить комадну в группу
     * @param command
     */
    public void addCommand(CancelledCommand<?> command){
        Log.v(TAG , "addCommand " + command.getClass().getSimpleName());
        mCommands.add(command);
    }

    /**
     * выполнить группу команд
     * вернуть суммарный результат
     */
    @Override
    public CommandStatus execute() {
        while (!mCommands.isEmpty() && !isCancel()) {
             executeNextCommand();
        }
        return getAccumulatedResult();
    }

    /**
     * вернуть аккумулированый результат группы команд
     */
    public CommandStatus<?> getAccumulatedResult() {
        return mResult;
    }

    /**
     * отменить выполнение группы команд
     */
    @Override
    public void cancel() {
        super.cancel();
        cancelCurrentCommand();
    }

    private void cancelCurrentCommand(){
        if(mCurrentCommand != null) {
            mCurrentCommand.cancel();
        }
    }

    /**
     * выполнять следующую команду
     */
    protected void executeNextCommand() {
        mCurrentCommand = mCommands.pollFirst();
        final CommandStatus<?> result = executeSingleCommand();
        Log.v(TAG, "executeNextCommand command=" + mCurrentCommand.getClass().getSimpleName() + "result= " + result.toString() );
        setResult(result);
        defaultCommandStatusHandler(result);
        onCommandExecuted(mCurrentCommand, result);
    }

    /**
     * выполнить единичную команду из группы команд
     * @return
     */
    protected CommandStatus<?> executeSingleCommand() {
        return sSingleCommandExecutor.execute(mCurrentCommand);
    }

    protected void defaultCommandStatusHandler(CommandStatus<?> result) {
        if (!result.isOK()) {
            removeAllCommands();
        }
    }

    protected void setResult(CommandStatus<?> result) {
        mResult = result;
    }

    protected void removeAllCommands(){
        mCommands.clear();
    }

    /**
     *  вызывается после того как единичная команда выполнилась
     *  здель можно проверить результат и например остановить выполнение
     *  или изменить список команд группы
     * @param command - команда которая выполнилась
     * @param result - результат ее выполнения
     */
    abstract protected void onCommandExecuted(Command<?> command, CommandStatus<?> result);


    /**
     *  исполнение единичной команды из группы
     *  например, можновыбранные команды выполнять в выбранных потоках
     */
    public interface SingleCommandExecutor {
        /**
         * @param command команда которую надо выполнить
         * @return  статус выполнения
         */
        CommandStatus<?> execute(CancelledCommand<?> command);
    }

    /**
     * дефолтная реализация исполнение единичных команд в том же потоке
     */
    public static  class DefaultSingleCommandExecutor implements SingleCommandExecutor {
        @Override
        public CommandStatus<?> execute(CancelledCommand<?> command) {
            return command.execute();
        }
    }

}
