package ru.sberbank.async.api.tasks;

import android.graphics.Bitmap;

import ru.sberbank.async.command.CancelledCommand;
import ru.sberbank.async.command.CommandStatus;

/**
 * Created by usr on 29.01.2018.
 */

public class DecodeImageCommand extends CancelledCommand<Bitmap> {

    public DecodeImageCommand(Bitmap bitmap) {

    }

    @Override
    public CommandStatus<Bitmap> execute() {
        return null;
    }
}
