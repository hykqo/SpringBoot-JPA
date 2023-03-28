package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController //RestController안에는 ResponseBody어노테이션이 있는데, 해당 어노테이션은 데이터를 프론트단으로 보낼떼 json이나 xml로 치환하여 보내주는 기능이다.
@RequiredArgsConstructor
@RequestMapping("api")
public class MemberApiController {

    private final MemberService memberService;


    //양방향 연관관계에서 엔티티를 그대로 넘기면, jackSon 응답을 Json으로 변환하는 과정에서 무한 루프에 빠지게 된다.
    //따라서 Jackson에서 제공하는 @JsonIgnore 라는 어노테이션을 이용해서 특정 값을 직렬화 대상에서 제외시킬 수 있다.
    @GetMapping("v1/members")
    public List<Member> membersV1(){
        List<Member> members = memberService.fineMembers();
        return members;
    }

    //배열자체를 json으로 보내면 안된다. 유연성을 위해서 data라는 콜렉션에 넣어서 반환시켜야 한다.
    @GetMapping("v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.fineMembers();
        List<MemberDTO> collect = findMembers.stream().map(MemberDTO::new).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }
    @Data
    @AllArgsConstructor
    static class MemberDTO{
        private String name;
        public MemberDTO(Member member) { this.name = member.getName();}
    }
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }




    @PostMapping("v1/members") //RequestBody어노테이션은 json으로 받은 데이터를 member객체로 치환해주는 기능이다. 엔티티와 API가 1:1로 매핑되어있다.이러면 안된다. 별도로 DTO로 구성을 해야 한다.
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = Member.JOIN(request.name, new Address("","",""));
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @Data
    @AllArgsConstructor //response파리미터를 객체로 변환해줄때에는 생성자와 Getter이 반드시 있어야 한다.
    static class CreateMemberResponse { private Long id; }
    @Data //request파라미터를 객체로 받을때에는 기본생성자와 Getter이 반드시 있어야 한다.
    static class CreateMemberRequest { @NotEmpty private String name; }


    //Put : 같은 url이면 한번만 실행된다.
    @PutMapping("v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName()); //커맨드와 쿼리를 분리해야 한다.
        Member findMember = memberService.findOne(id); //ex : 업데이트 기능과 조회기능을 분리시킨다.
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse { private Long id; private String name;}
    @Data
    static class UpdateMemberRequest { private String name;}
}
