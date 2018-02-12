package ru.sberbank.async.api.tasks;

import android.graphics.Bitmap;

import ru.sberbank.async.command.CancelledCommand;
import ru.sberbank.async.command.CommandStatus;

/**
 */

public class LoadImageFromCacheCommand extends CancelledCommand<Bitmap> {

    public LoadImageFromCacheCommand(String imageUrl) {

    }

    @Override
    public CommandStatus<Bitmap> execute() {
        return null;
    }
}
