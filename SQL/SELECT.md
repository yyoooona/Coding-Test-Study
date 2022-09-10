### 조회

SELECT 문

**쿼리 조회 시 사칙연산**

- 저장되있는 데이터 타입이 문자인 경우 사칙연산 불가능

  ex) SELECT ENAME + 'A'

    	FROM EMP;

  => ERROR



- 저장되있는 데이터 타입이 숫자인 경우 사칙 연산 가능할 수 있음

  ex) SELECT HIREDATE, HIREDATE + 1

  ​		FROM EMP;

  => 조회 가능

  ex) SELECT HIREDATE * 2

  ​		FROM EMP;

  => ERROR

  ex) SELECT SYSDATE - HIREDATE

  ​		FROM EMP;

  => 조회 가능 ( 날짜에서 날짜를 빼는 연산 가능 )

  ex) SELECT SYSDATE + HIREDATE

  ​		FROM EMP;

  => ERROR



**ㅁ** **DATE**

- DATE +- NUMBER   =>  일수, 시간 가감 연산 가능
- DATE - DATE            =>  두 날짜 사이의 갭 ( 일 수 )



**ㅁ 연결연산자 (||)**

SELECT lnid || ' 차주의 등급은 ' || grade || ' 입니다.'
FROM   tid 
WHERE id_typ = '2' ;



**ㅁ 이름 지정 규칙**

- 첫 글자는 문자로 시작

- 30자 이내 

- A~Z, 0~9, #, $, _ 만 사용 가능

  ex) SELECT lnact, lnact_seq, ln_amt, ln_amt/1000 "대출금(천원)"
    FROM tacct ; 
  ex) CREATE TABLE EMP2 
  (EMPNO    NUMBER(4),
   "SAL*12"   NUMBER) ;



**ㅁ 행 중복제거**

​	ex) SELECT DISTINCT grade

​			FROM tid;

​	ex) SELECT DISTINCT branch, prod_cd
​			FROM tacct ;

​	ex) SELECT branch, DISTINCT prod_cd  

​			FROM tacct ;
​			=> ERROR 	



**ㅁ 검색할 때 조건절에 ' ' (따옴표)**

​	1) SELECT * 
  		FROM TID 
​		WHERE ID_TYP = 1 ; 

​	2) SELECT * 
  		FROM TID 
 	  WHERE ID_TYP = '1' ;  

가급적 2) 방법 사용
숫자형 데이터 타입의 경우에도 ' ' 사용해야 정상적으로 Index를 사용할 수 있게 해준다
1)로 검색 시, 결과는 검색이 되긴 하지만 속도가 떨어질 수 있다



**ㅁ 날짜 조회할 때**

 SELECT *

​	FROM EMP

WHERE HIREDATE = '1980/12/17';

=> 1980/12/17 **<u>00:00:00</u>** 의 데이터를 조회하는 것

DATE : YYYY/MM/DD HH:MI:SS



ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY/MM/DD HH24:MI:SS';



**날짜 조회 시 BETWEEN 연산자 이용, TO_DATE 는 형 변환 함수**

SELECT * 
  FROM EMP 
 WHERE HIREDATE BETWEEN TO_DATE('1980/12/17','YYYY/MM/DD')
                    AND TO_DATE('1980/12/18','YYYY/MM/DD') - **<u>1/86400</u>** ; 

=> 1/86400 은 1초 

​	즉, TO_DATE('1980/12/18','YYYY/MM/DD') - **<u>1/86400</u>** 는

​		1980/12/17 23:59:59 를 의미함



날짜 조회할 때, 

ex) 12월 입사한 사람 구하는 쿼리

​	  SELECT *

​		FROM EMP

​	 **WHERE TO_CHAR(HIREDATE, 'MM') = '12';**



**ㅁ LIKE 비교 연산자 ( % , _ ) **

SELECT *

FROM tcode
WHERE grade LIKE 'A%' ;

=> A로 시작하는 뒤에 0개 이상의 문자



SELECT * 
FROM tcode 
WHERE grade LIKE 'A_' ;
A로 시작하는 뒤에 1개의 문자



