# PaymentImplApplication 실행

## 주의사항 
- build시 avro class generate 실행되도록 하였으나 혹시나 Dto 관련 compile에러가 발생할 시 소스패키지의 main/avro를 삭제 후 gradle task에 generateAvroJava로 source generate 해주세요
- 프로젝트 최초 import시 IntelliJ가 jdk세팅 못잡을 때가 있는데 project root directory/.idea 폴더 삭제 후 다시 import시켜주세요
- profile : h2, test(기초 자료 init) 로 실행

## 프로젝트 한계점
- 도메인을 상위모듈에서 독립화 시켰으나, 도메인을 entity화 시키면서 양방향 매핑이 자료형의 충돌로 사용할 수 없음.
  - ManyToOne측에서 List의 generic형이 entity가 아니면 컴파일에러가 발생. 하지만 도메인 입장에서는 jpa에 종속되지 않기 때문에 관련 메소드 오버라이드 불가
  - 관련해서 해결방법을 아직 찾지 못함.
  - 현재 parent기준 orphan remove기능 및 list를 직접 가져와서 add할 시 cascade가 먹지 않고있음. (영속성이 관리 못하고있는것으로 보임)
  - 도메인에 정의된 addChild를 써야함