package nowicki.piotr.spring_boot_docker.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.*;
import nowicki.piotr.spring_boot_docker.mapper.DutyMapper;
import nowicki.piotr.spring_boot_docker.mapper.ExpenseMapper;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.repository.*;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CSVExportService {

    private final DutyService dutyService;
    private final EventService eventService;
    private final ExpenseService expenseService;
    private final DutyMapper dutyMapper;
    private final ExpenseMapper expenseMapper;

    public void exportDutiesToCsv(HttpServletResponse response, String groupId) throws IOException {

        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=duties_" + System.currentTimeMillis() + ".csv";
        response.setHeader(headerKey, headerValue);

        List<DutyDto> duties = dutyService.findAllByGroupId(groupId);

        List<DutyExportDto> exportDuties = new ArrayList<>();

        for (DutyDto duty : duties)
            exportDuties.add(dutyMapper.toDutyExportDto(duty));

        try (PrintWriter writer = response.getWriter()) {

            StatefulBeanToCsv<DutyExportDto> beanToCsv = new StatefulBeanToCsvBuilder<DutyExportDto>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withOrderedResults(true)
                    .build();

            beanToCsv.write(exportDuties);

        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new IOException("Error during CSV export: " + e.getMessage(), e);
        }
    }

    public void exportEventsToCsv(HttpServletResponse response, String groupId) throws IOException {

        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=events_" + System.currentTimeMillis() + ".csv";
        response.setHeader(headerKey, headerValue);

        List<EventDto> events = eventService.findAllByGroupId(groupId);

        try (PrintWriter writer = response.getWriter()) {

            StatefulBeanToCsv<EventDto> beanToCsv = new StatefulBeanToCsvBuilder<EventDto>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withOrderedResults(true)
                    .build();

            beanToCsv.write(events);

        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new IOException("Error during CSV export: " + e.getMessage(), e);
        }
    }

    public void exportExpensesToCsv(HttpServletResponse response, String groupId) throws IOException {

        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=expenses_" + System.currentTimeMillis() + ".csv";
        response.setHeader(headerKey, headerValue);

        List<ExpenseDto> expenses = expenseService.findAllByGroupId(groupId);

        List<ExpenseExportDto> exportExpenses = new ArrayList<>();

        for (ExpenseDto expense : expenses)
            exportExpenses.add(expenseMapper.toExpenseExportDto(expense));

        try (PrintWriter writer = response.getWriter()) {

            StatefulBeanToCsv<ExpenseExportDto> beanToCsv = new StatefulBeanToCsvBuilder<ExpenseExportDto>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withOrderedResults(true)
                    .build();

            beanToCsv.write(exportExpenses);

        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new IOException("Error during CSV export: " + e.getMessage(), e);
        }
    }

}
