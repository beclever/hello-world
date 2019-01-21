package org.example;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author esnahow
 * @date 01/17/2019 17:57
 */
public class HelloWorldTest {

	private static int num;

    @Before
    public void setNum(){
        num =1;
    }

    @Test
    public void test1(){
        assertEquals(1,num);
    }
}
