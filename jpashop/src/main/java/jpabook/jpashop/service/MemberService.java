package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final로 잡혀있는 객체의 생성자를 만들어주고 Di를 해준다.
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        if(!memberRepository.findByName(member.getName()).isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> fineMembers(){
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.updateName(name);
    }
}
