package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface AliasRepository {
    List<Alias> findByAgentId(int agentId);

    Alias add(Alias alias);

    boolean update(Alias alias);

    boolean deleteById(int aliasId);

    JdbcTemplate getJdbcTemplate();

}
