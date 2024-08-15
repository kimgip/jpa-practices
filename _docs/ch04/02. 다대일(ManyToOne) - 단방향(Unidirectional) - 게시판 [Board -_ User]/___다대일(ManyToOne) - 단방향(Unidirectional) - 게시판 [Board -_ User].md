

#### 2-4. JpaBoardRepository Test : Spring Data JPA 기반 Repository


3. __JpaBoardRepositoryTest.java__
    1) JpqlBoardRepositoryTest 쿼리로그 꼭 비교 분석할 것 (완전 일치)
    2) test01Save()
        - 기본 메소드 CrudRepository.save(s)
        - CrudRepository.save(s) 에 대한 오해
          <img src="../_resources/32008.png" width="600px" />
          <br>
            1) insert만 하는 메소드가 아니다.
            2) update도 한다.
            3) insert가 되면 파라미터로 전달받은 객체는 영속화가 된다.
            4) 하지만, update에서는 파라미터로 전달받은 객체는 값만 복사하는데 사용되기 때문에 영속화가 되지 않는다.
            5) 따라서 save() 메소드 호출 후, 영속객체를 사용해야 하면 반드시 반환되는 객체를 사용하여야 한다.(반환되는 객체는 영속화를 보장한다.)
            6) 이와 관련된 테스트는 Model08 UserRepositoryTest에서 한다.
    3) test02FindById
        - 기본 메소드 CrudRepository.findById()를 사용하면 Outer Join이 기본이고 JPA에서는 left outer join만 지원한다.
        - JPQL 사용해서 구현하는 것은 JPQL Repository Test에서 확인 한 것 처럼 Select 쿼리가 2번 실행하기 때문에 고려의 대상이 안된다.
        - Left Outer Join 쿼리 로그 확인해 볼 것.
    4) test03FindById2
        - JpaBoardQryDslRepositoryImpl.findById2(no)
        - 기본 메소드 CrudRepository.findById()는 join은 되지만 Projection이 이슈가 되면 사용하지 못한다.
        - QueryDSL Projection 으로 해결한다.
        - Inner Join을 사용한다.
        - Projection에서는 setter를 사용하는 Projections.fields()를 사용했다.
        - Projection 타겟 객체는 BoardDto 이다.
    5) test04FindAllByOrderByRegDateDesc
        - 쿼리메소드 JpaBoardRepository.findAllByOrderByRegDateDesc()
        - 쿼리로그를 보면, join을 하지 않는 것을 확인할 수 있다.
        - 기본이 EAGER이기 때문에 각각의 Board가 참조하고 있는 User의 정보를 얻어오기 위해 select가 여러번 실행된다.
        - User가 영속객체이기 때문에 1차 캐시가 되면(중복되는 게시물 작성자) User를 가져오기 위해 게시물 전체에 대한 select User는 하지 않을 것이다.
        - **성능이슈**: 하지만, 대용량 게시판에선 문제가 될 수 있다.
    6) test05FindAllByOrderByRegDateDesc2
        - JpaBoardQryDslRepositoryImpl.findAllByOrderByRegDateDesc2()는 test04의 문제를 해결한다.
        - QueryDSL Fetch Join을 사용한다.
    7) test06FindAllByOrderByRegDateDesc3
        - JpaBoardQryDslRepositoryImpl.findAllByOrderByRegDateDesc3()은 test05의 findAllByOrderByRegDateDesc2()에 Projection 기능을 추가하였다.
        - QueryDSL Inner Join을 사용한다.
        - Projections.fields()를 통해 setter를 활용한다.
        - 주의할 것은 Projection을 사용하면 fetch join되는 엔티티는 별칭을 가질 수 없기 때문에 fetch join을 사용할 수 없다.(test02에서도 마찬 가지이다.)
        - Projection을 하는 경우 Inner Join만 사용한다.
    8) test07FindAllByOrderByRegDateDesc3
        - JpaBoardQryDslRepositoryImpl.findAllByOrderByRegDateDesc3(page, size)는 test06의 findAllByOrderByRegDateDesc3()에 Paging 기능을 오버로딩 하였다.
        - QueryDSL의 offset(), limit()를 사용해서 Paging을 구현하였다.
        - page index는 0부터 시작
    9) test08FindAll3
        - JpaBoardQryDslRepositoryImpl.findAll3(pageable)는 기능과 만들어지는 쿼리는 test07의 findAllByOrderByRegDateDesc3(page, size)와 같다.
        - 차이점은 Paging과 Sorting을 위해 Pageable 인터페이스 구현체를 파라미터로 받아 QueryDSL에 적용하고 있다.
        - 따라서 OrderBy 필드를 외부에서 지정할 수 있다.
        - page index는 0부터 시작
    10) test09FindAll3
        - JpaBoardQryDslRepositoryImpl.findAll3(keyword, pageable)는 Like검색을 위한 keyword를 추가하였다.
    11) test10Update
        - 특별난 메소드를 제공하지 않는다.
        - 영속객체를 사용한다.
        - 선별적 컬럼 업데이트이지만 영속객체를 사용하기 때문에 전체 속성이 업데이트 된다.
        - select와 update 실행
    12) test11Update
        - test10의 문제점을 해결하기 위해 QueryDSL로 구현된 JpaBoardQryDslRepositoryImpl.update(Board) 이다.
        - update 쿼리만 실행된다.
        - 선별적 컬럼 업데이트가 가능하다.
        - update(), set(), where(), execute() 함수 사용법
    13) test12Delete
        - 기본 메소드 CrudRepository.delete(board)
        - 영속객체를 사용한다.
        - select와 delete 실행
    14) test13Delete
        - 기본 메소드 CrudRepository.deleteById(no)
        - 영속객체를 사용한다.
        - select와 delete 실행
        - test12의 CrudRepository.delete(board)와 동작은 완전 동일하다.
    15) test14Delete
        - JpaBoardQryDslRepositoryImpl.delete(no)
        - QueryDSL로 구현된 메소드다.
        - delete 쿼리만 실행된다.
    16) test15Delete
        - JpaBoardQryDslRepositoryImpl.delete(boardNo, userNo)
        - QueryDSL로 구현된 메소드다.
        - delete 쿼리만 실행된다.
        - 게시판 삭제 비즈니스 로직에 맞게 작성된 메소드이다.
    17) JpaBoardRepository.count() 메소드
        - 기본 메소드 CrudRepository.count()

