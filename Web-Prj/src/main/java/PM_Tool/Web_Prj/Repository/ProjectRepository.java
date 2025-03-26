package PM_Tool.Web_Prj.Repository;


import PM_Tool.Web_Prj.Model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByOwnerId(String ownerId);
    Optional<Project> findByIdAndOwnerId(String id, String ownerId);
}