package ru.sberbank.async.command;

class MutlCommand extends CancelledCommand<Integer> {

    final int a;
    final int b;
    private boolean executed;

    MutlCommand(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public CommandStatus<Integer> execute() {
        executed = true;
        return new CommandStatus.OK<>(a * b);
    }

    boolean isExecuted(){
        return executed;
    }

}
