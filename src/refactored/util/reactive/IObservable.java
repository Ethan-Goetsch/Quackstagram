package refactored.util.reactive;

import refactored.util.functions.IAction;

public interface IObservable
{
    public void subscribe(IAction onExecute);
    public void unsubscribe(IAction onExecute);
}