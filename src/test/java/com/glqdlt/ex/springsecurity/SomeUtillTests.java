package com.glqdlt.ex.springsecurity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author glqdlt
 * 2019-02-17
 */
public class SomeUtillTests {

    @Test
    public void List_를콤마로쉽게변환() throws Exception {
        final String expected = "aaa0,aaa1,aaa2,aaa3,aaa4,aaa5,aaa6,aaa7,aaa8,aaa9,aaa10";
        List<String> datas = IntStream.rangeClosed(0, 10)
                .boxed()
                .map(x -> "aaa" + x)
                .collect(Collectors.toList());

        String commaString = StringUtils.collectionToCommaDelimitedString(datas);
        Assert.assertEquals(expected,commaString);
    }
}
