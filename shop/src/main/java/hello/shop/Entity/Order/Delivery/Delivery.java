package hello.shop.Entity.Order.Delivery;

import hello.shop.Entity.Member.Address;
import hello.shop.Entity.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //order - delivery 연관관계, delivery 입장에서 order 정보만 알면됨.
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    //Address - 임베디드 타입
    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
