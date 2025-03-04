-- 1.1 userTbl테이블을 대상으로 1개의 이름을 입력하여 해당 회원의 정보를 출력하는 프로시저를 작성하고 실행시키세요  '조관우' 회원의 정보를 출력하세요
use ssgdb;
-- 동적
DELIMITER $$

CREATE PROCEDURE UserInfo(IN user_name_input VARCHAR(10))
BEGIN
SELECT *
FROM userTbl
WHERE name = user_name_input;
end$$

DELIMITER ;

CALL UserInfo('조관우');
-- 1.2 userTbl 테이블  : 회원중 출생년도가 1970 이후 출생자이면서 키가 178 초과 인 회원의 정보를 출력하는 프로시저를 작성하고 실행시키세요
DELIMITER $$
CREATE PROCEDURE GetUserBirHei()
BEGIN
SELECT *
FROM usertbl
WHERE birthYear > 1970 AND height >178;
end $$
DELIMITER ;

CALL GetUserBirHei();
-- 1.3 usertbl 테이블 :  1980년 이후 출생자에게는 "고객님 건강보험 생애 전환 서비스 가입에 해당하지 않습니다." 1980년 이전 출생자들에게는 "고객님 건강보험 생애 전환 서비스 가입에 해당하오니 가입해 주시길 바랍니다." 출력하는 프로시저 작성

DROP PROCEDURE IF EXISTS ifelseProc;
DELIMITER $$
CREATE PROCEDURE ifelseProc(IN userName VARCHAR(20))
BEGIN
            DECLARE bYear INT;

SELECT birthYear into bYear FROM usertbl where name = userName;
if(bYear >= 1980) THEN
SELECT '고객님 건강보험 생애 전환 서비스 가입에 해당하지 않습니다.';
else
SELECT '고객님 건강보험 생애 전환 서비스 가입에 해당하오니 가입해 주시길 바랍니다.';
END IF;
     END$$
DELIMITER ;


-- 1.4 while문을 활용하여 구구단을 문자열로 생성해서 테이블에 입력하는 프로시저를 작성해 보자

DROP Table IF EXISTS guguTBL;
CREATE TABLE guguTBL(txt VARCHAR(100));  -- 구구단 저장용 테이블

DROP PROCEDURE if exists whileProcgugu;
DELIMITER $$
CREATE PROCEDURE whileProcgugu()
BEGIN
        DECLARE i int; -- 구구단 앞자리
        DECLARE j int; -- 구구단 뒷자리
        DECLARE str VARCHAR(100); -- 각 단을 문자열로 저장

        SET i = 2;

        WHILE (i < 10) DO -- 2단~9단까지
            SET str = '';
            SET j=1; -- 구구단 뒤 숫자 2x1 부터 9까지
            WHILE(j <10) DO
                    SET str = concat(str, '', i, 'x', j, '=', i*j); -- 결과물 출력
                    SET j = j+1;
end while;
                SET i = i+1;
INSERT INTO guguTBL VALUES (str);

END WHILE;

END $$
DELIMITER ;

call whileProcgugu();
select * from guguTBL;

-- 1.4-1 1부터 100까지 합계를 구하는 totalSum(1,100) 프로시저 작성해 보세요. 출력 포맷은 '1-100의 총합은 5050 입니다.'
DROP PROCEDURE IF EXISTS totalSum;
DELIMITER $$
CREATE PROCEDURE totalSum()
BEGIN
        DECLARE i int; -- 1씩 증가하는 값
        DECLARE result int; -- 합계(정수형), 오버플로 발생시킬 예정
        DECLARE savepointResult int; -- 오버플로 직전의 값 저장

        DECLARE EXIT HANDLER FOR 1264 -- INT형 오버플로가 발생하면 해당 부분 수행
BEGIN
SELECT CONCAT('INT 오버플로 직전의 합계 --> ', savepointResult);
SELECT CONCAT('1+2+3......+', i, '= 오버플로');
END ;

        SET i = 1; -- i 1로 초기화
        SET result = 0; -- 합계  초기화

        WHILE(TRUE) DO -- 무한루프
            SET savepointResult = result; -- 오버플로 직전의 합을 저장하기 위해
            SET result = result + i;
            SET i = i + 1;
