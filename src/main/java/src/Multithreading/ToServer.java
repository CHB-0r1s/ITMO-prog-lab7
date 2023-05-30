package src.Multithreading;

import src.Command.Command;

import java.io.Serializable;

public class ToServer implements Serializable {
    private Command command;

    public ToServer(Command command) {
        this.command = command;
    }
}
