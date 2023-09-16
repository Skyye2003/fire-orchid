package com.lan.src;

import com.lan.src.utils.ParseUtils;
import com.lan.src.utils.StrUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilsTest {
    @Test
    public void testParseUtils(){
        String str = "aaaty441";
        String string = StrUtils.subStr(str);
        TestEntity result = (TestEntity)ParseUtils.parseAttribute(string, TestEntity.class);
        System.out.println(result);
    }
}