END WHILE;
END $$
DELIMITER ;

call totalSum();
-- 1.5 DECARE~ HANDLER 를 이용해서 오류처리 구문을 추가해 보자 . ex) 1부터 숫자를 더하여 합계가 정수형(int)데이터 형식의 오버플로우가 발생하면 멈추고 오류처리를 해보자

-- 1.6 현재 저장된 프로시저의 이름과 내용을 확인해 보자

use information_schema;
SELECT routine_name, ROUTINES.ROUTINE_DEFINITION FROM information_schema.ROUTINES
where ROUTINE_SCHEMA= 'ssgdb' and ROUTINE_TYPE = 'PROCEDURE';
-- 1.7 파라미터도 확인해 보자
SELECT parameter_mode, parameter_name, DTD_IDENTIFIER FROM information_schema.PARAMETERS
where SPECIFIC_NAME = 'ifelseProc';
-- 1.8 테이블 이름을 파라미터로 전달해 보자
use ssgdb;

DROP PROCEDURE if exists nameTblProc;

DELIMITER $$
CREATE PROCEDURE nameTblProc(IN tblname VARCHAR(20))
BEGIN
SELECT * FROM tblname;
END $$
DELIMITER ;

call nameTblProc('usertbl');

DELIMITER $$
CREATE PROCEDURE nameTblProc(IN tblname VARCHAR(20))
BEGIN
    SET @sqlQuery = concat ('SELECT * FROM', tblname);
PREPARE myQuery FROM @sqlQuery;
EXECUTE myQuery;
DEALLOCATE PREPARE myQuery;
end $$
DELIMITER ;

-- 1.9 배송담당자는 사용자의 정보를 접근할 수 있는 프로시저(delivProc)를 이용하여 사용자의 주소와 이름을 확인한다.
    -- userID, name, addr, mobile1, mobile2 만 접근해서 사용자의 정보를 조회할 수 있는 delivProc 작성하세요
    -- 배송담당자는 사용자의 아이디로 회원의 정보를 접근할 수 있다.

DELIMITER $$
CREATE PROCEDURE delivProc(IN id VARCHAR(20))
BEGIN
SELECT usertbl.userID, usertbl.addr, usertbl.mobile1,usertbl.mobile2 FROM usertbl WHERE userID =id;
END $$
DELIMITER ;

call delivProc();

use ssgdb;
CREATE TABLE usertbl -- 회원 테이블
(
    userID    CHAR(8)     NOT NULL PRIMARY KEY, -- 사용자 아이디(PK)
    name      VARCHAR(10) NOT NULL,             -- 이름
    birthYear INT         NOT NULL,             -- 출생년도
    addr      CHAR(2)     NOT NULL,             -- 지역(경기,서울,경남 식으로 2글자만입력)
    mobile1   CHAR(3),                          -- 휴대폰의 국번(011, 016, 017, 018, 019, 010 등)
    mobile2   CHAR(8),                          -- 휴대폰의 나머지 전화번호(하이픈제외)
    height    SMALLINT,                         -- 키
    mDate     DATE                              -- 회원 가입일
);

CREATE TABLE buytbl -- 회원 구매 테이블(Buy Table의 약자)
(
    num       INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 순번(PK)
    userID    CHAR(8)            NOT NULL,             -- 아이디(FK)
    prodName  CHAR(6)            NOT NULL,             --  물품명
    groupName CHAR(4),                                 -- 분류
    price     INT                NOT NULL,             -- 단가
    amount    SMALLINT           NOT NULL,             -- 수량
    FOREIGN KEY (userID) REFERENCES usertbl (userID)
);

