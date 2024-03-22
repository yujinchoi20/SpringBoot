package hello.shop.web.Admin;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminLoginForm {

    @NotEmpty(message = "관리자 아이디는 필수 입니다.")
    private String adminUserId;

    @NotEmpty(message = "관리자 비밀번호는 필수 입니다.")
    private String password;
}
