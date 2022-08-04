package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean(){
        String[] beaDefinitionNames = ac.getBeanDefinitionNames();
        for (String beaDefinitionName : beaDefinitionNames) {
            Object bean = ac.getBean(beaDefinitionName);
            System.out.println("name = " + beaDefinitionName + " object = " + bean);
        }

    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean(){
        String[] beaDefinitionNames = ac.getBeanDefinitionNames(); // 스프링에 등록된 모든 빈 이름을 조회한다.
        for (String beaDefinitionName : beaDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beaDefinitionName);    // bean 메타데이터 정보


            // Role ROLE_APPLICATION :  직접 등록한 애플리케이션 빈
            // Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beaDefinitionName); // 빈 이름으로 객체를 조회한다.
                System.out.println("name = " + beaDefinitionName + " object = " + bean);
            }

        }
    }

}
