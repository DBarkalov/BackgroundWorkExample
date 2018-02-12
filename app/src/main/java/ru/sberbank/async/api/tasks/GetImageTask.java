package ru.sberbank.async.api.tasks;

import android.graphics.Bitmap;

import ru.sberbank.async.command.Command;
import ru.sberbank.async.command.CommandGroup;
import ru.sberbank.async.command.CommandStatus;


public class GetImageTask extends CommandGroup<Bitmap> {

    private final String mImageUrl;

    public GetImageTask(String imageUrl) {
        super(new LoadImageFromCacheCommand(imageUrl));
        this.mImageUrl = imageUrl;
    }

    @Override
    protected void onCommandExecuted(Command<?> command, CommandStatus<?> result) {
        if(command instanceof LoadImageFromCacheCommand && !result.isOK()){
            addCommand(new LoadImageCommand(mImageUrl));
        } else if (command instanceof LoadImageCommand && result.isOK()){
            final Bitmap bitmap = (Bitmap) result.getData();
            addCommand(new DecodeImageCommand(bitmap));
        } else if (command instanceof DecodeImageCommand && result.isOK()){
            final Bitmap bitmap = (Bitmap) result.getData();
            addCommand(new SaveImageToLocalStorageCommand(bitmap));
        }
    }

}
