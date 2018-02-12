package ru.sberbank.async.command;

class SummCommand extends CancelledCommand<Integer> {

    final int a;
    final int b;
    private boolean executed;

    SummCommand(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public CommandStatus<Integer> execute() {
        executed = true;
        return new CommandStatus.OK<>(a + b);
    }
}
