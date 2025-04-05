package PM_Tool.Web_Prj.Controller;


import PM_Tool.Web_Prj.Service.DashboardService;
import PM_Tool.Web_Prj.payload.DashboardProjectOverviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;


    @GetMapping("/project/{projectId}")
    public ResponseEntity<DashboardProjectOverviewResponse> getProjectOverview(@PathVariable String projectId) {
        return ResponseEntity.ok(dashboardService.getProjectOverview(projectId));
    }
}