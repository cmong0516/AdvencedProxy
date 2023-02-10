package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

//    @Bean
    // 스프링은 AnnotationAwareAspectJAutoProxyCreator 라는 빈 후처리기를 자동으로 등록해준다.
    // 포인트컷은 프록시 적용 여부와 어드바이스 적용 여부를 판단해준다.
    // 따라서 패키지와 메서드 이름을 정밀하게 지정하여야 한다.
    // AspectJExpressionPointcut
    public Advisor advisor1(LogTrace logTrace) {
        //pointcut
        // 스프링에서 제공하는 NameMatchMethodPointcut 을 통해 request* order* save* 과 일치하면 advice 실행.
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
        // Advisor 의 대표적 구현체는 DefaultPointcutAdvisor 에 포인트컷과 Advice 를 넣어 반환한다.
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        // hello.proxy.app 와 하위 모든 패키지의 모든 메서드.
        pointcut.setExpression("execution(* hello.proxy.app..*(..))");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        // hello.proxy.app 하위의 모든 패키지
        // *(..) 모든 메서드
        // && !execution(* hello.proxy.app..noLog(..) noLog 는 제외한다.
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
