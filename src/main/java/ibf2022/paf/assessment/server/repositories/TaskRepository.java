package ibf2022.paf.assessment.server.repositories;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.exceptions.InsertNotSuccessfulException;
import ibf2022.paf.assessment.server.models.Task;

// TODO: Task 6
@Repository
public class TaskRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Task insertTask(Task task, String userId) throws InsertNotSuccessfulException {
        
        task.setUserId(userId);

        String query = "insert into task (description, priority, due_date, user_id) VALUES (?, ?, ?, ?)";
        
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, task.getDescription());
                statement.setInt(2, task.getPriority());
                statement.setDate(3, new java.sql.Date(task.getDueDate()));
                statement.setString(4, userId);
                return statement;
            }, keyHolder);
            
            BigInteger primaryKey = (BigInteger) keyHolder.getKey();
            task.setTaskId(primaryKey.intValue());
            
            return task;
            
        } catch (Exception ex) {
            throw new InsertNotSuccessfulException("Error adding tasks to database!");
        }
    }

}
