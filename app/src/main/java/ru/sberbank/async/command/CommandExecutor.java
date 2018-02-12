package ru.sberbank.async.command;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class CommandExecutor implements ICommandExecutor {
    private static final String TAG = "CommandExecutor";

    private Set<Task> mRunningTasks = new HashSet<>();
    private static CommandExecutor sCommandExecutor;

    public static CommandExecutor getInstance(){
         if(sCommandExecutor == null){
             sCommandExecutor = new CommandExecutor();
          }
        return sCommandExecutor;
    }

    private CommandExecutor(){}

    /**
     * выполнить команду
     * @param cmd - команда
     */
    @Override
    public void exeTask(Command<?> cmd) {
        exeTask(cmd, null);
    }

    /**
     * выполнить команду
     * @param cmd - команда
     * @param listener - листнер вызовется когда завершится выполнение команды
     */
    @Override
    public void exeTask(Command<?> cmd, final CommandCompletedListener listener) {
        runTask(createTask(cmd, listener));
    }

    /**
     * отменить выпонение всех команд
     */
    @Override
    public void cancelAll(){
        for (Task t: mRunningTasks){
            t.cancel();
        }
        mRunningTasks.clear();
    }

    private void runTask(final Task task){
        task.execute();
        mRunningTasks.add(task);
    }

    /**
     * @param cmd - команда которую надо выполнить
     * @param listener - листнер выполненя команды, вызывается в main thread
     * @param <R> - результат выполнения команды
     * @return AsyncTask который выполняет команду
     */
    private <R> Task createTask(final Command<R> cmd, final CommandCompletedListener<R> listener) {
        return new Task<R>(cmd){
            @Override
            protected void onPostExecute(final R result) {
                Log.v(TAG, "onPostExecute" + cmd.getClass().getSimpleName());
                if(listener!= null) {
                    if(cmd.isCancel()){
                        listener.onCommandCancelled(cmd);
                    } else {
                        listener.onCommandComplete(cmd, result);
                    }
                }
                onTaskCompleted(this);
            }
        };
    }

    private void onTaskCompleted(Task task){
        mRunningTasks.remove(task);
    }

    /**
     * asynctask - который выполняет команду
     * @param <R> - результат выполнения команды
     */
    private static class Task<R> extends AsyncTask<Command<R>, Void, R> {
        private final Command<R> command;

        private Task(Command<R> command) {
            this.command = command;
        }

        /**
         * выполнить Task
         * выполнять на пуле потоков AsyncTask.THREAD_POOL_EXECUTOR
         */
        public void execute(){
            Log.v(TAG, "start execute " + command.getClass().getSimpleName());
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, command);
        }

        @Override
        protected R doInBackground(Command<R>... params) {
            final Command<R> cmd = params[0];
            return cmd.execute();
        }

        /**
         *  отменить выполнение
         */
        public void cancel(){
            Log.v(TAG, "cancel " + command.getClass().getSimpleName());
            command.cancel();
        }

    }
}
