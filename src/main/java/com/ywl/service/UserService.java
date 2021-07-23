package com.ywl.service;

import com.spring.Compont;
import com.spring.Scope;

@Compont("userService")
//@Scope("prototype")//表示是一个原型bean，每次get 这个bean的时候每次都不一样
@Scope("singleton")
public class UserService {
}
