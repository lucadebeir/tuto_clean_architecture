package com.clean.architecture.tuto.rest.endpoints;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.use.cases.personne.GetAllPersonUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private GetAllPersonUseCase getAllPersonUseCase;

    @Autowired
    public PersonController(GetAllPersonUseCase getAllPersonUseCase) {
        this.getAllPersonUseCase = getAllPersonUseCase;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll() throws UnknownHostException {
        List<Person> allPersons = this.getAllPersonUseCase.execute();
        return ResponseEntity.ok(allPersons);
    }
}
