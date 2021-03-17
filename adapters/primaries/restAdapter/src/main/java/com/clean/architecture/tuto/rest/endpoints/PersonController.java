package com.clean.architecture.tuto.rest.endpoints;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.use.cases.personne.*;
import com.clean.architecture.tuto.core.utils.Utils;
import com.clean.architecture.tuto.rest.models.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private GetAllPersonUseCase getAllPersonUseCase;
    private DisplayDetailsPersonUseCase displayDetailsPersonUseCase;
    private CreatePersonUseCase createPersonUseCase;
    private DeletePersonUseCase deletePersonUseCase;
    private UpdatePersonUseCase updatePersonUseCase;

    @Autowired
    public PersonController(GetAllPersonUseCase getAllPersonUseCase,
                            DisplayDetailsPersonUseCase displayDetailsPersonUseCase,
                            CreatePersonUseCase createPersonUseCase,
                            DeletePersonUseCase deletePersonUseCase,
                            UpdatePersonUseCase updatePersonUseCase) {
        this.getAllPersonUseCase = getAllPersonUseCase;
        this.displayDetailsPersonUseCase = displayDetailsPersonUseCase;
        this.createPersonUseCase = createPersonUseCase;
        this.deletePersonUseCase = deletePersonUseCase;
        this.updatePersonUseCase = updatePersonUseCase;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(new ResponseApi<>(this.getAllPersonUseCase.execute()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseApi<>(Collections.singletonList("Erreur technique : Veuillez contacter le support technique")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{uuid}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable("uuid") String uuid) throws TechnicalException, BusinessException {
        Optional<Person> optionalPerson = this.displayDetailsPersonUseCase.execute(uuid);
        return optionalPerson.isPresent() ? new ResponseEntity<>(new ResponseApi<>(optionalPerson), HttpStatus.OK)
                                          : new ResponseEntity<>(new ResponseApi<>(Collections.singletonList("Inconnu")), HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody Person person) throws TechnicalException, BusinessException {
        return new ResponseEntity<>(new ResponseApi<>(this.createPersonUseCase.execute(person)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Person person) throws TechnicalException, BusinessException, SQLException, UnknownHostException {
        return new ResponseEntity<>(new ResponseApi<>(this.updatePersonUseCase.execute(person)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteById(@PathVariable("uuid") String uuid) throws SQLException, UnknownHostException, TechnicalException, BusinessException {
        return new ResponseEntity<>(new ResponseApi<>(this.deletePersonUseCase.execute(uuid)), HttpStatus.OK);
    }


}
