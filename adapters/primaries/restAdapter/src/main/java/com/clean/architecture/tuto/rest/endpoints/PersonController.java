package com.clean.architecture.tuto.rest.endpoints;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.use.cases.personne.CreatePersonUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.DisplayDetailsPersonUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.GetAllPersonUseCase;
import com.clean.architecture.tuto.rest.models.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private GetAllPersonUseCase getAllPersonUseCase;
    private DisplayDetailsPersonUseCase displayDetailsPersonUseCase;
    private CreatePersonUseCase createPersonUseCase;

    @Autowired
    public PersonController(GetAllPersonUseCase getAllPersonUseCase,
                            DisplayDetailsPersonUseCase displayDetailsPersonUseCase,
                            CreatePersonUseCase createPersonUseCase) {
        this.getAllPersonUseCase = getAllPersonUseCase;
        this.displayDetailsPersonUseCase = displayDetailsPersonUseCase;
        this.createPersonUseCase = createPersonUseCase;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            List<Person> allPersons = this.getAllPersonUseCase.execute();
            return new ResponseEntity<>(new ResponseApi<>(allPersons), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseApi<>(Collections.singletonList("Erreur technique : Veuillez contacter le support technique")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        Person p = Person.builder()
                .id(id).build();
        try {
            Optional<Person> optionalPerson = this.displayDetailsPersonUseCase.execute(p);
            return new ResponseEntity<>(new ResponseApi<>(optionalPerson), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ResponseApi<>(e.getErrorsList()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseApi<>(Collections.singletonList("Erreur technique : Veuillez contacter le support technique")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody Person person) {
        try {
            person = this.createPersonUseCase.execute(person);
            return new ResponseEntity<>(new ResponseApi<>(person), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ResponseApi<>(e.getErrorsList()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseApi<>(Collections.singletonList("Erreur technique : Veuillez contacter le support technique")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
