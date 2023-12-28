package kr.smhrd.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.smhrd.entity.Member;
import kr.smhrd.mapper.MemberMapper;

// POJO를 찾기위해 @(어노테이션)으로 Controller라고 명시해야 함
// 어떤 패키지에서 Controller를 찾을 건지 servlet-context.xml 파일에도 명시해야 함
@Controller
public class MemberController {

	// class 안에 있는 메소드를 쓰려면 '객체생성' 필요함, interface는 객체생성도 안됨 -> but 스프링에서는 생성하는게 아니라 주입받아서 씀
	@Autowired // 스프링 컨테이너에 객체가 생성되어 올라간 boardMapper 객체를 주입받아 사용하겠다
	private MemberMapper memberMapper; // DAO같은 역할인데 DAO는 커넥션 관리까지 다했다면
	
	@RequestMapping("/")
	public String main() {
		return "Main";
	}
	
	// 회원가입 /memberInsert
	@RequestMapping("/memberInsert")
	public String memberInsert(Member member, Model model) {
		System.out.println(member.toString());
		// DB에 회원정보 삽입하기
		memberMapper.memberInsert(member); // 인터페이스는 추상메소드만 존재
		model.addAttribute("email", member.getEmail());
//		model.addAttribute("email", model);
		return "JoinSuccess";
	}
	
	//로그인 / memberSelect
	@RequestMapping("/memberSelect")
	public String memberSelect(Member member, HttpSession session) { // email, pw
		Member loginMember = memberMapper.memberSelect(member);
		session.setAttribute("loginMember", loginMember);
		return "Main";
	}
	
	//로그아웃
	@RequestMapping("/LogoutMember")
	public String LogoutMember(HttpSession session) {
		session.invalidate();
		return "Main";
		//return "redurect:/";
	}
	
	//Email 중복체크
	@RequestMapping("emailCheck")
	public @ResponseBody boolean emailCheck(@RequestParam("inputE") String inputE) {
		Member member = memberMapper.emailCheck(inputE);
		if(member != null) {
			//사용 불가능한 이메일
			return false;
		}else {
			//사용 가능한 이메일
			return true;
		}
	}
	
	//화원수정 페이지로 이동 / goUpdatePage
	@RequestMapping("/goUpdateMember")
	public String goUpdateMember() {
		return "UpdateMember";
	}
	
	//회원수정기능 /updateMemeber
	@RequestMapping("/updateMember")
	public String updateaMember(Member member, HttpSession session) {
//	public String updateaMember(Member member) {
	//	Member loginMember =(Member)session.getAttribute("loginMember");
		//String email = loginMember.getEmail();
		int cnt = memberMapper.updateMember(member);
		if(cnt>0) { //수정 성공시 -> Main.jsp
			session.setAttribute("loginMember", member);
			return "Main";
		}else { //수정 실패시 -> UpdateMember.jsp
			return "UpdateMember";
		}
	}
	
	//회원정보 보는 페이지로 이동 / showMember
	//회원정보 보는 페이지로 이동 + DB에 있는 회원 조회/ showMember
	@RequestMapping("/GoShowMember")
	public String showMember(Model model) {
		List<Member> list = memberMapper.showMember();
		System.out.println(list.size());
		model.addAttribute("list",list);
		return "ShowMember";
	}
	
	//회원삭제/deleteMember
	@RequestMapping("/deleteMember")
	public String deleteMember(@RequestParam("email") String email) { //deleteMember?email=~~
		memberMapper.deleteMember(email);
		//return "ShowMember"; 
		return "redirect:/GoShowMember";//이렇게 하면 바로 위에 @RequestMapping("/showMember")로 이동해서 db의 값 다시 불러옴 
	}
	
}
