package PM_Tool.Web_Prj.Repository;


import PM_Tool.Web_Prj.Model.Epic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EpicRepository extends MongoRepository<Epic, String> {
    List<Epic> findByProjectId(String projectId);
}