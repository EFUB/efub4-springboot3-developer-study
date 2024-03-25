package me.sonminseo.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    public void test() {
        memberRepository.save(new Member(1L, "A")); // 생성 (C)

        Optional<Member> member = memberRepository.findById(1L); // 조회 (R)
        List<Member> allMembers = memberRepository.findAll(); // 조회 (R)

        memberRepository.deleteById(1L); // 삭제 (D)
    }
}