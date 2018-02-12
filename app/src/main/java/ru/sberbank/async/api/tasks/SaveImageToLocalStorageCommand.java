package ru.sberbank.async.api.tasks;

import android.graphics.Bitmap;

import ru.sberbank.async.command.CancelledCommand;
import ru.sberbank.async.command.CommandStatus;

/**
 * Created by usr on 29.01.2018.
 */

public class SaveImageToLocalStorageCommand extends CancelledCommand<Void> {
    public SaveImageToLocalStorageCommand(Bitmap bitmap) {

    }

    @Override
    public CommandStatus<Void> execute() {
        return null;
    }
}
