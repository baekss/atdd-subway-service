package nextstep.subway;

import nextstep.subway.auth.application.AuthorizationException;
import nextstep.subway.auth.domain.LoginMember;

import org.aspectj.lang.ProceedingJoinPoint;

// @Aspect
// @Component
public class Authentication {

    // @Around("execution(* nextstep.subway.auth.application.AuthService.findMemberByToken(..))")
    public Object accessCheck(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed();
            if (returnValue instanceof LoginMember) {
                LoginMember member = (LoginMember)returnValue;
                checkMember(member);
            }
        } catch (Throwable t) {
            throw new AuthorizationException("인증에 실패하였습니다");
        }
        return returnValue;
    }

    private void checkMember(LoginMember member) {
        if (member.getId() == null) {
            throw new RuntimeException();
        }
    }
}
