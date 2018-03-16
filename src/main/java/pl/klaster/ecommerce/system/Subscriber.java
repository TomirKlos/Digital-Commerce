package pl.klaster.ecommerce.system;

public interface Subscriber<T> {
    public void handle(T event);
}
