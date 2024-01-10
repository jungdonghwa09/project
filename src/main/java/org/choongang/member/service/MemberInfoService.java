package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FileInfoService fileInfoService;
    private final EntityManager em;
    private final HttpServletRequest request;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username) // 이메일 조회
                .orElseGet(() -> memberRepository.findByUserId(username) // 아이디로 조회
                        .orElseThrow(() -> new UsernameNotFoundException(username)));

        List<SimpleGrantedAuthority> authorities = null;
        List<Authorities> tmp = member.getAuthorities();
        if (tmp != null) {
            authorities = tmp.stream()
                    .map(s -> new SimpleGrantedAuthority(s.getAuthority().name()))
                    .toList();
        }
    //프로필 이미지 처리
        List<FileInfo> files = fileInfoService.getListDone(member.getGid());
        if(files != null && !files.isEmpty()){
            member.setProfileImage(files.get(0));
        }

        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }

    public ListData<Member> getList(MemberSearch search){
        int page = Utils.onlyPositiveNumber(search.getPage(),1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(),20);
        int offset = (page-1)*limit;//레코드 시작번호
        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;
        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class,
                "member");
        List<Member> items = new JPAQueryFactory(em)
                .selectFrom(member)
                .leftJoin(member.authorities)
                .fetchJoin()
                .where(andBuilder)
                .limit(limit)
                .offset(offset)
                .orderBy(new OrderSpecifier(Order.DESC,pathBuilder
                        .get("createdAt")))
                .fetch();

        //페이징처리
        int total = (int)memberRepository.count(andBuilder);//총 레코드 수
        total=1234;
        Pagination pagination = new Pagination(page,total,10,
                limit, request);

        return new ListData<>(items,pagination);
    }
}