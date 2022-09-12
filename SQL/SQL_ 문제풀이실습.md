

#### GROUP BY ~ HAVING 절

- WHERE 절과 HAVING절의 차이

  HAVING 절에서는 집계함수를 사용하여 조건을 표현할 수 있음

  WHERE절 조건을 만족하는 데이터들에 대해서 GROUP화 하고 해당 그룹에 HAVING절을 통해 조건을 명시

<BR/>

#### CASE WHEN 분기문

- SIMPLE_CASE_EXPRESSION

  SELECT (CASE 컬럼명 WHEN 비교값1 THEN 반환값1

  ​									  WHEN 비교값2 THEN 반환값2

  ​				...

  ​				ELSE WHEN절 이외의 조건일 때 반환될 값

  ​				END AS 별칭 컬럼명)

  FROM 테이블명



- SEARCHED_CASE_EXPRESSION

  SELECT

  ​			(CASE WHEN 조건문1 THEN 반환값 1

  ​						WHEN 조건문2 THEN 반환값 2

  ​				...

   			ELSE WHEN절 이외의 조건일 때 반환될 값

  ​				END AS 별칭 컬럼명)

  FROM 테이블명

<BR/>

#### 순위집계

1. RANK

   1등, 2등, 3등(85점), 3등(85점), 3등(85점), 6등(80점)

2. DENSE_RANK

   1등, 2등, 3등(85점), 3등(85점), 3등(85점), 4등(80점)

3. ROW_NUMBER

   1등, 2등, 3등(85점), 4등(85점), 5등(85점), 6등(80점)



예시쿼리)

1. SELECT NAME, SCORE, **RANK() OVER(PARTITION BY department ORDER BY score DESC)**

   FROM tScore

2. SELECT NAME, SCORE, ROW_NUMBER() OVER (PARTITION BY department ORDER BY score DESC)

   FROM tScore

 

<BR/>

![image-20220911170824979](C:\Users\USER\AppData\Roaming\Typora\typora-user-images\image-20220911170824979.png)

<BR/>

![image-20220911171308109](C:\Users\USER\AppData\Roaming\Typora\typora-user-images\image-20220911171308109.png)

<BR/>



#### 조인

<BR/>

#### 집합연산



SELECT te.eName, TO_CHAR(te.startdate, 'YYYY**-**MM**-**DD')

FROM tEmployee te

WHERE te.DNumber IN ('D1001', 'D2001')

**ORDER BY TO_CHAR(te.startdate, 'YYYYMMDD') ASC**

**=> ORDER BY te.StartDate**



BETWEEN CAST('2020-01-01' AS timestamp) and CAST('2020-02-01' AS timestamp) 

BETWEEN A AND B (B는 포함 X)



SELECT TO_CHAR(tor.Odate, 'MM')

FROM tOrder AS tor

GROUP BY TO_CHAR(tor.Odate, 'MM')

UNION

SELECT TO_CHAR(tre.Rdate, 'MM')

FROM tReturn AS tre

GROUP BY TO_CHAR(tre.Rdate, 'MM')



SELECT tpr.ENumber AS 직원번호

, SUM(tpr.PCount) AS 총_생산량

, **RANK() OVER**(ORDER BY SUM(tpr.PCount) DESC) AS 총_ 생산량_순위

FROM tProduction AS tpr

GROUP BY tpr.ENumber

LIMIT 10



SELECT tpr.ENumber AS 직원번호

, tpr.INumber AS 제품번호

, tpr.PCount AS 생산량

, **ROW_NUMBER() OVER**(PARTITION BY tpr.INumber ORDER BY tpr.PCount DESC, tor.ENumber ASC) AS 총_ 생산량_순위

FROM tProduction AS tpr

WHERE tpr.INumber = 'I2003'





DENSE_RANK()

SELECT 

  CASE

  WHEN tem.DNumber = 'D1001' THEN '문구생산부'

  WHEN tem.DNumber = 'D2001' THEN '가구생산부'

  WHEN tem.DNumber = 'D3001' THEN '악세사리생산부'

  WHEN tem.DNumber = 'D4001' THEN '전자기기생산부'

  WHEN tem.DNumber = 'D5001' THEN '음료생산부'

  ELSE '부서없음'

  END AS 부서명

, tem.EName AS 직원명

, SUBSTRING(tem.ERRN, 1, 2) AS 출생연도

, DENSE_RANK() OVER(PARTITION BY tem.DNumber ORDER BY SUBSTRING(tem.ERRN, 1, 2)) AS 출생연도_순위

FROM tEmployee AS tem



SELECT tit.IName AS 제품명, SUM(tpr.PCount) AS 총_판매량

FROM tOrder AS tor

JOIN tProduction AS tpr

ON tor.PNumber = tpr.PNumber

JOIN tItem AS tit

ON tpr.INumber = tit.INumber

WHERE TO_CHAR(tor.ODate, 'YYYY-MM') = '2021-01' AND SUBSTRING(tit.INumber, 1, 2) = 'I4'

GROUP BY tit.IName



SELECT tit.Name AS 제품명, tBase.PCount AS 생산량

​			 , RANK() OVER(ORDER BY tBase.PCount DESC NULLS LAST) AS 생산량_순위 

FROM tItem AS tit

LEFT JOIN

(

​	SELECT tpr.INumber, SUM(tpr.PCount) AS PCount

​	FROM tProduction tpr

​	WHERE TO_CHAR(tpr.PDate, 'YYYYMM') = '202001'	

​	GROUP BY INumber

) AS tBase

ON tit.INumber = tBase.INumber





GROUP BY, 날짜형식(DATE_FORMAT), 조인, DISTINCT, RANK, 반올림

출처 : 한 번에 끝내는 코딩테스트 369 Java편 초격차 패키지 Online. (fastcampus)

