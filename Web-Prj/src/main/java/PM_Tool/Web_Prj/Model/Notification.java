package PM_Tool.Web_Prj.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;

    private String userId;
    private String message;
    private String type; // TASK_ASSIGNMENT, PROJECT_INVITATION, DEADLINE_REMINDER
    private boolean read;
    private LocalDateTime createdAt;
}