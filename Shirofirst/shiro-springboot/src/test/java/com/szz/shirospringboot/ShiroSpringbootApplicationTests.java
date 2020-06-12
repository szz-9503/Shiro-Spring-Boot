package com.szz.shirospringboot;

import com.szz.shirospringboot.service.UserServiceimpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Autowired
    UserServiceimpl userServiceimpl;

    @Test
    void contextLoads() {
        System.out.println(userServiceimpl.queryUserByName("szz"));;
    }

}