INSERT INTO usertbl
VALUES ('LSG', '이승기', 1987, '서울', '011', '1111111', 182, '2008-8-8');
INSERT INTO usertbl
VALUES ('KBS', '김범수', 1979, '경남', '011', '2222222', 173, '2012-4-4');
INSERT INTO usertbl
VALUES ('KKH', '김경호', 1971, '전남', '019', '3333333', 177, '2007-7-7');
INSERT INTO usertbl
VALUES ('JYP', '조용필', 1950, '경기', '011', '4444444', 166, '2009-4-4');
INSERT INTO usertbl
VALUES ('SSK', '성시경', 1979, '서울', NULL, NULL, 186, '2013-12-12');
INSERT INTO usertbl
VALUES ('LJB', '임재범', 1963, '서울', '016', '6666666', 182, '2009-9-9');
INSERT INTO usertbl
VALUES ('YJS', '윤종신', 1969, '경남', NULL, NULL, 170, '2005-5-5');
INSERT INTO usertbl
VALUES ('EJW', '은지원', 1972, '경북', '011', '8888888', 174, '2014-3-3');
INSERT INTO usertbl
VALUES ('JKW', '조관우', 1965, '경기', '018', '9999999', 172, '2010-10-10');
INSERT INTO usertbl
VALUES ('BBK', '바비킴', 1973, '서울', '010', '0000000', 176, '2013-5-5');
INSERT INTO buytbl
VALUES (NULL, 'KBS', '운동화', NULL, 30, 2);
INSERT INTO buytbl
VALUES (NULL, 'KBS', '노트북', '전자', 1000, 1);
INSERT INTO buytbl
VALUES (NULL, 'JYP', '모니터', '전자', 200, 1);
INSERT INTO buytbl
VALUES (NULL, 'BBK', '모니터', '전자', 200, 5);
INSERT INTO buytbl
VALUES (NULL, 'KBS', '청바지', '의류', 50, 3);
INSERT INTO buytbl
VALUES (NULL, 'BBK', '메모리', '전자', 80, 10);
INSERT INTO buytbl
VALUES (NULL, 'SSK', '책', '서적', 15, 5);
INSERT INTO buytbl
VALUES (NULL, 'EJW', '책', '서적', 15, 2);
INSERT INTO buytbl
VALUES (NULL, 'EJW', '청바지', '의류', 50, 1);
INSERT INTO buytbl
VALUES (NULL, 'BBK', '운동화', NULL, 30, 2);
INSERT INTO buytbl
VALUES (NULL, 'EJW', '책', '서적', 15, 1);
INSERT INTO buytbl
VALUES (NULL, 'BBK', '운동화', NULL, 30, 2);
commit;

SELECT * FROM usertbl;
SELECT * FROM buytbl;

use ssgdb;
SET GLOBAL log_bin_trust_function_creators = 1;

DROP FUNCTION IF EXISTS userFunc;
DELIMITER $$
CREATE FUNCTION userFunc (value1 INT, value2 INT)
    RETURNS INT
BEGIN
RETURN value1 + value2;
end $$
DELIMITER ;

SELECT userFunc(100, 200);

--
use ssgdb;
DROP FUNCTION IF EXISTS getAgeFunc;
DELIMITER $$
CREATE FUNCTION getAgeFunc(bYear INT)
    RETURNS INT
BEGIN
    DECLARE age INT;
    SET age = YEAR(CURDATE()) - bYear;
RETURN age;
end $$
DELIMITER ;

SELECT getAgeFunc(1979);

SELECT getAgeFunc(1979) INTO @age1979;
SELECT getAgeFunc(1997) INTO @age1997;
SELECT CONCAT('1997년과 1979년의 나이차 ==> ', (@age1979-@age1997));

SELECT userID, name, getAgeFunc(birthYear) AS '만 나이' FROM usertbl;


-- 커서의 처리 순서
-- 커서 이용해 고객의 평균 키 구하는 스토어드 프로시저
use ssgdb;
DROP PROCEDURE IF EXISTS cursorProc;
DELIMITER $$
CREATE PROCEDURE cursorProc()
BEGIN
    DECLARE userHeight INT; -- 고객의 키
    DECLARE cnt INT DEFAULT 0; --  고객의 인원 수 (=읽은 행의 수)
    DECLARE totalHeight INT DEFAULT 0; -- 키의 합계

    DECLARE endOfRow BOOLEAN DEFAULT FALSE; -- 행의 끝 여부(기본을 FALSE)
    DECLARE userCursor CURSOR FOR -- 커서 선언
SELECT height FROM usertbl;
DECLARE CONTINUE HANDLER FOR
        NOT FOUND SET endOfRow = TRUE;   -- 행의 끝이면 endOfRow 변수에 TRUE를 대입

OPEN userCursor; -- 커서 열기

