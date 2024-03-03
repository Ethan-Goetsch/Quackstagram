package refactored.util.generic.reactive;

public interface IObserver<T>
{
    public void execute(T item);
}