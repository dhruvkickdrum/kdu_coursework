package com.dhruv.hospital_staffing.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ShiftUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UUID insert(UUID tenantId, UUID shiftId, UUID userId) {
        String sql = """
      INSERT INTO shift_user (tenant_id, shift_id, user_id)
      VALUES (?, ?, ?)
      RETURNING id
      """;
        return jdbcTemplate.queryForObject(sql, UUID.class, tenantId, shiftId, userId);
    }

    public int[][] batchInsert(UUID tenantId, List<Object[]> rows) {
        String sql = "INSERT INTO shift_user (tenant_id, shift_id, user_id) VALUES (?, ?, ?)";
        return jdbcTemplate.batchUpdate(sql, rows, 200, (ps, row) -> {
            ps.setObject(1, tenantId);
            ps.setObject(2, row[0]);
            ps.setObject(3, row[1]);
        });
    }
}