cursor_loop: LOOP
        FETCH userCursor INTO userHeight; -- 고객 키 1개를 대입

        IF endOfRow THEN
            LEAVE cursor_loop;
end if;
        SET cnt = cnt +1;
        SET totalHeight = totalHeight + userHeight;
END LOOP cursor_loop;

    -- 고객 키의 평균을 출력한다.
SELECT CONCAT('고객 키의 평균 ==>', (totalHeight/cnt));

CLOSE userCursor; -- 커서 닫기

END $$
DELIMITER ;

CALL cursorProc();

use ssgdb;
CREATE DATABASE IF NOT EXISTS ssgdb;
use ssgdb;
CREATE TABLE IF NOT EXISTS testTbl (id INT, txt VARCHAR(10));
INSERT INTO testTBL(id,txt) values (1,'레드벨벳'),(2,'잇지'),(3,'블랙핑크');

DELIMITER //
CREATE TRIGGER testTrg
    AFTER DELETE
    ON testtbl
    FOR EACH ROW
BEGIN
    SET @msg = '가수 그룹이 삭제됨';
end //
DELIMITER ;

SET @msg = '';
INSERT INTO testTbl values (4,'마마무');

SET @msg = '';
UPDATE testtbl SET txt='블핑' where id = 3;

SELECT @msg;
DELETE FROM testtbl where id = 4;
SELECT @msg;

-- 트리거의 사용
-- AFTER 트리거의 사용
-- 회원 테이블에 update나 delete를 시도하면 수정 또는 삭제된 데이터를 별도의 테이블에 보관하고 변경된 일자와 변경한 사람을 기록
use ssgdb;
DROP TABLE buyTbl;
CREATE TABLE backup_userTbl(
                               userID CHAR(8) NOT NULL PRIMARY KEY ,
                               name VARCHAR(10) NOT NULL ,
                               birthYear INT NOT NULL,
                               addr CHAR(2) NOT NULL ,
                               mobile1 CHAR(3),
                               mobile2 CHAR(8),
                               height SMALLINT,
                               mDate DATE,
                               modType CHAR(2),     -- 변경된 타입 '수정' 또는 '삭제'
                               modDate DATE,        -- 변경된 날짜
                               modUser VARCHAR(256) -- 변경한 사용자
);
-- AFTER 트리거의 사용
-- 변경(Update) 발생시 작동하는 backUserTbl_UpdateTrg 트리거 생성
DROP TRIGGER IF EXISTS backUserTbl_UpdateTrg;
DELIMITER //
CREATE TRIGGER backUserTbl_UpdateTrg -- 트리거 이름
    AFTER UPDATE -- 변경 후에 작동하도록 지정
    ON usertbl -- 트리거를 부착할 테이블
    FOR EACH ROW
BEGIN
    INSERT INTO backup_userTbl values (OLD.userID,OLD.name, OLD.birthYear, OLD.addr, OLD.mobile1, OLD.mobile2, OLD.height, OLD.mDate, '수정', CURDATE(), CURRENT_USER() );
end //
DELIMITER ;

-- AFTRE 트리거의 사용
-- 삭제(Delte) 발생시 작동하는 backUserTbl_DeleteTrg 트리거 생성
DROP TRIGGER IF EXISTS backUserTbl_DeleteTrg;
DELIMITER //
CREATE TRIGGER backUserTbl_DeleteTrg
    AFTER DELETE
    ON usertbl
    FOR EACH ROW
BEGIN
    INSERT INTO backup_userTbl values (OLD.userID, OLD.name, OLD.birthYear, OLD.addr, OLD.mobile1, OLD.mobile2, OLD.height, OLD.mDate, '삭제', CURDATE(), CURRENT_USER() );
end //
DELIMITER ;

SELECT * FROM usertbl;

UPDATE userTbl SET addr = '몽고' WHERE userID = 'JKW';
DELETE FROM usertbl WHERE height >=177;

SELECT * FROM backup_userTbl;

TRUNCATE TABLE usertbl;

SELECT * FROM backup_userTbl;

-- AFTER 트리거의 사용
-- INSERT 트리거를 생성
DROP TRIGGER IF EXISTS userTbl_InsertTrg;
DELIMITER //
CREATE TRIGGER userTbl_InsertTrg -- 트리거 이름
    AFTER INSERT  -- 입력 후에 작동하도록 지정
    ON usertbl    -- 트리거를 부착할 테이블
    FOR EACH ROW
