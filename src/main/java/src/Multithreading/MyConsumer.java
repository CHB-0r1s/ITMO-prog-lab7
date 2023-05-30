package src.Multithreading;

import src.ClientServer.ServerFunc;

public class MyConsumer implements Runnable{

    @Override
    public void run() {
        while (true) {
            try {
                MyObjectReader input = Queueueue.poll();
                synchronized (Queueueue.queue) {
                    Queueueue.queue.notifyAll();
                }
                Response response = new Response();
                response.setMessage(ServerFunc.execution(input.getCommand(), input.getUser()));
                response.setSocket(input.getSocket());
                new Thread(response).start();
            } catch (NullPointerException e) {
                synchronized (Queueueue.queue) {
                    try {
                        Queueueue.queue.wait();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
}
