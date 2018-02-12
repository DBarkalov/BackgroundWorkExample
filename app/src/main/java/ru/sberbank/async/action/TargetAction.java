package ru.sberbank.async.action;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import ru.sberbank.async.TestActivity;
import ru.sberbank.async.command.Command;
import ru.sberbank.async.command.CommandCompletedListener;
import ru.sberbank.async.command.CommandStatus;

public abstract class TargetAction<Target extends ActionExecutor, Result>
        implements Action, CommandCompletedListener<CommandStatus<Result>>,
        Serializable {

    private WeakReference<Target> mTarget;

    public TargetAction() {
        this(null);
    }

    public TargetAction(Target target) {
        setTarget(target);
    }

    public void setTarget(Target target) {
        mTarget = new WeakReference<>(target);
    }

    @Override
    public void onCommandCancelled(Command<CommandStatus<Result>> command) {
        Target target = getTarget();
        if(target != null){
            onCancel(target, command);
        }
    }

    @Override
    public void onCommandComplete(Command<CommandStatus<Result>> command, CommandStatus<Result> result) {
        Target target = getTarget();
        if(target != null){
            onComplete(target, command, result);
        }
    }

    protected Target getTarget() {
        return mTarget.get();
    }

    protected abstract void onCancel(Target target, Command<CommandStatus<Result>> command);

    protected abstract void onComplete(Target target, Command<CommandStatus<Result>> command, CommandStatus<Result> result);
}

