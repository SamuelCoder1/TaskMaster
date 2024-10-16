package com.riwi.TaskMaster.application.services.generic;

import org.springframework.http.ResponseEntity;

public interface Create<Entity, EntityRequest> {
    public Entity create(EntityRequest entityRequest);
}
