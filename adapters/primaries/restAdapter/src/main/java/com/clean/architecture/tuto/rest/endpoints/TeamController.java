package com.clean.architecture.tuto.rest.endpoints;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.use.cases.equipe.CreateTeamUseCase;
import com.clean.architecture.tuto.core.use.cases.equipe.GetAllTeamUseCase;
import com.clean.architecture.tuto.rest.models.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private CreateTeamUseCase createTeamUseCase;
    private GetAllTeamUseCase getAllTeamUseCase;

    @Autowired
    public TeamController(CreateTeamUseCase createTeamUseCase, GetAllTeamUseCase getAllTeamUseCase) {
        this.getAllTeamUseCase = getAllTeamUseCase;
        this.createTeamUseCase = createTeamUseCase;
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody Team team) {
        try {
            team = this.createTeamUseCase.execute(team);
            return new ResponseEntity<>(new ResponseApi<>(team), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ResponseApi<>(e.getErrorsList()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseApi<>(Collections.singletonList("Erreur technique : Veuillez contacter le support technique")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
}
