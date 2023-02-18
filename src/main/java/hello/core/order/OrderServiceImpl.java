package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 고정 할인 정책

    // 단일 체계 원칙을 잘 지킨 구현 방법임(할인가격과 주문의 관심사의 분리)
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); // 주문 생성 요청이 오면 회원 정보를 먼저 조회
        int discountPrice = discountPolicy.discount(member, itemPrice); // 그 후 할인정책에 회원을 넘김으로써 할인가격을 알아냄

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
