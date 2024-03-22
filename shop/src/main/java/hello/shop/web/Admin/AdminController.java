package hello.shop.web.Admin;

import hello.shop.Sevice.Admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

}
