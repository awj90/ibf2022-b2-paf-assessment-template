package ibf2022.paf.assessment.server.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.server.exceptions.InsertNotSuccessfulException;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7
@Service
public class TodoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Transactional
    public List<Task> upsertTask(List<Task> tasks, String username) throws InsertNotSuccessfulException {

        String userId = null;

        Optional<User> optUser = userRepository.findUserByUsername(username);
        if (optUser.isEmpty()) {
            // User does not exist, create user first
            User user = new User();
            user.setUsername(username);
            userId = userRepository.insertUser(user);
            user.setUserId(userId);
        } else {
            // User exists, get his/her unique identifier
            userId = optUser.get().getUserId();
        }

        // Insert tasks
        List<Task> insertedTasks = new LinkedList<>();
        for (Task task : tasks) {
            Task insertedTask = taskRepository.insertTask(task, userId);
            insertedTasks.add(insertedTask);
        }
        return insertedTasks;
    }

}
