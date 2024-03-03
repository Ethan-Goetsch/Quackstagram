package refactored.util.generic.functions;

@FunctionalInterface
public interface IAction<T>
{
    public void execute(T item);
}