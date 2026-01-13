package com.dhruv.hospital_staffing.repository;

import com.dhruv.hospital_staffing.model.ShiftType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor

public class ShiftTypeRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ShiftType> MAPPER = (ResultSet rs, int rowNum) -> ShiftType.builder()
            .id(UUID.fromString(rs.getString("id")))
            .tenantId(UUID.fromString(rs.getString("tenant_id")))
            .name(rs.getString("name"))
            .description(rs.getString("description"))
            .active(rs.getBoolean("active"))
            .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class))
            .updatedAt(rs.getObject("updated_at", java.time.OffsetDateTime.class))
            .build();

    public UUID insert(UUID tenantId, String name, String description, boolean active) {
        String sql = """
      INSERT INTO shift_type (tenant_id, name, description, active)
      VALUES (?, ?, ?, ?)
      RETURNING id
      """;
        return jdbcTemplate.queryForObject(sql, UUID.class, tenantId, name, description, active);
    }

    public List<ShiftType> findAll(UUID tenantId) {
        return jdbcTemplate.query("SELECT * FROM shift_type WHERE tenant_id = ? ORDER BY name ASC", MAPPER, tenantId);
    }

    public UUID findIdByName(UUID tenantId, String name) {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM shift_type WHERE tenant_id = ? AND name = ?",
                UUID.class, tenantId, name
        );
    }

    public int[][] batchInsert(UUID tenantId, List<ShiftType> types) {
        String sql = """
        INSERT INTO shift_type (tenant_id, name, description, active) VALUES (?, ?, ?, ?)
        """;
        return jdbcTemplate.batchUpdate(
                sql,
                types,
                200,
                (ps, st) -> {
            ps.setObject(1, tenantId);
            ps.setString(2, st.getName());
            ps.setString(3, st.getDescription());
            ps.setBoolean(4, st.isActive());
        });
    }
}
