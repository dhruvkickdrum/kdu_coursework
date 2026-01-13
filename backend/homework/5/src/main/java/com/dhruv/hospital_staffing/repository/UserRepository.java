package com.dhruv.hospital_staffing.repository;

import com.dhruv.hospital_staffing.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> USER_ROW_MAPPER = (ResultSet rs, int rowNum) -> User.builder()
            .id(UUID.fromString(rs.getString("id")))
            .tenantId(UUID.fromString(rs.getString("tenant_id")))
            .username(rs.getString("username"))
            .loggedIn(rs.getBoolean("logged_in"))
            .timezone(rs.getString("timezone"))
            .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class))
            .updatedAt(rs.getObject("updated_at", java.time.OffsetDateTime.class))
            .build();

    public UUID insert(UUID tenantId, String username, boolean loggedIn, String timezone) {
        String sql = """
                INSERT INTO users (tenant_id, username, logged_in, timezone)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;
        return jdbcTemplate.queryForObject(sql, UUID.class, tenantId, username, loggedIn, timezone);
    }

    public int update(UUID tenantId, UUID userId, String username, Boolean loggedIn, String timezone) {
        String sql = """
                UPDATE users
                SET
                    username = COALESCE(?, username),
                    logged_in  = COALESCE(?, logged_in),
                    timezone   = COALESCE(?, timezone),
                    updated_at = NOW()
                WHERE tenant_id = ? AND id = ?
                """;
        return jdbcTemplate.update(sql, username, loggedIn, timezone, tenantId, userId);
    }

    public Optional<User> findById(UUID tenantId, UUID userId) {
        String sql = "SELECT * FROM users WHERE tenant_id = ? AND id = ?";
        List<User> list = jdbcTemplate.query(sql, USER_ROW_MAPPER, tenantId, userId);
        return list.stream().findFirst();
    }

    public List<User> findAll(UUID tenantId) {
        String sql = "SELECT * FROM users WHERE tenant_id = ? ORDER BY username ASC";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER, tenantId);
    }

    public long count(UUID tenantId) {
        Long c = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE tenant_id = ?", Long.class, tenantId);
        return c == null ? 0 : c;
    }

    public List<User> findPage(UUID tenantId, int size, int offset, String sortDir) {
        String sql = """
          SELECT * FROM users
          WHERE tenant_id = ?
          ORDER BY username %s
          LIMIT ? OFFSET ?
      """.formatted(sortDir);
        return jdbcTemplate.query(sql, USER_ROW_MAPPER, tenantId, size, offset);
    }

    public UUID findIdByUsername(UUID tenantId, String username) {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM users WHERE tenant_id = ? AND username = ?",
                UUID.class, tenantId, username
        );
    }

    public int[][] batchInsert(UUID tenantId, List<User> users) {
        String sql = "INSERT INTO users (tenant_id, username, logged_in, timezone) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.batchUpdate(sql, users, 200, (ps, u) -> {
            ps.setObject(1, tenantId);
            ps.setObject(2, u.getUsername());
            ps.setObject(3, u.isLoggedIn());
            ps.setObject(4, u.getTimezone());
        });
    }

}
