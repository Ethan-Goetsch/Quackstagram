package refactored.util.generic.reactive;

import refactored.util.generic.functions.IAction;

public interface IObservable<T>
{
    public void subscribe(IAction<T> onExecute);
    public void unsubscribe(IAction<T> onExecute);
}