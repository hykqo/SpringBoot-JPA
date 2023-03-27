package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController //RestController안에는 ResponseBody어노테이션이 있는데, 해당 어노테이션은 데이터를 프론트단으로 보낼떼 json이나 xml로 치환하여 보내주는 기능이다.
@RequiredArgsConstructor
@RequestMapping("api")
public class MemberApiController {

    private final MemberService memberService;

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
    @AllArgsConstructor
    static class CreateMemberResponse { private Long id; }

    @Data //request파라미터를 객체로 받을때에는 기본생성자와 Getter이 반드시 있어야 한다.
    static class CreateMemberRequest { @NotEmpty private String name; }
}
