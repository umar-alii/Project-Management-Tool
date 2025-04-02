package PM_Tool.Web_Prj.Repository;


import PM_Tool.Web_Prj.Model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TeamRepository extends MongoRepository<Team, String> {
    List<Team> findByOwnerId(String ownerId);
    List<Team> findByMembersUserId(String userId);
}