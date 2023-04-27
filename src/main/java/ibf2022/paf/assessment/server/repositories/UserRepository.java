package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.exceptions.InsertNotSuccessfulException;
import ibf2022.paf.assessment.server.models.User;

// TODO: Task 3
@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<User> findUserByUsername(String username) {
        String query = "select user_id, username, name from user where username = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(query, username);
        if (rs.next()) {
            User user = User.create(rs);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public String insertUser(User user) throws InsertNotSuccessfulException {
        String userId = UUID.randomUUID().toString().substring(0, 8);
        user.setUserId(userId);
        String username = user.getUsername();
        String query = "insert into user (user_id, username, name) VALUES (?, ?, ?)";
        int recordsInserted = jdbcTemplate.update(query, userId, username, user.getName());
        if (recordsInserted == 1) {
            return userId;
        } else {
            throw new InsertNotSuccessfulException("Error adding username %s to database!".formatted(username));
        }
    }
}
