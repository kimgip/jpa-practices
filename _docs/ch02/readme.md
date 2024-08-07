## Entity Mapping

#### 1. Entity Class: @Entity
1. 자바 클래스를 테이블과 매핑한다고 JPA에게 알려준다.
2. @Entity가 사용된 클래스를 엔티티 클래스라 한다.
3. 만약 DDL 자동 생성 기능을 켠 경우, @Table로 물리 테이블 이름을 지정하지 않으면 클래스 이름으로 테이블이 생성된다.
4. @Entity 적용 시 주의사항I: domain.Company
   - 기본생성자 반드시 존재
   - final 필드 사용 금지
   - @Id로 기본키로 매핑된 필드가 하나 반드시 존재
   - class만 가능(interface, enum, inner class 적용 불가)
5. @Entity 적용 시 주의사항II: domain.marketing.Member, domain.sales.Member
   - 다른 패키지의 같은 이름의 클래스에 @entity를 적용해야 하는 경우
   - name 속성을 사용해서 이름을 다르게 지정해 주어야 한다.
   - 이 때에는 클래스 이름이 아니라 속성 이름으로 물리 테이블과 매핑된다.  
6. [예제] ex01: DDL 자동 생성 


#### 2. Database Table: @Table
1. @Entity가 적용된 엔티티에 매핑될 물리 테이블을 지정한다.
2. 생략하면 엔티티 클래스 이름을 테이블 이름으로 자동 매핑한다.
3. name 속성으로 엔티티 클래스 이름과 다른 테이블을 매핑할 수 있다.
4. [예제] ex01: ex01.domain.sales.Invoice, DDL 자동 생성


#### 3. Primary Key Mapping: @Id, @GenerateValue
1. @Id를 적용한 기본키로 매핑된 필드가 엔티티에 반드시 있어야 한다.
2. @Id를 적용한 기본키로 매핑된 필드의 타입
   - 자바 기본 타입
   - 자바 래퍼 클래스 타입
   - String
   - util.Date, sql.Date, time.LocalDate
   - BigDecimal, BigInteger
3. 기본키 자동 생성 전략
   - 엔티티 객체의 기본키 값은 보통 애플리케이션이 직접 할당하지 않고 데이터베이스이가 생성해주는 값을 사용
   - @Id와 함께 @GenerateValue를 사용
   - @GenerateValue 속성 strategy에 IDENTITY, SEQUENCE, TABLE 전략을 각각 사용할 수 있다.
   - Vendor Provider(Hibernate) Configuration(hibernate.id.new_generator_mappings) 와 SpringBoot Hibernate 지원 설정 프로퍼티(spring.jpa.hibernate.use-new-id-generator-mappings)의 추가 지원 설정은 Deprecated 되었다.
4. [예제] ex02: IDENTITY 전략 적용(MariaDB, PostgreSQL, SQL Server, DB2)


#### 4. 필드와 컬럼 매핑: @Column, @Enumerated, @Temporal, @Lob, @Type, @Transient
1. [예제] ex03: 물리 Database의 Table로 부터 JPA Entity로 매핑하기
2. jpa.properties.hibernate.hbm2ddl.auto = validate 켜기

3. Emaillist Entity
   - Integer id: @Column의 name="no", nullable=false 속성 세팅
   - String firstName: nullable=false, length=N 속성 사용
   - String lastName: nullable=false, length=N 속성 사용
   - String email: nullable=false, length=N 속성 사용

4. Guestbook Entity
   - String contents: @Type을 사용한 text 타입 매핑. cf) @Lob은 CLOB, BLOB 타입으로 매핑
   - Date regDate: @Temporal의 속성 TemporalType.TIMESTAMP 사용한 매핑

5. User Entity
   - GenderType gender: @Enumerated를 사용한 Enum 타입 매핑 
   - RoleType role: @Enumerated를 사용한 Enum 타입 매핑

6. Board Entity
   - [문제점] Integer userNo 매핑

7. GuestbookLog Entity
   - LocalDate date:  Date과 LocalDate의 차이점