package hello.shop.Sevice.Admin;

import hello.shop.Entity.Admin.Admin;
import hello.shop.Repository.Admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    /**
     * join
     * findAdmin
     */

    private final AdminRepository adminRepository;

    @Transactional
    public Long join(Admin admin) {
        adminRepository.save(admin);
        return admin.getId();
    }

    public Admin findAdmin(Long id) {
        return adminRepository.findOne(id);
    }

    public Admin findById(String adminId) {
        return adminRepository.findById(adminId);
    }
}
