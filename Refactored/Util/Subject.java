package Refactored.Util;

import java.util.ArrayList;
import java.util.List;

public class Subject implements IObservable
{
    private List<IAction> actions;

    public Subject()
    {
        actions = new ArrayList<>();
    }

    @Override
    public void subscribe(IAction onExecute)
    {
        actions.add(onExecute);
    }

    @Override
    public void unsubscribe(IAction onExecute)
    {
        actions.remove(onExecute);
    }

    @Override
    public void execute()
    {
        actions.forEach(action -> action.execute());
    }
}