package refactored.util.generic.functions;

@FunctionalInterface
public interface IFunc<T, TK>
{
    public TK execute(T item);
}