package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN command);
}