**ㅁ NULL, NOT NULL**

SELECT lnact, lnact_seq, acct_typ, lmt_typ, lnid, ln_dt, ln_amt 
FROM tacct
WHERE lmt_typ IS NULL ;

SELECT lnact, lnact_seq, acct_typ, lmt_typ, lnid, ln_dt, ln_amt 
FROM tacct
WHERE lmt_typ IS NOT NULL ;



**ㅁ 연산자 우선순위 (AND 연산 우선순위가 OR 연산 우선순위보다 높다)**

SELECT LNACT, LNACT_SEQ, PROD_CD, LNID, LN_DT, LN_AMT 
  FROM TACCT 
 WHERE LN_DT >= '2021/01/01' 
   AND PROD_CD = '101'
    OR PROD_CD = '102' ;

SELECT LNACT, LNACT_SEQ, PROD_CD, LNID, LN_DT, LN_AMT 
  FROM TACCT 
 WHERE PROD_CD = '102'
    OR PROD_CD = '101'
   AND LN_DT >= '2021/01/01' ;

SELECT LNACT, LNACT_SEQ, PROD_CD, LNID, LN_DT, LN_AMT
  FROM TACCT 
 WHERE LN_DT >= '2021/01/01' 
   AND (PROD_CD = '101'
    OR PROD_CD = '102') ;



**ㅁ INDEX**

INDEX 생성
CREATE INDEX EMP_IX ON EMP(SAL);

INDEX를 사용하려고 할 때
1. 컬럼을 가공하지 않는다
(사칙연산 혹은 TO_CHAR 등 함수를 통해 컬럼을 가공을 하면 INDEX 사용하지 않음)

2. NULL을 검색하지 않는다

3. 암시적인 형 변환을 사용하지 않는다

4. LIKE 비교 시 %, _를 앞에 두지 않는다 



SELECT * 
  FROM EMP 
 WHERE ENAME LIKE 'S%' ; 

=> RANGE SCAN

SELECT * 
  FROM EMP 
 WHERE ENAME LIKE '%S' ; 

=> FULL SCAN

INDEX는 TABLE과 별도로 저장됨
INDEX 쓴다고 해서 무조건 성능이 좋지 않음, RANGE SCAN 하는지 FULL SCAN하는지 확인
FULL SCAN시 성능이 떨어질 수 있음



**ㅁ ORDER BY 절을 이용한 정렬**

꼭 필요한 경우가 아니면 사용하지 않는 것이 좋음

- WHERE절에는 ALIAS 컬럼 사용할 수 없지만 ORDER BY절에서는 가능
  ex) SELECT lnact, lnact_seq, lnid, ln_dt, ln_amt AS 대출금
  		FROM tacct
  	 WHERE lmt_typ IS NULL 
       ORDER BY 대출금 ;

- 컬럼 번호로 정렬
  ex) SELECT lnact, lnact_seq, lnid, ln_dt, ln_amt AS 대출금
          FROM tacct
        WHERE lmt_typ IS NULL 
        ORDER BY 3 ;         // 세 번째 컬럼을 기준으로 ASC 오름차순 정렬

  ex) SELECT lnact, lnact_seq, lnid, ln_dt, ln_amt AS 대출금
         FROM tacct
       WHERE lmt_typ IS NULL 
       ORDER BY 5, 4 DESC ;



**ㅁ NULL 은 대소 비교 불가능**

​	SELECT * 
  	FROM EMP 
​	ORDER BY COMM ; 
​	=> 해당 쿼리 조회 시 NULL이 마지막으로 정렬

- **<u>내림차순으로 정렬을 진행할 때, NULLS FIRST가 DEFAULT로 동작함</u>**

  SELECT * 
    FROM EMP 
  **ORDER BY COMM <u>DESC NULLS LAST</u> ;** 



- **NULL 상태를 다른 값으로 바꾸기 -> NVL 함수 사용**

  SELECT * 
    FROM EMP 
  ORDER BY NVL(COMM,-1)  ; 

  **=> <u>NVL(칼럼명, NULL값을 대치할 지정값)</u>**



