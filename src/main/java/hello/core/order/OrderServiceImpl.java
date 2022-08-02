package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // interface에 의존함 DIP 지킴
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        // 1. 회원 정보 조회
        Member member = memberRepository.findById(memberId);

        // 할인 정책 적용된 가격
       int discountPrice = discountPolicy.discount(member,itemPrice);

       // 최종 주문 반환
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
}
