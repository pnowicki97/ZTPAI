package nowicki.piotr.spring_boot_docker.mapper;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.EventDto;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.model.Event;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventMapper {
    public Event toEvent(EventDto dto){
        Event event = new Event();
        event.name = dto.title();
        event.setBeginDate(dto.start());
        event.setEndDate(dto.end());
        return event;
    }
    public EventDto toEventDto(Event event){
        return new EventDto(event.getName(), event.getBeginDate(), event.getEndDate());
    }
}
