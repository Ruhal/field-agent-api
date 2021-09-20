package learn.field_agent.controllers;

import learn.field_agent.domain.MissionService;
import learn.field_agent.domain.Result;
import learn.field_agent.models.Mission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/mission")
public class MissionController {

    private final MissionService service;

    public MissionController(MissionService service) {
        this.service = service;
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<Mission> findById(@PathVariable int missionId) {
        Mission mission = service.findById(missionId);
        if (mission == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mission, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody(required = false) Mission mission) {
        Result<Mission> result = service.add(mission);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{missionId}")
    public ResponseEntity<Object> update(@PathVariable int missionId, @RequestBody(required = false) Mission mission) {
        if (missionId != mission.getMissionId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Mission> result = service.update(mission);

        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);

    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity<Void> deleteById(@PathVariable int missionId) {
        if (service.deleteById(missionId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
