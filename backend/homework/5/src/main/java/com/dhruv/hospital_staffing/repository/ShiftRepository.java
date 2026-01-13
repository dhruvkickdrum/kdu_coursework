package com.dhruv.hospital_staffing.repository;


import com.dhruv.hospital_staffing.model.Shift;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ShiftRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Shift> MAPPER = (ResultSet rs, int rowNum) -> Shift.builder()
            .id(UUID.fromString(rs.getString("id")))
            .tenantId(UUID.fromString(rs.getString("tenant_id")))
            .shiftTypeId(UUID.fromString(rs.getString("shift_type_id")))
            .dateStart(rs.getObject("date_start", java.time.LocalDate.class))
            .dateEnd(rs.getObject("date_end", java.time.LocalDate.class))
            .timeStart(rs.getObject("time_start", java.time.LocalTime.class))
            .timeEnd(rs.getObject("time_end", java.time.LocalTime.class))
            .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class))
            .updatedAt(rs.getObject("updated_at", java.time.OffsetDateTime.class))
            .build();

    public UUID insert(Shift shift) {
        String sql = """
      INSERT INTO shift (tenant_id, shift_type_id, date_start, date_end, time_start, time_end)
      VALUES (?, ?, ?, ?, ?, ?)
      RETURNING id
      """;
        return jdbcTemplate.queryForObject(sql, UUID.class,
                shift.getTenantId(), shift.getShiftTypeId(),
                shift.getDateStart(), shift.getDateEnd(),
                shift.getTimeStart(), shift.getTimeEnd());
    }

    public List<Shift> findAll(UUID tenantId) {
        String sql = "SELECT * FROM shift WHERE tenant_id = ? ORDER BY date_start DESC, time_start DESC";
        return jdbcTemplate.query(sql, MAPPER, tenantId);
    }

    public int[][] batchInsert(UUID tenantId, List<Shift> shifts) {
        String sql = """
      INSERT INTO shift (tenant_id, shift_type_id, date_start, date_end, time_start, time_end)
      VALUES (?, ?, ?, ?, ?, ?)
      """;
        return jdbcTemplate.batchUpdate(sql, shifts, 200, (ps, s) -> {
            ps.setObject(1, tenantId);
            ps.setObject(2, s.getShiftTypeId());
            ps.setObject(3, s.getDateStart());
            ps.setObject(4, s.getDateEnd());
            ps.setObject(5, s.getTimeStart());
            ps.setObject(6, s.getTimeEnd());
        });
    }

    public UUID findIdByNaturalKey(UUID tenantId, UUID shiftTypeId,
                                   java.time.LocalDate dateStart, java.time.LocalTime timeStart) {
        String sql = """
      SELECT id FROM shift
      WHERE tenant_id = ? AND shift_type_id = ? AND date_start = ? AND time_start = ?
      """;
        return jdbcTemplate.queryForObject(sql, UUID.class, tenantId, shiftTypeId, dateStart, timeStart);
    }
}

