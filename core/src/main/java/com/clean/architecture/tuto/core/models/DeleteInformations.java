package com.clean.architecture.tuto.core.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DeleteInformations {

    private List<String> idsDeleted;

    public DeleteInformations() {
        this.idsDeleted = new ArrayList<>();
    }

    public void addIdDeleted(String id) {
        this.idsDeleted.add(id);
    }

}


