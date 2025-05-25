package nowicki.piotr.spring_boot_docker.mapper;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.DutyExportDto;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DutyMapper {

    public final UserMapper userMapper;
    public final UserService userService;
    public Duty toDuty(DutyDto dto){
        Duty duty = new Duty();
        duty.name = dto.name();
        duty.setUser(dto.user());
        duty.setValue(dto.value());
        duty.setPhoto_url(dto.photoUrl());
        duty.setBeginDate(dto.beginDate());
        duty.setEndDate(dto.endDate());
        return duty;
    }
    public DutyDto toDutyDto(Duty duty){
        return new DutyDto(duty.getName(), duty.getValue(), duty.getUser(), duty.getPhoto_url(),duty.getBeginDate(),duty.getEndDate());
    }

    public DutyDto toDutyDto(DutyExportDto dutyExportDto){
        return new DutyDto(dutyExportDto.getName(), dutyExportDto.getValue(), userMapper.toUser(userService.findById(dutyExportDto.getUserId())), dutyExportDto.getPhotoUrl(), dutyExportDto.getBeginDate(), dutyExportDto.getEndDate());
    }

    public DutyExportDto toDutyExportDto(DutyDto dutyDto){
        DutyExportDto dutyExportDto = new DutyExportDto();
        dutyExportDto.setName(dutyDto.name());
        dutyExportDto.setUserId(dutyDto.user().getId());
        dutyExportDto.setValue(dutyDto.value());
        dutyExportDto.setBeginDate(dutyDto.beginDate());
        dutyExportDto.setEndDate(dutyDto.endDate());
        return dutyExportDto;
    }

}
