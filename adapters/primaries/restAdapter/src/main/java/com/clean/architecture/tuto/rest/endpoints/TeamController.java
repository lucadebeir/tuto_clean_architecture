package com.clean.architecture.tuto.rest.endpoints;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.use.cases.equipe.*;
import com.clean.architecture.tuto.core.utils.Utils;
import com.clean.architecture.tuto.rest.models.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private CreateTeamUseCase createTeamUseCase;
    private GetAllTeamUseCase getAllTeamUseCase;
    private DisplayDetailsTeamUseCase findByIdTeamUseCase;
    private DeleteTeamUseCase deleteTeamUseCase;
    private UpdateTeamUseCase updateTeamUseCase;

    @Autowired
    public TeamController(CreateTeamUseCase createTeamUseCase,
                          GetAllTeamUseCase getAllTeamUseCase,
                          DisplayDetailsTeamUseCase findByIdTeamUseCase,
                          DeleteTeamUseCase deleteTeamUseCase,
                          UpdateTeamUseCase updateTeamUseCase) {
        this.getAllTeamUseCase = getAllTeamUseCase;
        this.createTeamUseCase = createTeamUseCase;
        this.findByIdTeamUseCase = findByIdTeamUseCase;
        this.deleteTeamUseCase = deleteTeamUseCase;
        this.updateTeamUseCase = updateTeamUseCase;
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody Team team) throws TechnicalException, BusinessException {
        return new ResponseEntity<>(new ResponseApi<>(this.createTeamUseCase.execute(team)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> findById(@PathVariable("uuid") String uuid) throws TechnicalException, BusinessException {
        Optional<Team> optionalTeam = this.findByIdTeamUseCase.execute(uuid);
        return optionalTeam.isPresent() ? new ResponseEntity<>(new ResponseApi<>(optionalTeam), HttpStatus.OK)
                : new ResponseEntity<>(new ResponseApi<>(Collections.singletonList("Inconnu")), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            List<Team> allTeam = this.getAllTeamUseCase.execute();
            return new ResponseEntity<>(new ResponseApi<>(allTeam), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseApi<>(Collections.singletonList("Erreur technique : Veuillez contacter le support technique")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Team team) throws TechnicalException, BusinessException, SQLException, UnknownHostException {
        return new ResponseEntity<>(new ResponseApi<>(this.updateTeamUseCase.execute(team)), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteById(@PathVariable("uuid") String uuid) throws SQLException, UnknownHostException, TechnicalException, BusinessException {
        return new ResponseEntity<>(new ResponseApi<>(this.deleteTeamUseCase.execute(uuid)), HttpStatus.OK);
    }
}
