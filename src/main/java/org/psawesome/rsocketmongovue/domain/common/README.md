reflection 사용 정리
=

## fields

- getXXX
  -
  - modifier public 인 것들이 조회됨.
    -
    - SecurityManager sm = System.getSecurityManager()
  
- getDeclaredXXX
  -
  - 선언된 필드 조회 (private 포함)
    - 

- dtoField.set(res, entityField.get(entity));
  -
  
  - 설명
    -
    - **[1.]dtoField**.set(**[2]res**,  
      -
      
      1. **dtoField** 에 값을 set 하는데.  
        - 
        
      2. **res**(dto) newInstance 한 객체이고,  
        - 
    
    - **[3.]entityField.get**(**[4]entity**)
      -
      3. **entity 에 선언된 필드**(껍데기)에  
        -
        
      4. **entity** 객체에 있는 값을 가져와서  
        -
        
      * last
        -
        - 1번을 마무리한다.  


## constructor: getDeclaredConstructors

- Class.~~newInstance()~~
  -
  - Class Class 에 정의된 newInstance() 메서드는 deprecated 됨.
    - 
- Constructor<?>.newInstance()
  -
  - @NoArgumentConstructor
    -
    - **기본 생성자를 없앨 경우** -> java.lang.NullPointerException
      - 
      - setAccess(true) 와는 관계 없음.
      
