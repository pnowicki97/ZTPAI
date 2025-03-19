package nowicki.piotr.spring_boot_docker.service;

import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.ExpenseDto;
import nowicki.piotr.spring_boot_docker.mapper.DutyMapper;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.model.Expense;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.repository.DutyRepository;
import nowicki.piotr.spring_boot_docker.repository.GroupRepository;
import nowicki.piotr.spring_boot_docker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DutyService {
    private final DutyRepository dutyRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final DutyMapper dutyMapper;

    @Autowired
    public DutyService(DutyRepository dutyRepository, UserRepository userRepository, GroupRepository groupRepository, DutyMapper dutyMapper) {
        this.dutyRepository = dutyRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.dutyMapper = dutyMapper;
    }

    public DutyDto saveDuty(DutyDto dto){
        Duty duty = dutyMapper.toDuty(dto);
        dutyRepository.save(duty);
        return dto;
    }
    public DutyDto saveDuty(DutyDto dto, String userId, String groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Duty duty = dutyMapper.toDuty(dto);
        duty.setUser(user);
        duty.setGroup(group);
        dutyRepository.save(duty);

        return dto;
    }

    public List<DutyDto> findAllDuties(){
        return dutyRepository.findAll().stream().map(dutyMapper::toDutyDto).collect(Collectors.toList());
    }
    public List<DutyDto> findAllByUserId(String userId){
        return dutyRepository.findByUser_Id(userId).stream().map(dutyMapper::toDutyDto).collect(Collectors.toList());
    }
    public List<DutyDto> findAllByGroupId(String groupId){
        return dutyRepository.findByGroup_Id(groupId).stream().map(dutyMapper::toDutyDto).collect(Collectors.toList());
    }
    public DutyDto findById(String id){
        return dutyRepository.findById(id).map(dutyMapper::toDutyDto).orElse(null);
    }
    public void deleteById(String id){
        dutyRepository.deleteById(id);
    }
}
