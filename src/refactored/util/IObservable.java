package src.Refactored.util;

public interface IObservable
{
    public void subscribe(IAction onExecute);
    public void unsubscribe(IAction onExecute);
}