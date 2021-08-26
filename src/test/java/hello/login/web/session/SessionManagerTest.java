package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        MockHttpServletResponse response = new MockHttpServletResponse();

        Member member = new Member();
        member.setLoginId("test");
        member.setName("세션테스트");
        member.setPassword("test!");

        // 세션 생성
        sessionManager.createSession(member, response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        // 요청에 응답 쿠키 저장
        request.setCookies(response.getCookies());

        // 세선 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }

}