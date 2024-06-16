package com.rosada.crud.enums;

import lombok.Getter;

@Getter
public enum State {
    ACTIVE("Activo", "Usuario activo y con acceso completo."),
    INACTIVE("Inactivo", "Usuario inactivo con acceso limitado."),
    PENDING("Pendiente", "Usuario pendiente de activación o aprobación."),
    BLOCKED("Bloqueado", "Usuario bloqueado y sin acceso.");

    private final String stateName;
    private final String description;

    State(String stateName, String description) {
        this.stateName = stateName;
        this.description = description;
    }

}

