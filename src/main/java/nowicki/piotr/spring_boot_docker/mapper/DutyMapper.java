package nowicki.piotr.spring_boot_docker.mapper;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.model.Duty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DutyMapper {
    public Duty toDuty(DutyDto dto){
        Duty duty = new Duty();
        duty.name = dto.name();
        duty.setUser(dto.user());
        duty.setValue(dto.value());
        return duty;
    }
    public DutyDto toDutyDto(Duty duty){
        return new DutyDto(duty.getName(), duty.getValue(), duty.getUser());
    }
}
