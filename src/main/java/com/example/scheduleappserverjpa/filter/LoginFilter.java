package com.example.scheduleappserverjpa.filter;

import com.example.scheduleappserverjpa.exception.UnauthorizedAccessException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.http.HttpConnectTimeoutException;

public class LoginFilter implements Filter {

  private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login"};

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain)
          throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    // request URL Get
    String requestURL = httpServletRequest.getRequestURI();
    try {
      // 화이트 리스트 여뷰 확인
      if (!isWhiteList(requestURL)) {
        HttpSession session = httpServletRequest.getSession(false);

        // 세션이 없거나 세션 키가 널이라면, 로그인 되지 않은 것.
        if (session == null || session.getAttribute("loginUser") == null) {
          throw new RuntimeException();
        }
      }
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (RuntimeException ex) {
      HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
      httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpServletResponse.setContentType("application/json");
      httpServletResponse.setCharacterEncoding("UTF-8");
      httpServletResponse.getWriter().write("로그인 후 이용 가능합니다.");
    }
  }

  public boolean isWhiteList(String string) {
    return PatternMatchUtils.simpleMatch(WHITE_LIST, string);
  }
}