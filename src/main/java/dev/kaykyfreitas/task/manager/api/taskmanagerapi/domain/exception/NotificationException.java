package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.handler.Notification;

public class NotificationException extends DomainException{
    public NotificationException(final String aMessage, final Notification aNotification) {
        super(aMessage, aNotification.getErrors());
    }
}