#### 2-6. JpqlUserRepository Test : JPQL 기반 Repository
1. __JpqlConfig.java__
    1) ch03/02 내용과 동일

2. __JpqlUserRepository.java__
    1) JPQL 기반으로 작성
    2) 저장을 위한 객체 영속화
    3) TypedQuery와 Update 구현시 TypedQuery 대신 Query 객체 사용하는 방법
    4) DTO 객체를 활용한 Projection 방법
    5) 집합함수: 통계 쿼리 스칼라 타입 조회

3. __JpqlUserRepositoryTest.java__

    1) test01Save
        - JpqlUserRepository.save(User)
        - 객체 영속화
    2) test02FindAllById
        - JpqlUserRepository.findById(id)
        - 영속화 객체 조회 이기 때문에 영속화 컨텍스트에서 찾고 없으면 select 쿼리를 통해 DB에서 가져온다.(1차 캐시)

    3) test03UpdatePersisted
        - JpqlUserRepository.update1(User)
        - 영속화 객체를 사용한 수정(업데이트)
        - 성능이슈: update 이전에 select 쿼리가 실행되는 문제점이 있다.(쿼리 로그 확인해 볼 것)
    4) test04FindByEmailAndPassword
        - JpqlUserRepository.findByEmailAndPassword(Long, String)
        - 영속화 객체를 사용하지 않는다. 따라서 무조건 select 쿼리를 통해 DB에서 가져온다.
        - TypedQuery 객체 사용
        - 주로 사용자 인증(로그인)에 사용하게 될 메소드이다.
        - 따라서, 모든 정보를 담은 User 엔티티 객체륿 반환할 이유가 없다. UserDto를 사용한 Projection 구현
        - 이름 기반 파라미터 바인딩
    5) test05Update2
        - JpqlUserRepository.update2(User)
        - 영속화 객체를 사용하지 않고 JPQL 기반의 업데이트
        - JpqlUserRepository.update1(User)에 비하여 select 쿼리가 실행되지 않는 장점이 있다(쿼리 로그 확인해 볼 것)
        - 반환할 타입이 없는 경우에는 TypedQuery 대신 Query객체를 사용하여 JPQL를 실행시킨다.
        - 이름 기반의 파라미터 바인딩을 사용하는 것은 TypedQuery와 다르지 않다.
    6) JpqlUserRepository.count() 메소드
        - JPQL에서 통계함수 사용

