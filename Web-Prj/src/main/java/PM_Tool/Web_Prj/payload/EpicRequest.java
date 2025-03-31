package PM_Tool.Web_Prj.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpicRequest {
    private String name;
    private String description;
    private String projectId;
}