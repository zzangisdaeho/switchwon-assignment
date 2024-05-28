# PaymentImplApplication 실행

## 주의사항 
- build시 avro class generate 실행되도록 하였으나 혹시나 Dto 관련 compile에러가 발생할 시 gradle task에 generateAvroJava로 source generate 해주세요
- 프로젝트 최초 import시 IntelliJ가 jdk세팅 못잡을 때가 있는데 project root directory/.idea 폴더 삭제 후 다시 import시켜주세요
- profile : h2 로 실행
- 