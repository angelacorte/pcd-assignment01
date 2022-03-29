package conc.model.agent;

/**
 * A bounded buffer used to share data between threads.
 * @param <T> the data type.
 */
public interface BoundedBuffer<T> {
    /**
     * Add the item to the buffer.
     * @param item The item to add.
     */
    void add(T item) throws InterruptedException;

    /**
     *
     * @return an item in the buffer.
     */
    T get() throws InterruptedException;
}
