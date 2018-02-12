package ru.sberbank.async;

import android.graphics.Bitmap;

import ru.sberbank.async.action.TargetAction;
import ru.sberbank.async.api.RequestManager;
import ru.sberbank.async.command.Command;
import ru.sberbank.async.command.CommandStatus;

/**
 */
public class ShowImageAction extends TargetAction<TestActivity, Bitmap> {
    private final String mUrl;

    protected ShowImageAction(String url, TestActivity target) {
        super(target);
        this.mUrl = url;
    }

    @Override
    public void execute() {
        RequestManager.getInstance().getImage(mUrl, this);
    }

    @Override
    protected void onCancel(TestActivity target, Command<CommandStatus<Bitmap>> command) {
        target.showBitmapLoadCancelled();
    }

    @Override
    protected void onComplete(TestActivity target, Command<CommandStatus<Bitmap>> command, CommandStatus<Bitmap> result) {
        if (result.isOK()) {
            //no cast !
            Bitmap bmp = result.getData();
            target.showBitmap(bmp);
        } else {
            target.showBitmapLoadError();
        }
    }
}
