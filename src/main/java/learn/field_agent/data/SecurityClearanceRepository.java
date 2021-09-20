package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SecurityClearanceRepository {

    List<SecurityClearance> findAll();

    SecurityClearance findById(int securityClearanceId);

    SecurityClearance add(SecurityClearance securityClearance);

    JdbcTemplate getJdbcTemplate();

    boolean update(SecurityClearance securityClearance);

    @Transactional
    boolean deleteById(int securityClearanceId);
}
