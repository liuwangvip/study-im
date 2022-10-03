package com.isoler.studyim.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptTest {

    @Test
    public void encrypt() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String admin = bCryptPasswordEncoder.encode("admin");
        System.out.println(admin);

    }

    @Test
    public void testContain() {
        System.out.println(StringUtils.contains(null, "aaaa"));
        System.out.println(StringUtils.contains("aaacccbbbb", "cccc"));
        System.out.println(StringUtils.contains("aaacccbbbb", "ccc"));
    }
}
