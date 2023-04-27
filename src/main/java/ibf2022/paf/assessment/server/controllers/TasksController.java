package ibf2022.paf.assessment.server.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.exceptions.InsertNotSuccessfulException;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.services.TodoService;
import jakarta.servlet.http.HttpServletRequest;

// TODO: Task 4, Task 8

@Controller
public class TasksController {

    @Autowired
    TodoService todoService;

    // MAV
    @PostMapping(path="/task")
    public ModelAndView saveButtonHandler(@RequestBody MultiValueMap<String, String> form, HttpServletRequest req) throws ParseException {
        
        String username = form.getFirst("username");
        int numberOfTasks = (form.size() - 1)/3;
        
        List<Task> tasks = new LinkedList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            Task task = new Task();
            String description = form.getFirst("description-%d".formatted(i));
            Integer priority = Integer.parseInt(form.getFirst("priority-%d".formatted(i)));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(form.getFirst("dueDate-%d".formatted(i)));
            Long dueDate = date.getTime();
            task.setDescription(description);
            task.setPriority(priority);
            task.setDueDate(dueDate);
            tasks.add(task);
            // System.out.println(task.toString());
        }
        try {
            List<Task> insertedTasks = todoService.upsertTask(tasks, username);
            // System.out.println(insertedTasks.size());

            ModelAndView mav = new ModelAndView("result.html");
            mav.addObject("username", username);
            mav.addObject("taskCount", numberOfTasks);
            mav.setStatus(HttpStatus.OK);
            return mav;

        } catch (InsertNotSuccessfulException ex) {
            ModelAndView mav = new ModelAndView("error.html");
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return mav;
        }
    }

    // String
    
    // @PostMapping(path="/task")
    // public String saveButtonHandler(@RequestBody MultiValueMap<String, String> form, Model model) throws ParseException {
    //     String username = form.getFirst("username");
    //     int numberOfTasks = (form.size() - 1)/3;
    //     model.addAttribute("username", username);
    //     model.addAttribute("taskCount", numberOfTasks);
        
    //     List<Task> tasks = new LinkedList<>();
    //     for (int i = 0; i < numberOfTasks; i++) {
    //         Task task = new Task();
    //         String description = form.getFirst("description-%d".formatted(i));
    //         Integer priority = Integer.parseInt(form.getFirst("priority-%d".formatted(i)));
    //         DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //         Date date = formatter.parse(form.getFirst("dueDate-%d".formatted(i)));
    //         Long dueDate = date.getTime();
    //         task.setDescription(description);
    //         task.setPriority(priority);
    //         task.setDueDate(dueDate);
    //         tasks.add(task);
    //         System.out.println(task.toString());
    //     }
    //     try {
    //         List<Task> insertedTasks = todoService.upsertTask(tasks, username);
    //         System.out.println(insertedTasks.size());
    //         return "result";
    //     } catch (InsertNotSuccessfulException ex) {
    //         return "error";
    //     }
    // }
}
