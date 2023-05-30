package src.Multithreading;

import java.util.concurrent.LinkedBlockingQueue;

public class Queueueue {
    static LinkedBlockingQueue<MyObjectReader> queue = new LinkedBlockingQueue<>(10);

    public static void add(MyObjectReader reader) throws IllegalStateException {
        queue.add(reader);
    }

    public static MyObjectReader poll() throws NullPointerException {
        return queue.poll();

    }
}
