package rxjava;

public interface Action<T> {
    void call(T t);
}
