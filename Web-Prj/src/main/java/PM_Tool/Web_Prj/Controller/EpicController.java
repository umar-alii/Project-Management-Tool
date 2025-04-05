package PM_Tool.Web_Prj.Controller;


import PM_Tool.Web_Prj.Service.EpicService;
import PM_Tool.Web_Prj.payload.EpicRequest;
import PM_Tool.Web_Prj.payload.EpicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epics")
@RequiredArgsConstructor
public class EpicController {

    private final EpicService epicService;

    @PostMapping
    public ResponseEntity<EpicResponse> createEpic(@RequestBody EpicRequest request) {
        return ResponseEntity.ok(epicService.createEpic(request));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<EpicResponse>> getEpicsByProject(@PathVariable String projectId) {
        return ResponseEntity.ok(epicService.getEpicsByProject(projectId));
    }

    @GetMapping("/{epicId}")
    public ResponseEntity<EpicResponse> getEpic(@PathVariable String epicId) {
        return ResponseEntity.ok(epicService.getEpic(epicId));
    }

    @PutMapping("/{epicId}")
    public ResponseEntity<EpicResponse> updateEpic(@PathVariable String epicId, @RequestBody EpicRequest request) {
        return ResponseEntity.ok(epicService.updateEpic(epicId, request));
    }

    @DeleteMapping("/{epicId}")
    public ResponseEntity<Void> deleteEpic(@PathVariable String epicId) {
        epicService.deleteEpic(epicId);
        return ResponseEntity.noContent().build();
    }
}