#### 2-7. QueryDslUserRepository Test : QueryDSL 기반 Repository
1. __JpqlConfig.java__
    1) ch03/02 내용과 동일

2. __QueryDslUserRepository.java__
    1) QueryDSL 기반으로 작성
    2) 저장을 위한 객체 영속화
    3) 다양한 쿼리함수 사용법
    4) QueryDSL DTO 객체를 활용한 Projection 방법
    5) QueryDSL 통계 쿼리

3. __QueryDslUserRepository Test.java__
    1) test01Save
        - QueryDslUserRepository.save(User)
        - 객체 영속화
    2) test02FindAllById
        - QueryDslUserRepository.findById(id)
        - 영속화 객체 조회 이기 때문에 영속화 컨텍스트에서 찾고 없으면 select 쿼리를 통해 DB에서 가져온다.(1차 캐시)
    3) test03UpdatePersisted
        - QueryDslUserRepository.update1(User)
        - 영속화 객체를 사용한 수정(업데이트)
        - 성능 이슈: update 이전에 select 쿼리가 실행되는 문제점이 있다.(쿼리 로그 확인해 볼 것)
    4) test04FindAllById2
        - QueryDslUserRepository.findById2(id)
        - 영속화 객체를 사용하지 않는다. 따라서 무조건 select 쿼리를 통해 DB에서 가져온다.
        - 하지만, 반환되는 Entity 객체는 영속객체다.
        - 쿼리함수 from(), where(), fetchOne()
    5) test05Update2
        - QueryDslUserRepository.update2(User)
        - 영속화 객체를 사용하지 않고 JPQL 기반의 업데이트
        - QueryDslUserRepository.update1(User)에 비하여 select 쿼리가 실행되지 않는 장점이 있다(쿼리 로그 확인해 볼 것)
        - 쿼리함수 update(), where(), set(), execute()
    6) test05FindByEmailAndPassword
        - QueryDslUserRepository.findByEmailAndPassword(Long, String)
        - 영속화 객체를 사용하지 않는다. 따라서 무조건 select 쿼리를 통해 DB에서 가져온다.
        - 주로 사용자 인증(로그인)에 사용하게 될 메소드이다.
        - QueryDSL에서 UserDto를 사용한 Projection 구현 (Projections.constructor)
        - 쿼리함수 select(), from(), where(), fetchOne()
    7) QueryDslUserRepository.count() 메소드
        - QueryDSL의 fetchCount() 사용방법

#### 2-8. JpaUserRepository Test : Spring Data JPA 기반 Repository
1. __JpaConfig.java__
    1) ch03/02 내용과 동일

2. __JpaUserRepository.java__
    1) 기본 메소드
        - save(User)
        - findById(id)
    2) 쿼리 메소드
        - findByEmailAndPassword(email, password)
    3) @Query 어노테이션을 사용한 메소드에 쿼리 정의
        - findById2(id)
        - update(User)
3. __JpaUserRepositoryTest.java__
    1) test01Save()
        - 기본 메소드 CrudRepository.save(S)
    2) test02FindById
        - 기본 메소드 CrudRepository.findById()
    3) test03Update()
        - 기본 메소드 CrudRepository.findById()를 통해 영속객체를 얻어오고 업데이트를 한다.
        - **성능이슈: update 쿼리 이전에 select 쿼리 실행**
    4) test04FindById2()
        - JPQL 기반 메소드 직접 구현
        - JpaUserRepository.findById2(id)
        - @Query 어노테이션을 사용한 메소드쿼리(JPQL) 정의
        - JPQL Projection
    5) test05Update
        - JPQL 기반 메소드 직접 구현
        - JpaUserRepository.update(...)
        - @Query 어노테이션을 사용한 메소드 쿼리(JPQL) 정의
        - JPQL 이름 바인딩
        - 이름 바인딩은 객체 이름 경로를 사용할 수 없기 때문에 메소드의 파라미터가 많다.
        - 이를 해결하기 위해서는 QueryDSL과 통합해야 함(ch03/04의 JpaUserRepository 참고)
    6) test06FindByEmailAndPassword()
        - JpaUserRepository.findByEmailAndPassword(email, password)
        - 쿼리메소드 예시
        - 프로젝션 하지 않음
        - 프로젝션을 하기 위해서는 앞의 @Query 메소드쿼리 정의 또는 QueryDSL 통합을 해야 한다.
        - jpa03-model03의 JpaUserRepository 참고