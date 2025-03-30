package nowicki.piotr.spring_boot_docker.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;

@Getter
@Setter
@RequiredArgsConstructor
public class Balance {
    private UserResponseDto lender;
    private UserResponseDto debtor;
    private Double amount;

    public Balance(UserResponseDto id, Double suma) {
        this.lender = id;
        this.amount = suma;
    }
    public Balance(UserResponseDto lender, Double suma, UserResponseDto debtor) {
        this.lender = lender;
        this.amount = suma;
        this.debtor = debtor;
    }
}
