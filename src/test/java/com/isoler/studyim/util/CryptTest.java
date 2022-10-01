package com.isoler.studyim.util;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptTest {

    @Test
    public void encrypt(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String admin = bCryptPasswordEncoder.encode("admin");
        System.out.println(admin);

    }
}
