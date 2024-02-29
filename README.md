## Record
-----
#### 1. 게시판 웹 페이지 (board)
-----
#### 2. 회원가입 구현 (member)
  * 회원가입 구현 후 게시판 추가
-----
#### 3. 서점 웹 페이지 구현 (shop)

## 개발 과정

### 1. 엔티티 클래스 구현


__도메인(Entity) 목록__

* 회원(Member)
* 주문(Order)
* 배송(Delivery)
* 주문 상품(OrderItem)
* 상품(Item), 상속 -> 도서(Book), 음반(Album), 영화(Movie)
* 카테고리(Category)
* 카테고리_상품(CategoryItem)


__임베디드 타입__
* 주소(Address): 값 타입은 변경 불가능하게 설계함. @Setter를 제거하고 생성자에서 값을 모두 초기화해 변경 불가능한 클래스로 만듦.


__EnumType__
* 주문 상태(Order Status) - ORDER, CANCEL
* 배송 상태(Delivery Status) - READY, COMP
* EnumType은 꼭 String으로 설정, 기본 설정 값으로 두면 index로 Enum 값을 사용하기 때문에 Enum 값 추가시 혼란이 생길 수 있음.


__다대다 연관관계 주의 사항 및 수정 사항__
* 다대다 연관관계 매핑을 중간 테이블(CategoryItem)을 하나 생성하여 일대다 + 다대일 연관관계 매핑으로 변경함
* 만약 다대다 연관관계 매핑을 사용할 경우 연관관계 편의 메서드를 사용하다 무한 루프에 빠질 가능항이 매우 높음
* 따로 중간 테이블을 생성하면 컬럼을 추가할 수 있으며, 세밀하게 쿼리를 실행할 수 있음


__지연로딩으로 연관관계 설정__
* 즉시 로딩을 사용할 경우 SQL 문의 실행을 예측할 수 없고, JPQL을 실행할 때 N+1 문제가 자주 발생함


__컬렉션은 필드에서 초기화__
* 예시) private List<Item> items = new ArrayList<>(); //즉시 초기화 함
* null 값을 방지하기 위해 바로 초기화함

![image](https://github.com/yujinchoi20/SpringMVC-and-JPA/assets/105353163/882d6813-da2b-4155-bafe-ac601d1cd074)

https://www.erdcloud.com/d/PwNZ7b9zzwQKXfHyg



### 2. 구현 요구 사항

* 회원 기능
  * 회원 등록
  * 회원 조회
* 상품 기능
  * 상품 등록
  * 상품 수정
  * 상품 조회
* 주문 기능
  * 상품 주문
  * 주문 내역 조회
  * 주문 취소

![image](https://github.com/yujinchoi20/SpringMVC-and-JPA/assets/105353163/0cf66e48-6e18-46b4-a4b2-b6ac9935d900)

__Entity, Repository, Service 란?__
* Entity: @Entity, 데이터베이스의 구조를 만든다. 필드를 선언하고 JPA가 동적으로 테이블을 생성해준다.
* Repository: @Repository, 데이터베이스에 접근하기 위한 계층이다. 객체의 CRUD를 수행하는 메서드를 선언한다. JpaRepository를 상속받아 인터페이스로 사용하지 않고, 직접 클래스로 구현했다.
* Service: @Service, 비즈니스 로직을 구현하는 계층이다. 클라이언트의 요청을 처리하고 정보를 가공해 전달한다. 


1) 회원 기능(Member)
   * Entity: 회원 아이디(id, PK), 회원 이름(username), 회원 주소(Address), 회원 주문 정보(orders)
   * Repository: 회원 등록(save), 회원 조회(findOne, findAll, findByName)
   * Service: 회원 등록(join), 중복 회원 검증(validateDuplicateMember), 회원 조회(findOne, findMembers, findByName)
2) 상품 기능(Item)
   * Entity
     * 각각의 상품을 DTYPE으로 구분
     * 상속 관계의 클래스를 단일 테이블로 관리(한 번의 쿼리문으로 전체 상품을 조회할 수 있기 때문에 성능적으로 좋음)
     * 상품 아이디(id, PK), 상품 이름(name), 가격(price), 재고(stockQuantity), categoryItems(연관관계 중간 테이블)
     * 비즈니스 로직: addStock(주문 취소로 인한 재고 추가), removeStock(주문 접수로 인한 재고 감소)
   * Repository: 상품 등록(save), 상품 조회(findOne, findAll)
   * Service: 상품 등록(enroll), 상품 조회(findOne, findItems)
3) 주문 기능(Order)
   * Entity
      * 주문 아이디(id, PK), 주문자 정보(member), 주문 상품(orderItems), 배송(delivery), 주문 시간(orderDate), 주문 상태(status)
      * 연관관계 편의 메서드(api 구현시 연관관계를 맺어주는 역할): setMember, addOrderItem, setDelivery
      * 생성 편의 메서드: createOrder
      * 비즈니스 로직: cancel
      * 조회 로직: getTotalPrice
   * Repository: 상품 주문(save), 주문 내역 조회(findOne)
   * Service: 상품 주문(order), 주문 취소(cancelOrder)
