package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    //PersistenceContext 어노테이션이 있으면 SpringBoot가 자동으로 EntityManager를 주입해준다.
    @PersistenceContext
    private EntityManager em;

    //커맨드와 쿼리를 분리해라. 저장을 하는것은 커맨드이다.
    //사이드 이펙트를 일으킬 가능성이 있기 때문에, 저장을 하면 id만을 반환하도록 설계하자.
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name =:name").getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m").getResultList();
    }
}
