package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final UserMapper ROW_MAPPER = new UserMapper();

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final SimpleJdbcInsert insertUserRole;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertUserRole = new SimpleJdbcInsert(dataSource)
                .withTableName("user_roles");
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            user.getRoles().stream().forEach(r -> {
                Map<String, Object> params = new HashMap<>();
                params.put("user_id", user.getId());
                params.put("role", r.name());
                insertUserRole.execute(params);
            });
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON r.user_id=u.id WHERE u.id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(ROW_MAPPER.getUsers());
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        jdbcTemplate.query("SELECT * FROM users u LEFT  JOIN user_roles r ON u.id=r.user_id WHERE email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(ROW_MAPPER.getUsers());
    }

    @Override
    public List<User> getAll() {
        jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON u.id=r.user_id ORDER BY name, email", ROW_MAPPER);
        return ROW_MAPPER.getUsers();
    }

    private static class UserMapper implements RowMapper<User> {
        private List<User> users = new ArrayList<>();
        private Map<Integer, User> alreadyFetchedUsers = new HashMap<>();

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            final User userFromRS = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);

            User user = alreadyFetchedUsers.computeIfAbsent(userFromRS.getId(), v -> {
                users.add(userFromRS);
                return userFromRS;
            });

            String role = rs.getString("role");
            if (role != null) {
                user.addRole(Role.valueOf(role));
            }

            return user;
        }

        public List<User> getUsers() {
            return users;
        }
    }
}
