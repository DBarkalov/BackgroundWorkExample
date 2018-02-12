package ru.sberbank.async;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.async.action.Action;
import ru.sberbank.async.action.ActionExecutor;
import ru.sberbank.async.action.TargetAction;

public class TestActivity extends AppCompatActivity implements ActionExecutor {

    private static final String ACTIONS_KEY = "actions_key";
    private List<TargetAction<TestActivity,?>> mActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreActions(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveActions(mActions, outState);
    }

    private void saveActions(List<TargetAction<TestActivity, ?>> mActions, Bundle outState) {
        outState.putSerializable(ACTIONS_KEY, new ArrayList(mActions));
    }

    private void restoreActions(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mActions = (ArrayList<TargetAction<TestActivity, ?>>) savedInstanceState.getSerializable(ACTIONS_KEY);
            for (TargetAction<TestActivity, ?> action : mActions) {
                action.setTarget(this);
            }
        }
    }

    private void startLoadBitmap(String url){
        runAction(new ShowImageAction(url, this));
    }

    protected void showBitmapLoadError() {
    }

    protected void showBitmap(Bitmap bitmap){
      //imageView.setBitmap(bitmap);
    }

    protected void showBitmapLoadCancelled() {
    }

    @Override
    public void runAction(Action action) {
        action.execute();
    }

}
