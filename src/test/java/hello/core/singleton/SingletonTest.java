package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {


    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        // 1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();


        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService != memberServcie2
        // 호출할 때마다 객체를 다시 생성하는것은 정말 비효율적이다.
        // 우리가 만들었던 스프링 없는 순수한 di컨테이너 AppConfig는 요청할때마다 객체를 새로 생성한다.
        // 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다 -> 메모리 낭비
        // 해결방안은 해당 객체가 딱 1개만 생성되고 공유되도록 설계하면 된다. -> 싱글톤 패턴턴
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }


    // 스프링 컨테이너에서는 해당 싱글톤 패턴을 제공해줌
    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonService(){
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + instance1);
        System.out.println("singletonService2 = " + instance2);

        Assertions.assertThat(instance1).isSameAs(instance2);

        instance1.logic();
        instance2.logic();

    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
//        AppConfig appConfig = new AppConfig();
        // 스프링 컨테이너에서 이미 싱글톤을 적용해줌 -> 스프링은 기본적으로 싱글톤 방식
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService",MemberService.class);
        MemberService memberService2 = ac.getBean("memberService",MemberService.class);


        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }

}
