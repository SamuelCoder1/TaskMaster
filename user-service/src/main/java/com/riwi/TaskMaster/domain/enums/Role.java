package com.riwi.TaskMaster.domain.enums;

import java.util.Collections;
import java.util.Set;

public enum Role {

    ADMIN(Set.of("CREATE_PROJECT", "DELETE_PROJECT", "VIEW_PROJECTS", "MANAGE_USERS")),
    USER(Set.of("VIEW_PROJECTS"));

    private final Set<String> permissions;

    Role(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }
}