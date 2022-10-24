# 재고시스템으로 알아보는 동시성이슈 해결방법
[재고시스템으로 알아보는 동시성이슈 해결방법](https://www.inflearn.com/course/%EB%8F%99%EC%8B%9C%EC%84%B1%EC%9D%B4%EC%8A%88-%EC%9E%AC%EA%B3%A0%EC%8B%9C%EC%8A%A4%ED%85%9C#)
인프런 강의 실습코드입니다.

여러 스레드가 동일한 자원을 공유할 때 **동시성이슈**가 발생한다.
의도한 데이터 수정과 다른 경우가 생길 수 있다

문제해결법
1. Application level
    - `syncronized` 이용 : `@Transactional`과 함께 이용하면 동시성이슈가 해결되지 않는다. 또한 여러 application(server)가 db를 공유할 경우는 해결되지 않는다.
2. Database Lock
    - Pessimistic Lock : 데이터에 lock을 거는 방법으로 무결성이 우수하지만 dead lock을 주의해야한다. 충돌이 빈번할경우 Optimistic Lock보다 성능이 좋다
    - Optimistic Lock : 데이터의 버전 혹은 타임스탬프 등을 활용하여 데이터의 무결성을 보장하는 것으로 별도의 lock을 사용하지 않아 성능상 이점이 있다. 단점은 update가 실패했을때 재시도 로직을 개발자가 작성해줘야하고 충돌이 빈번할 경우 Pessimistic Lock보다 성능이 안좋을 수 있다.
    - Name Lock :
3. Redis Distributed Lock



----------------------------------
추가적으로 트랜잭션의 특징중 하나인 격리성과 Database Lock이 무엇이 다른지 궁금했다. 
Optimistic Lock은 별도의 버전컬럼을 이용해 코드로 구현하는 것이라 별도이고
Pessimistic Lock은 데이터베이스의 lock을 이용하는 것이 아닐까 궁금했다.
Pessimistic Lock은 `select for update`를 이용해 lock을 거는것이고 transaction은 `set transaction isolation level read serializable;`를 이용하는 것이다.

++트랜잭션으로 락을 거는 것은 모든 트랜잭션이 하나의 격리레벨로 동작하는 것이고 Pessimistic Lock은 개별 데이터에 대해 lock을 걸 수 있는 것으로 파악했다.