**ㅁ 그룹함수**

그룹당 한번 씩 실행
SELECT SUM(SAL), AVG(SAL), MIN(SAL), MAX(SAL), COUNT(SAL)
  FROM EMP ; 

**SUM이나 AVG같은 함수는 DATATYPE이 숫자일때만 가능**

ex) SELECT SUM(ENAME)

   	 FROM EMP;

​		=> ERROR

ex) SELECT AVG(HIREDATE)

​		 FROM EMP;

​		=> ERROR



**MIN, MAX, COUNT 는 DATATYPE과 상관없이 사용 가능**

ex) SELECT MIN(ENAME), MAX(HIREDATE)

  	  FROM EMP

​		=> 조회 가능



**NULL이 들어있는 칼럼도 COUNT, AVG 사용 가능, NULL을 제외한 행 수 계산**

ex) SELECT COUNT(*), COUNT(EMPNO), COUNT(COMM)

​		  FROM EMP;

​	=> 14, 14, 4

ex) SELECT AVG(COMM), SUM(COMM) / 14 
	  FROM EMP ; 

​	=> AVG함수는 NULL을 제외하고 계산하기 때문에 AVG(COMM)은 SUM(COMM)/4로 계산됨

​	SELECT AVG(NVL(COMM, '0')), SUM(COMM) /14

​	  FROM EMP;

​	=> 이렇게 해야지 조회 시 같은 결과가 나옴



**그룹함수는 기본적으로 중복을 포함해서 계산, BUT 중복을 제거하려면 그룹함수에 DISTINCT 사용 가능**

SELECT COUNT(DEPTNO), COUNT(DISTINCT DEPTNO)
  FROM EMP ; 
=> 14, 3



SELECT SUM(DEPTNO), SUM(DISTINCT DEPTNO)

  FROM EMP;



**ㅁ GROUP BY**

ex) SELECT SUM(SAL)

​		  FROM EMP;



SELECT SUM(SAL) 
  FROM EMP 
 WHERE DEPTNO = 10 ; 

SELECT SUM(SAL) 
  FROM EMP 
 WHERE DEPTNO = 20 ; 

SELECT SUM(SAL) 
  FROM EMP 
 WHERE DEPTNO = 30 ; 



SELECT DEPTNO, SUM(SAL)

​	FROM EMP

GROUP BY DEPTNO;



GROUP BY 절을 사용한다는 건 그룹함수를 사용해야 될 때,

그룹함수를 사용하지 않으면 그냥 중복제거밖에 안됨!



SELECT DEPTNO, JOB, SUM(SAL)  -- ERROR 
  FROM EMP 
 GROUP BY DEPTNO ; 

SELECT SUM(SAL) , DEPTNO   -- ERROR 
  FROM EMP ;



SELECT TO_CHAR(HIREDATE,'YYYY'), COUNT(*)
  FROM EMP 
 GROUP BY TO_CHAR(HIREDATE,'YYYY');



SELECT CASE WHEN SAL >= 3000 THEN '상'
               WHEN SAL >= 2000 THEN '중'
               **ELSE '하'**
          **END** 
       ,SUM(SAL)
  FROM EMP 
 GROUP BY CASE WHEN SAL >= 3000 THEN '상'
               WHEN SAL >= 2000 THEN '중'
               ELSE '하'
          END ; 

 **'*' 컬럼의 별칭을 이용해서 GROUPING 은 안됨!**

**TIP)** 컬럼의 별칭을 사용하는 곳 FROM, WHERE, GROUP BY, HAVING 인식 안돼

ORACLE 에서는 **<u>ORDER BY절이 유일</u>**

SELECT DEPTNO AS DEPTID
      ,SUM(SAL) 
  FROM EMP 
 GROUP BY DEPTID ; -- ERROR



ㅁ GROUP BY ~ HAVING 절 실습문제

1) SELECT DEPTNO, SUM(SAL) 
  	FROM EMP 
	 GROUP BY DEPTNO 
	 HAVING SUM(SAL) > 9000 ; 

2) SELECT DEPTNO, SUM(SAL) 
  	FROM EMP 
	  HAVING SUM(SAL) > 9000
      GROUP BY DEPTNO   ; 

**=> HAVING절과 GROUP BY절은 순서가 바뀌어도 실행이 됨**



1) 
SELECT DEPTNO, SUM(SAL) 
  FROM EMP 
 GROUP BY DEPTNO 
HAVING SUM(SAL) > 9000
   AND DEPTNO IN (10,20) ; 

2)
SELECT DEPTNO, SUM(SAL) 
  FROM EMP 
 WHERE DEPTNO IN (10,20)
 GROUP BY DEPTNO 
HAVING SUM(SAL) > 9000 ;

**=> 2) 방법이 WHERE절이 먼저 시행되서 데이터를 줄이고 그룹핑하니까 시간을 줄일수 있어 효율적**



**'*'<u>GROUP BY절에 정의되어있는 칼럼, 표현식</u>이나 <u>그룹함수</u>만 HAVING절에 사용 가능**

ex) SELECT DEPTNO, SUM(SAL) 
  FROM EMP 
 GROUP BY DEPTNO 
HAVING SUM(SAL) > 9000
   AND JOB > 'A' ;   **-- ERROR** 



SELECT branch                AS 지점
      ,TO_CHAR(ln_dt,'YYYY') AS 연도
      ,COUNT(*)
  FROM tacct 
 WHERE lmt_typ IS NULL 
GROUP BY branch
        ,TO_CHAR(ln_dt,'YYYY')
HAVING SUM(LN_AMT) > '170000000'
   AND TO_CHAR(LN_DT, 'YYYY') = '2020'
ORDER BY 1, 2 ;