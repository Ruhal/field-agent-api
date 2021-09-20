package learn.field_agent.domain;


import learn.field_agent.data.AliasRepository;

import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;

    public AliasService(AliasRepository repository) {
        this.repository = repository;
    }

    public List<Alias> findByAgentId(int agentId) {
        return this.repository.findByAgentId(agentId);
    }

    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("aliasId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        Integer value1 = repository.getJdbcTemplate().queryForObject(
                "select count(*) from agent where agent_id = ?", Integer.class, alias.getAgentId());
        if (value1 != null && !(value1 > 0)) {
            result.addMessage("Agent ID not found. Need a valid agent ID to add an alias.", ResultType.INVALID);
            return result;
        }

        // require persona when the name is duplicate for the give agentId
        if (Validations.isNullOrBlank(alias.getPersona())) {
            Integer value = repository.getJdbcTemplate().queryForObject(
                    "select count(*) from alias where name = ? and agent_id = ?", Integer.class, alias.getName(),alias.getAgentId());
            if (value != null && value > 0) {
                result.addMessage("Persona is required for duplicate name. The persona differentiates between duplicate names.", ResultType.INVALID);
                return result;
            }
        }

        alias = repository.add(alias);
        result.setPayload(alias);
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("aliasId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            String msg = String.format("aliasId: %s, not found", alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int aliasId) {
        return repository.deleteById(aliasId);
    }


    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (alias == null) {
            result.addMessage("alias cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (alias.getAgentId() <= 0) {
            result.addMessage("agentId must be set and must be more than 0", ResultType.INVALID);
        }

        return result;

    }

}
