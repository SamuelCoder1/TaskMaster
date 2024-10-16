package com.riwi.TaskMaster.application.services.generic;

public interface Register<Entity, EntityRequest> {
    public Entity register(EntityRequest entityRequest);
}
