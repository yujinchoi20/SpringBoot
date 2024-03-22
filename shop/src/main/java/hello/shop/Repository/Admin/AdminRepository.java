package hello.shop.Repository.Admin;

import hello.shop.Entity.Admin.Admin;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    /**
     * save
     * findOne
     */

    private final EntityManager em;

    public Long save(Admin admin) {
        em.persist(admin);
        return admin.getId();
    }

    public Admin findOnd(Long id) {
        return em.find(Admin.class, id);
    }

    public Admin findById(String adminUserId) {
        return em.createQuery("select a from Admin a where a.adminUserId =: adminUserId", Admin.class)
                .setParameter("adminUserId", adminUserId)
                .getSingleResult();
    }
}
