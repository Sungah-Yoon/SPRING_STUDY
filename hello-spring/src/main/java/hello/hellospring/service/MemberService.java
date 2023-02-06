package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {

    // *** DI (Dependency Injection) ***
    // MemberService 의 입장, 내가 직접 new 해서 객체를 생성하지 X
    //                       외부에서 memberRepository 를 넣어줌
    //   -> 이런 것을 의존관계 주입 즉, DI 라고 함

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원 가입
     */
    public Long join(Member member) {

        //같은 이름이 있는 중복 회원 X
        validateDuplicateMember(member); // 중복 회원 검증 -> 함수로 따로 빼는 것이 좋음 (ctrl + Alt + m)

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
