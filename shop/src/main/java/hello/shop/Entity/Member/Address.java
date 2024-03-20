package hello.shop.Entity.Member;

import jakarta.persistence.Embeddable;
import lombok.Getter;

//임베디드 타입
@Embeddable
@Getter
public class Address {

    /**
     * 값 타입은 변경 불가능하게 설계해야 한다. @Setter를 제거사고 생성자에 값을 모두 초기화해서 변경 불가능한 클래스를 만들어야 한다.
     * JPA 스펙상 임베디드 타입은 기본 생성자를 public 또는 protected로 설정해야 한다.
     * public으로 두는 것 보다 protected로 설정하는 것이 더 안전하다.
     */
    private String city;
    private String street;
    private String zipcode;

    protected Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
