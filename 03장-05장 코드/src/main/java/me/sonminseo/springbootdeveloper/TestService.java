package me.sonminseo.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TestService {
    @Autowired
    MemberRepository memberRepository; // 빈 주입받음

    /*    public List<Member> getAllMembers(){
            return memberRepository.findAll(); // 멤버 테이블에 저장된 멤버 목록을 모두 가져옴

        }*/
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
