package learn.field_agent.domain;

import learn.field_agent.data.MissionRepository;
import learn.field_agent.models.Mission;

import org.springframework.stereotype.Service;

@Service
public class MissionService {

    private final MissionRepository repository;

    public MissionService(MissionRepository repository) {
        this.repository = repository;
    }

    public Mission findById(int missionId) {
        return repository.findById(missionId);
    }

    public Result<Mission> add(Mission mission) {
        Result<Mission> result = validate(mission);
        if (!result.isSuccess()) {
            return result;
        }

        if (mission.getMissionId() != 0) {
            result.addMessage("missionId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        mission = repository.add(mission);
        result.setPayload(mission);
        return result;
    }

    public Result<Mission> update(Mission mission) {
        Result<Mission> result = validate(mission);
        if (!result.isSuccess()) {
            return result;
        }

        if (mission.getMissionId() <= 0) {
            result.addMessage("missionId must be set for `update` operation", ResultType.INVALID);
            return result;
        }


        if (!repository.update(mission)) {
            String msg = String.format("missionId: %s, not found", mission.getMissionId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int missionId) {
        return repository.deleteById(missionId);
    }

    private Result<Mission> validate(Mission mission) {
        Result<Mission> result = new Result<>();

        if (mission == null) {
            result.addMessage("mission cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(mission.getCodeName())) {
            result.addMessage("code name is required", ResultType.INVALID);
        }

        if (mission.getStartDate() == null) {
            result.addMessage("Start date is required", ResultType.INVALID);
        }

        if (mission.getProjectedEndDate() == null) {
            result.addMessage("Projected end date is required", ResultType.INVALID);
        }

        if (mission.getOperationalCost() < 0) {
            result.addMessage("Operational cost is required", ResultType.INVALID);
        }

        if (mission.getAgencyId() <= 0) {
            result.addMessage("AgencyId is required", ResultType.INVALID);
        }

        return result;

    }
}
