# hhplus-tdd

1. synchronized - 동시 사용량이 많지 않을 경우 편리하나 사용량이 많아질수록 성능 저하 
  100명이 동시에 api 사용할 경우 순차적으로 실행되기 때문에 100번째 사용자는 99명을 모두 기다려야하여 시간이 오래 걸린다.

2. 낙관적 점금 방법 - DB에 버전 정보를 저장하여 따로 관리할 것이 늘어남. 
  userPoint 객체에 버전 정보를 추가하여 api호출하여 업데이트 할 때마다 버전 정보를 확인하여 충돌이 발생할 경우 재시도처리하는 방법

3. ReentrantLock - synchronized 처럼 순차적으로 실행하나 락 범위를 설정하여 명시적으로 락을 획득(lock())하고 해제(unlock()) 하여 대기 시간을 단축시킬 수 있다

4. ConcurrentHashMap - ConcurrentHashMap 에서 제공하는 메서드는 데이터 구조 내부에서 자동으로 동시성 제어를 수행하여 별도의 락 관리 없이 동시성 제어 가능하여 동시성 제어에 최적화된 기능

동시성제어는 ConcurrentHashMap 로 포인트 충전에 적용해보려했는데 어떻게 구현해야할지 감이 안와서 과제 검사 후 피드백 주시면 다시 한번 구현해보도록 하겠습니다. 