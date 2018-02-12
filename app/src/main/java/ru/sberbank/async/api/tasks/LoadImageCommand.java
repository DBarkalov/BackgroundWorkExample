package ru.sberbank.async.api.tasks;

import android.graphics.Bitmap;

import java.io.IOError;
import java.io.IOException;

import ru.sberbank.async.command.CancelledCommand;
import ru.sberbank.async.command.CommandStatus;

/**
 */

public class LoadImageCommand extends CancelledCommand<Bitmap> {

    private final String url;

    public LoadImageCommand(String imageUrl) {
        url = imageUrl;
    }

    @Override
    public CommandStatus<Bitmap> execute() {
        try {
            return new CommandStatus.OK(load(url));
        } catch (IOException e){
            return new CommandStatus.ERROR(e);
        }
    }

    private Bitmap load(String url) throws IOException {
        //todo load
        return null;
    }
}
