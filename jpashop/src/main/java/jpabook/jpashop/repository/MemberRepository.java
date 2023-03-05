package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext //springDataJpa를 사용하면 @Autowired로 바꿀 수 있다. 따라서 다른 서비스와 일관성 있게 주입해줄 수 있다.
    private final EntityManager em; //엔티티메내저 인젝션

    public void save(Member member){ em.persist(member); }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name =: name ", Member.class)
                .setParameter("name", name).getResultList();

    }
}
