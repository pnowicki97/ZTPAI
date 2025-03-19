package nowicki.piotr.spring_boot_docker.controller;

import jakarta.validation.Valid;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.ExpenseDto;
import nowicki.piotr.spring_boot_docker.service.DutyService;
import nowicki.piotr.spring_boot_docker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/duty")
public class DutyController {
    private final DutyService dutyService;

    @Autowired
    public DutyController(DutyService dutyService) {
        this.dutyService = dutyService;
    }

    @GetMapping
    public List<DutyDto> findAllDuties(){
        return dutyService.findAllDuties();
    }
    @PostMapping
    public DutyDto saveDuty(@RequestBody @Valid DutyDto dto) {
        return dutyService.saveDuty(dto);
    }
    @GetMapping("/{Duty-id}")
    public DutyDto findDutyById(@PathVariable("duty-id") String id){
        return dutyService.findById(id);
    }
    @DeleteMapping("/{duty-id}")
    public void deleteDuty(@PathVariable("duty-id") String id){
        dutyService.deleteById(id);
    }
}
