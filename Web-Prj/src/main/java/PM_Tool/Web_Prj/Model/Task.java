package PM_Tool.Web_Prj.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;

    private String title;
    private String description;
    private String epicId;
    private String projectId;
    private String status; // TODO, IN_PROGRESS, DONE
    private List<String> assigneeIds;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}