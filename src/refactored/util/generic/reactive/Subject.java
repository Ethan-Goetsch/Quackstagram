package refactored.util.generic.reactive;

import java.util.ArrayList;
import java.util.List;
import refactored.util.generic.functions.IAction;

public class Subject<T> implements IObservable<T>, IObserver<T>
{
    private List<IAction<T>> actions;

    public Subject()
    {
        actions = new ArrayList<>();
    }

    @Override
    public void subscribe(IAction<T> onExecute)
    {
        actions.add(onExecute);
    }

    @Override
    public void unsubscribe(IAction<T> onExecute)
    {
        actions.remove(onExecute);
    }

    @Override
    public void execute(T item)
    {
        actions.forEach(action -> action.execute(item));
    }
}