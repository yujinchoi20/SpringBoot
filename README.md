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

### 3. 웹 계층 개발

* 홈 화면 (HomeController)
* 회원 기능 (MemberController, MemberForm)
  * 회원 등록
  * 회원 조회
* 상품 기능 (ItemController, BookForm)
  * 상품 등록
  * 상품 조회
  * 상품 수정(변경 감지 기능 사용)
* 주문 기능 (OrderController)
  * 상품 주문
  * 주문 내역 조회
  * 주문 취소


#### __Spring MVC 패턴__

* 클라이언트가 요청한 정보(데이터)를 1차 캐시 혹은 DB에서 조회하여 __Model__ 객체에 담아 __View__ 화면으로 보냄
* __View__ 화면은 __Model__ 객체에 있는 정보(데이터)를 사용하여 클라이언트에게 보여줄 화면을 구성
* 이때 __Model__ 객체에 데이터를 담은 주체를 __Controller__ 라고 함


#### __변경 감지 기능__

* 정보 수정은 기존 정보를 조회(select)하여 수정(setter)하고 다시 저장(update)하는 방식으로 진행함
* 만약 정보를 수정하지 않았는데 update 쿼리문을 실행하면 의미가 있을까? 없다. 그래서 정보 수정이 없으면 update를 진행하지 않고, 정보 수정이 있을 때만 update를 진행하는 변경 감지 기능을 사용함
* 변경 감지 기능은 영속화 되어 있는 객체에 대해서만 동작한다. 준영속화 되어 있는 객체를 대상으로 변경 감지 기능을 적용하려하면 일반적인 update 쿼리문을 실행하는 것과 똑같음
* 때문에 객체가 영속화 되어 있는 시점에서 변경 감지 기능을 사용해야 함
* 비즈니스 로직에 update 메서드를 만들어 놓고 Controller 계층에서 호출해 사용함

#### __@GetMappint, @PostMapping__

@RequestMapping 어노테이션에 호출방식을 지정하기 위해 @GetMapping, @PostMapping을 사용함 

#### __css, js__

* css와 js는 Bootstrap에서 다운받아 사용함
* jumbotron-narrow.css 파일을 추가로 작성하여 뷰 화면을 정렬(줄맞춤)함

-------------------------------

## 개발/구현 예정

1. 상품 등록/수정 시 DTYPE 값을 통해서 해당 아이템이 어떤 객체인지 확인한 후, 객체에 맞는 값을 설정할 수 있도록 수정(기존 코드는 Book 객체의 등록/수정만 다루고 있음)
2. 회원 주문 내역 조회시 주문한 상품의 총 가격을 보여주는 로직 구현(기존 코드의 getTotalPrice() 메서드 사용 예정)
3. Spring 서버 여러 개를 실행하여 부하 테스트 진행 - nGrinder 성능 테스트 툴 사용 예정
4. 데이터베이스 변경: H2 DB -> MySQL

--------------------------------

## 추가 개발 사항

__03/06/2024__

* 상품 등록을 상품의 종류에 따라 구분
   * 기존 items/new: Book 상품 등록 폼 --> 변경: items/new: 상품 종류 선택 페이지(button 태그를 사용해 선택)
   * 추가 items/new/book, items/new/album, items/new/movie: 각 종류의 상품 등록 폼
   * 상품 목록 items: 상품 종류를 명시, itemList에 th 태그 추가(상품 종류), item.getClass().getSimpleName() 값 사용
   * 상품 수정도 상품 종류에 따라 구분함 -> itemController의 updateItemForm 메서드에 if 조건문을 사용하여 상품 종류를 판별하고, updateForm으로 이동
   * 상품 정보 수정 폼(기존 updateItemForm -> 변경 updateBookForm, updateAlbumForm, updateMovieForm)
   * 상품 정보 수정 로직 수정(itemService 계층에서 변경 감지 기능을 사용하여 상품 정보 수정)

![image](https://github.com/yujinchoi20/SpringMVC-and-JPA/assets/105353163/cc325982-b02c-4ec0-b66a-83c13df533de)

![image](https://github.com/yujinchoi20/SpringMVC-and-JPA/assets/105353163/8a8ed043-caa5-4842-9eb1-9deb7f3aa7a9)

__03/07/2024__

* 생성 편의 메서드 추가(현재는 setter 사용 횟수에 큰 차이가 없지만 궁극적으로 setter 사용 횟수를 줄이는 것을 목표로 함)
  * Book/Album/Movie: createBook/createAlbum/createMovie
  * BookForm/AlbumForm/MovieForm: createBookForm/createAlbumForm/createMovieForm

__03/08/2024__

* Item 객체에 type 필드를 하나 추가해 상품 목록을 상품 종류별로 검색할 수 있는 기능 추가중
  * @DiscriminatorValue으로 설정한 값을 통해 조회하려 했으나, JPQL에서는 dtype 키워드를 사용하지 못해 Item 객체에 type 필드 추가 
