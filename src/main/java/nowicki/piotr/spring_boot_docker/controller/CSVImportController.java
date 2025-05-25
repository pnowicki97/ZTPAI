package nowicki.piotr.spring_boot_docker.controller;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.service.CSVImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/import")
public class CSVImportController {
    private final CSVImportService csvImportService;

    @GetMapping("/duties")
    public String showImportDutiesForm(Model model, @RequestParam("selectedGroup") String groupId) {
        model.addAttribute("selectedGroup", groupId);
        return "import-duties-page";
    }

    @PostMapping("/duties")
    public String importDuties(@RequestParam("photoFile") MultipartFile file, @RequestParam("selectedGroup") String groupId, Model model, RedirectAttributes redirectAttributes) {
        try {
            csvImportService.importDutiesFromCsv(file, groupId);
        } catch (IllegalArgumentException e) {}
        return "redirect:/duties/dutiesOverview?selectedGroup=" + groupId;
    }

    @GetMapping("/expenses")
    public String showImportExpensesForm(Model model, @RequestParam("selectedGroup") String groupId) {
        model.addAttribute("selectedGroup", groupId);
        return "import-expenses-page";
    }

    @PostMapping("/expenses")
    public String importExpenses(@RequestParam("photoFile") MultipartFile file, @RequestParam("selectedGroup") String groupId, Model model, RedirectAttributes redirectAttributes) {
        try {
            csvImportService.importExpensesFromCsv(file, groupId);
        } catch (IllegalArgumentException e) {}
        return "redirect:/expenses/expensesOverview?selectedGroup=" + groupId;
    }

    @GetMapping("/events")
    public String showImportEventsForm(Model model, @RequestParam("selectedGroup") String groupId) {
        model.addAttribute("selectedGroup", groupId);
        return "import-events-page";
    }

    @PostMapping("/events")
    public String importEvents(@RequestParam("photoFile") MultipartFile file, @RequestParam("selectedGroup") String groupId, Model model, RedirectAttributes redirectAttributes) {
        try {
            csvImportService.importExpensesFromCsv(file, groupId);
        } catch (IllegalArgumentException e) {}
        return "redirect:/groups?selectedGroup=" + groupId;
    }

}
