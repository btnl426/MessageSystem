drop table member;

-- Member 테이블 생성
create table member(
	email varchar(100) not null,
	pw varchar(100) not null,
	tel varchar(100) not null,
	address varchar(100) not null,
	primary key(email)
);

-- test 데이터
insert into member(email, pw, tel, address)
values('admin@smhrd.com', '1234', '010-0000-0000', '서구 금호동');

select * from member;

