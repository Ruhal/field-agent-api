package learn.field_agent.data;

import learn.field_agent.models.Mission;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.transaction.annotation.Transactional;



public interface MissionRepository {

    Mission findById(int missionId);

    Mission add(Mission mission);

    //JdbcTemplate getJdbcTemplate();

    boolean update(Mission mission);

    @Transactional
    boolean deleteById(int missionId);

    JdbcTemplate getJdbcTemplate();

}
