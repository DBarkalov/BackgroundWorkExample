package ru.sberbank.async;

import android.graphics.Bitmap;

import java.io.IOException;

import ru.sberbank.async.api.tasks.LoadImageCommand;
import ru.sberbank.async.command.CommandStatus;

/**
 * Created by usr on 05.02.2018.
 */

public class cc {

    void dwo(){

        CommandStatus<Bitmap> result = new LoadImageCommand("url").execute();
        if(result.isOK()){
            Bitmap bitmap = result.getData();
            // todo use bitmap
        } else {
            IOException exception = (IOException) result.getError();
            //todo show error
        }

    }
}
