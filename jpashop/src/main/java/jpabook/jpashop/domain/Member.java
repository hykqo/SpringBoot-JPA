package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private  String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public static Member JOIN(String name, Address address){
        return new Member(name, address);
    }

    private Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public void updateName(String name){
        this.name = name;
    }

}