BEGIN
    SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = '데이터의 입력을 시도했습니다. 귀하의 정보가 서버에 기록되었습니다.';
end //
DELIMITER ;

INSERT INTO usertbl values ('ABC', '에비씨', 1977, '서울', '011', '1111111', 181, '2019-12-25');

CREATE TABLE orderTbl -- 구매 테이블
(orderNo INT AUTO_INCREMENT PRIMARY KEY, -- 구매 일련번호
 userID VARCHAR(5), -- 구매한 회원아이디
 prodName VARCHAR(5), -- 구매한 물건
 orderamount INT );  -- 구매한 개수
CREATE TABLE prodTbl -- 물품 테이블
( prodName VARCHAR(5), -- 물건 이름
  account INT ); -- 남은 물건수량
CREATE TABLE deliverTbl -- 배송 테이블
( deliverNo  INT AUTO_INCREMENT PRIMARY KEY, -- 배송 일련번호
  prodName VARCHAR(5), -- 배송할 물건
  account INT UNIQUE); -- 배송할 물건개수

INSERT INTO prodTbl VALUES('사과', 100);
INSERT INTO prodTbl VALUES('배', 100);
INSERT INTO prodTbl VALUES('귤', 100);
select * from prodTbl;

-- 물품 테이블에서 개수를 감소시키는 트리거
DROP TRIGGER IF EXISTS orderTrg;
DELIMITER //
CREATE TRIGGER orderTrg  -- 트리거 이름
    AFTER INSERT
    ON orderTBL -- 트리거를 부착할 테이블
    FOR EACH ROW
BEGIN
    UPDATE prodTbl SET account = account - NEW.orderamount
    WHERE prodName = NEW.prodName ;
END //
DELIMITER ;

SELECT * FROM prodTbl;
SELECT * FROM orderTbl;

-- 배송테이블에 새 배송 건을 입력하는 트리거
DROP TRIGGER IF EXISTS prodTrg;
DELIMITER //
CREATE TRIGGER prodTrg  -- 트리거 이름
    AFTER  UPDATE
    ON prodTBL -- 트리거를 부착할 테이블
    FOR EACH ROW
BEGIN
    DECLARE orderAmount INT;
    -- 주문 개수 = (변경 전의 개수 - 변경 후의 개수)
    SET orderAmount = OLD.account - NEW.account;
    INSERT INTO deliverTbl(prodName, account)
    VALUES(NEW.prodName, orderAmount);
END //
DELIMITER ;

INSERT INTO orderTbl VALUES (NULL,'JOHN', '배', 5);

SELECT * FROM orderTbl;
SELECT * FROM prodTbl;
SELECT * FROM deliverTbl;

ALTER TABLE deliverTBL CHANGE prodName productName VARCHAR(5);

INSERT INTO orderTbl VALUES (NULL, 'DANG', '사과', 9);

SELECT * FROM orderTbl;
SELECT * FROM prodTbl;
SELECT * FROM deliverTbl;

-- BEFORE 트리거의 사용
-- 테이블 변경이 가해지기 전 작동

-- 트리거 생성
DROP TRIGGER IF EXISTS userTbl_BeforeInsertTrg;
DELIMITER //
CREATE TRIGGER userTbl_BeforeInsertTrg
    BEFORE INSERT
    ON usertbl
    FOR EACH ROW
BEGIN
    IF NEW.birthYear < 1900 THEN
        SET NEW.birthYear = 0;
    ELSEIF NEW.birthYear > YEAR(CURDATE()) THEN
        SET NEW.birthYear = YEAR(CURDATE());
end if ;
end //
DELIMITER ;

INSERT INTO userTbl values ('AAA','에이',1877,'서울','011','1112222',181,'2022-12-25');
INSERT INTO userTbl values ('BBB','비이',2977,'경기','011','1113333',171,'2019-3-25');

SELECT * FROM usertbl;

-- SHOW TRIGGERS 문으로 데이터베이스에 생성된 트리거 확인
SHOW TRIGGERS FROM ssgdb;

-- 트리거 삭제
DROP TRIGGER userTbl_BeforeInsertTrg


