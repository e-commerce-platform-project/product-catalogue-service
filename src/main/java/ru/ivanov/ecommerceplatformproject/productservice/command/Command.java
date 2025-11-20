package ru.ivanov.ecommerceplatformproject.productservice.command;

public interface Command {

    interface Handler<C extends Command> {
        void handle(C command);
    }
}
