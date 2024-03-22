package hello.shop.Entity.Admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Admin {

    @Id @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    private String name;
    private String adminUserId;
    private String password;
}
