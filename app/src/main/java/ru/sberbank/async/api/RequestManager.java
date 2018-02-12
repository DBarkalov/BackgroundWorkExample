package ru.sberbank.async.api;


import ru.sberbank.async.api.tasks.GetImageTask;
import ru.sberbank.async.command.CommandCompletedListener;
import ru.sberbank.async.command.CommandExecutor;
import ru.sberbank.async.command.ICommandExecutor;

public class RequestManager {

    private static final String TAG = "RequestManager";
    private static RequestManager sRequestManager;
    private final ICommandExecutor mCommandExecutor = CommandExecutor.getInstance();

    public static RequestManager getInstance() {
        if (sRequestManager == null) {
            sRequestManager = new RequestManager();
        }
        return sRequestManager;
    }

    private RequestManager() {
    }


    public void getImage(final String imageUrl, final CommandCompletedListener listener){
        mCommandExecutor.exeTask(new GetImageTask(imageUrl), listener);
    }

}
