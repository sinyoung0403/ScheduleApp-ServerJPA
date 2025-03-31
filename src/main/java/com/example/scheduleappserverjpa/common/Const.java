package com.example.scheduleappserverjpa.common;

public interface Const {
  // 세션을 설정할 때, 가져올 때 저런식의 id 값을 설정해야하는데,
  // 그냥 스트링으로 주게 되면 실수가 생길 수 있으니 상수로 만드는 것이다.
  String LOGIN_USER = "loginUser";
}
