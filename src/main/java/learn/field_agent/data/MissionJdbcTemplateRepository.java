package learn.field_agent.data;

import learn.field_agent.data.mappers.MissionMapper;
import learn.field_agent.models.Mission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class MissionJdbcTemplateRepository implements  MissionRepository {

    private final JdbcTemplate jdbcTemplate;

    public MissionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mission findById(int missionId) {

        final String sql = "select mission_id missionId, code_name codeName, notes, start_date startDate, projected_end_date projectedEndDate, " +
                "actual_end_date actualEndDate, operational_cost operationalCost, agency_id "
                + "from mission "
                + "where mission_id = ?;";

        return jdbcTemplate.query(sql, new MissionMapper(), missionId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public Mission add(Mission mission) {

        final String sql = "insert into mission (code_name, notes, start_date, projected_end_date, " +
                "actual_end_date, operational_cost, agency_id)"
                + "values (?,?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, mission.getCodeName());
            ps.setString(2, mission.getNotes());
            ps.setDate(3, mission.getStartDate() == null ? null : Date.valueOf(mission.getStartDate()));
            ps.setDate(4, mission.getProjectedEndDate() == null ? null : Date.valueOf(mission.getProjectedEndDate()));
            ps.setDate(5, mission.getActualEndDate() == null ? null : Date.valueOf(mission.getActualEndDate()));
            ps.setDouble(6, mission.getOperationalCost());
            ps.setInt(7, mission.getAgencyId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        mission.setMissionId(keyHolder.getKey().intValue());
        return mission;
    }

    @Override
    public boolean update(Mission mission) {

        // don't allow security_clearance_id updates for now
        final String sql = "update mission set "
                + "code_name = ?, "
                + "notes = ?, "
                + "start_date = ?, "
                + "projected_end_date = ?, "
                + "actual_end_date = ?, "
                + "operational_cost = ?, "
                + "agency_id = ? "
                + "where mission_id = ? ;";

        return jdbcTemplate.update(sql,
                mission.getCodeName(), mission.getNotes(), mission.getStartDate(), mission.getProjectedEndDate(),
                mission.getActualEndDate(), mission.getOperationalCost(), mission.getAgencyId(),mission.getMissionId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int missionId) {
        return jdbcTemplate.update("delete from mission where mission_id = ?;", missionId) > 0;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }


}
