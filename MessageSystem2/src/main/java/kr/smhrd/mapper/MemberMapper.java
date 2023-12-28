package kr.smhrd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.smhrd.entity.Member;

// 결국은 MemberMapper라는 클래스도 Spring Container로 올라가게됨
// mapper파일임을 알려줘야함
@Mapper // 어떤 패키지에 있는 mapper파일을 읽을건지?? -> root-context.xml
public interface MemberMapper { // 틀, SqlSessionFactoryBean이 MemberMapper를 implement 해서 사용

	public void memberInsert(Member member);
	
	public void login(String id, String pw);

	public Member memberSelect(Member member);

	//.xml 파일에 넘기지 않고 interface 안에서 바로 쿼리문 실행하기
	//이렇게 하려면 emailCheck라는 함수가 .xml에는(.xml에서는 id값) 없어야 한다.
	@Select("select * from member where email=#{email}")
	public Member emailCheck(String inputE);

	public int updateMember(Member member);

	public List<Member> showMember();

	public void showMember(String email);

	public void deleteMember(String email);
	
}

