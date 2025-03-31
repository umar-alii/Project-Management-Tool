package PM_Tool.Web_Prj.Repository;


import PM_Tool.Web_Prj.Model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByEpicId(String epicId);
    List<Task> findByProjectId(String projectId);
    List<Task> findByAssigneeIdsContaining(String assigneeId);
}