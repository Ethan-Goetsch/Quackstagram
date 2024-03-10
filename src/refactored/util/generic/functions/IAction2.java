package refactored.util.generic.functions;

@FunctionalInterface
public interface IAction2<T1, T2>
{
    public void execute(T1 item1, T2 item2);
}