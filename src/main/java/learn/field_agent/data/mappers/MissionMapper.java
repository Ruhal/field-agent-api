package learn.field_agent.data.mappers;

import learn.field_agent.models.Location;
import learn.field_agent.models.Mission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MissionMapper implements RowMapper<Mission> {

    @Override
    public Mission mapRow(ResultSet resultSet, int i) throws SQLException {
        Mission mission = new Mission();
        mission.setMissionId(resultSet.getInt("missionId"));
        mission.setCodeName(resultSet.getString("codeName"));
        mission.setNotes(resultSet.getString("notes"));
        mission.setStartDate(resultSet.getDate("startDate").toLocalDate());
        mission.setProjectedEndDate(resultSet.getDate("projectedEndDate").toLocalDate());
        if (resultSet.getDate("actualEndDate") != null) {
            mission.setActualEndDate(resultSet.getDate("actualEndDate").toLocalDate());
        }
        mission.setOperationalCost(resultSet.getDouble("operationalCost"));
        mission.setAgencyId(resultSet.getInt("agency_id"));
        return mission;
    }
}
