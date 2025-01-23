package nowicki.piotr.spring_boot_docker.mapper;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.model.Group;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupMapper {

    private UserMapper userMapper;
    public Group toGroup(GroupDto dto){
        var group = new Group();
        group.name = dto.name();
        group.photoUrl = dto.photoUrl();
        return group;
    }

    public GroupDto toGroupDto(Group group){
        return new GroupDto(group.name, group.photoUrl);
    }
}
