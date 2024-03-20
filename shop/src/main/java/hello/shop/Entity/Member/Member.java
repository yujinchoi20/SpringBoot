package hello.shop.Entity.Member;

import hello.shop.Entity.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String userId;
    private String password;

    //Address - 임베디드 타입
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //일대다 연관관계 매핑
    private List<Order> orders = new ArrayList<>();
}
