package nowicki.piotr.spring_boot_docker.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyExportDto;
import nowicki.piotr.spring_boot_docker.dto.EventDto;
import nowicki.piotr.spring_boot_docker.dto.ExpenseExportDto;
import nowicki.piotr.spring_boot_docker.mapper.DutyMapper;
import nowicki.piotr.spring_boot_docker.mapper.ExpenseMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CSVImportService {

    private final DutyService dutyService;
    private final EventService eventService;
    private final ExpenseService expenseService;

    private final DutyMapper dutyMapper;
    private final ExpenseMapper expenseMapper;

    public void importDutiesFromCsv(MultipartFile file, String groupId){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        List<DutyExportDto> exportDuties;
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<DutyExportDto> csvToBean = new CsvToBeanBuilder<DutyExportDto>(reader)
                    .withType(DutyExportDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            exportDuties = csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(DutyExportDto dutyExportDto : exportDuties){
            dutyService.saveDuty(dutyMapper.toDutyDto(dutyExportDto),dutyExportDto.getUserId(),groupId,dutyExportDto.getPhotoUrl());
        }
    }

    public void importExpensesFromCsv(MultipartFile file, String groupId){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        List<ExpenseExportDto> exportExpenses;
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ExpenseExportDto> csvToBean = new CsvToBeanBuilder<ExpenseExportDto>(reader)
                    .withType(ExpenseExportDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            exportExpenses = csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(ExpenseExportDto expenseExportDto : exportExpenses){
            expenseService.saveExpense(expenseMapper.toExpenseDto(expenseExportDto),expenseExportDto.getPaidById(),groupId, List.of(expenseExportDto.getUsersIds().split(",")),expenseExportDto.getPhotoUrl());
        }
    }

    public void importEventsFromCsv(MultipartFile file, String groupId){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        List<EventDto> exportEvents;
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<EventDto> csvToBean = new CsvToBeanBuilder<EventDto>(reader)
                    .withType(EventDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            exportEvents = csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(EventDto eventDto : exportEvents){
            eventService.saveEvent(eventDto);
        }
    }
}
