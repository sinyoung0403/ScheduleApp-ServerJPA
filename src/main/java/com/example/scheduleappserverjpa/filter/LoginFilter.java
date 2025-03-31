package com.example.scheduleappserverjpa.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.http.HttpConnectTimeoutException;

public class LoginFilter implements Filter {

  private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login"};

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
          throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    // request 에서 url 가져오기.
    String requestURL = httpServletRequest.getRequestURI();

    // response 도 다운 캐스팅
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

    // 화이트 리스트에 있지 않은 url 의 경우에는 로그인 여부를 확인해야한다.
    if (!isWhiteList(requestURL)) {
      // 만약 체크해야할 url 이라면,
      HttpSession session = httpServletRequest.getSession(false);

      // 세션이 없거나, 세션 키가 널이라면, 로그인 되지 않은 것.
      if (session == null || session.getAttribute("loginUser") == null) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
      }
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  // 화이트 리스트 안에 있는 값과 현재 url 을 비교하는 것.
  public boolean isWhiteList(String string) {
    return PatternMatchUtils.simpleMatch(WHITE_LIST, string);
  }
}
