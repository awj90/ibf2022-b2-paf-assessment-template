package ibf2022.paf.assessment.server.models;

// TODO: Task 4

public class Task {

    private Integer taskId;
    private String description;
    private Integer priority;
    private Long dueDate;
    private String userId;

    public Integer getTaskId() {
        return taskId;
    }
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public Long getDueDate() {
        return dueDate;
    }
    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "Task [taskId=" + taskId + ", description=" + description + ", priority=" + priority + ", dueDate="
                + dueDate + ", userId=" + userId + "]";
    }
    
}
