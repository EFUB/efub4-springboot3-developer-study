package me.gkdudans.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public void test(){
        //생성
        memberRepository.save(new Member(1L, "A"));
        //조회
        Optional<Member> member = memberRepository.findById(1L); //단건 조회
        List<Member> allMembers = memberRepository.findAll(); //전체 조회
        //삭제
        memberRepository.deleteById(1L);
    }
}
