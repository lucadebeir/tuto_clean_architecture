package com.clean.architecture.tuto.core.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DeleteInformations {

    private List<byte[]> uuidsDeleted;

    public DeleteInformations() {
        this.uuidsDeleted = new ArrayList<>();
    }

    public void addUuidDeleted(byte[] uuid) {
        this.uuidsDeleted.add(uuid);
    }

}


