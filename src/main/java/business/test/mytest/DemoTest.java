package business.test.mytest;

import annotation.AnnotationTest;
import org.testng.annotations.Test;

public class DemoTest extends AnnotationTest {
    @Test
    public void test1() {
        System.out.println("mytest");
    }

}
