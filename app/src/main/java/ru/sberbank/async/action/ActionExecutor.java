package ru.sberbank.async.action;

/**
 *  этот интерфейс инплементят активити и фрагмент
 */
public interface ActionExecutor {

    /**
     * выполнить экшен в авторизованой зоне
     * @param action
     */
    void runAction(Action action);
}
