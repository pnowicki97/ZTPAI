package nowicki.piotr.spring_boot_docker.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.service.CSVExportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("/export")
public class CSVExportController {

    private final CSVExportService csvExportService;

    @GetMapping("/duties")
    public void exportDuties(HttpServletResponse response, @RequestParam("selectedGroup") String groupId) throws IOException {
        csvExportService.exportDutiesToCsv(response, groupId);
    }

    @GetMapping("/events")
    public void exportEvents(HttpServletResponse response, @RequestParam("selectedGroup") String groupId) throws IOException {
        csvExportService.exportEventsToCsv(response, groupId);
    }

    @GetMapping("/expenses")
    public void exportExpenses(HttpServletResponse response, @RequestParam("selectedGroup") String groupId) throws IOException {
        csvExportService.exportExpensesToCsv(response, groupId);
    }
}
