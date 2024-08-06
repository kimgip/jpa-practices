# JPA Practices


## ch01: Basic Concepts & Configuration



## ch02: Mapping I
#### 01. 객체(@Entity) 와 테이블(@Table)
#### 02. 기본키(@Id)
#### 03. 필드와 컬럼(@Colume)



## ex03. Persistence Context
#### 01. 영속성 켄텍스트(Persistence Context)
#### 02. 영속성 관리와 엔티티 생명주기
#### 03. 엔티티 조회, 등록, 수정 그리고 삭제
#### 04. 준영속성



## ex04: Mapping II: 다양한 연관관계 매핑 및 Repository 작성법
#### 01. 단일(One) - 방명록
#### 02. 다대일(ManyToOne) : 단방향(Unidirectional) : 게시판 \[Board -> User\]
#### 03. 다대일(ManyToOne) : 양방향(Bidirectional) : 온라인북몰 \[Order &lt;-&gt; User\]
#### 04. 일대다(OneToMany) : 단방향(Unidirectional) : 게시판 \[Board -> Comment\]
#### 05. 일대다(OneToMany) : 양방향(Bidirectional) : 온라인북몰 \[User &lt;-&gt;Order\]
#### 06. 일대일(OneToOne) : 단방향(Unidirectional) : 주테이블 외래키 : JBlog \[User -> Blog\]
#### 07. 일대일(OneToOne) : 양방향(Bidirectional) : 주테이블 외래키 : JBlog \[User &lt;-&gt; Blog\]
#### 08. 일대일(OneToOne) : 양방향(Bidirectional) : 대상테이블 외래키(식별관계) : JBlog \[User &lt;-&gt; Blog\]
#### 09. 다대다(ManyToMany) : 단방향(Unidirectional) : 음반검색 \[Song -> Genre\]
#### 10. 다대다(ManyToMany) : 양방향(Bidirectional) : 음반검색 \[Song &lt;-&gt; Genre\]
#### 11. 다대다(ManyToMany) : 혼합모델(연결엔티티, 복합키(PK), 식별관계) : @EmbeddedId : 온라인북몰 \[User &lt;-&gt; CartItem -> Book\]
#### 12. 다대다(ManyToMany) : 혼합모델(연결엔티티, 복합키(PK), 식별관계) : @IdClass :  온라인북몰 \[User &lt;-&gt; CartItem -> Book\]
#### 13. 다대다(ManyToMany) : 혼합모델(연결엔티티, 새PK, 비식별관계) : 온라인북몰 \[User &lt;-&gt; CartItem -> Book\]