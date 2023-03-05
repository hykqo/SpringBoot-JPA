package jpabook.jpashop.service;

import jpabook.jpashop.MemberRepository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//readOnly = true로 하면 읽기전용 트랜잭션으로 좀더 최적화를 해준다.- salect이 서비스내에 더 많으면 상단에 default로 설정해주자.
//@AllArgsConstructor //생성자를 기본으로 생성해준다.
@RequiredArgsConstructor // final로 잡혀있는 객체의 생성자를 만들어주고 Di를 해준다.
public class MemberService {

    //field 주입 방식
//    @Autowired
    //final로 설정을 해두면, 컴파일 시점에 생성자가 잘 주입되었는지 체크해주기 때문에, 꼭 지정해주자.
    private final MemberRepository memberRepository;

//    @Autowired
    //setter 주입 방식 - 테스트 코드를 작성할때에는 괜찮으나, 중요한 이슈가 있다.
    //기본적으로 DI는 어플리케이션 로딩시점에 완료가 되어야 한다. 런타임 도중에 DI를 하는것은 중요한 이슈를 런타임으로 미루는 안좋은 방식이다.
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //생성자 주입 방식 - 선호하는 방 - 처음 어플리케이션 로딩시점에 DI가 된다. - 요즘에는 생성자가 하나만 있는 경우에는 스프링이 자동으로 Di를 해준다. 따라서 @Autowired가 필요없다.
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        //해당 문제는 멀티쓰레드(동시성) 상황을 고려해서 데이터 베이스의 회원 이름을 유니크 제약조건으로 걸어두어야 한다.
        if(memberRepository.findByName(member.getName()).isEmpty()){
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
}
