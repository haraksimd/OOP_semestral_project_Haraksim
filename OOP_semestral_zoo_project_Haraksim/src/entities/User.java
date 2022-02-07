package entities;

public interface User {
    public default void submitNewTask(){};

    public default void sendMessage(){};
}
