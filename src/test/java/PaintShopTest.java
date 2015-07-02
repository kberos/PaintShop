import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by kber0001 on 01/07/2015.
 */
public class PaintShopTest {
    @Test
    public void testCase1(){
        PaintShop paintShop = new PaintShop();
        String result  = paintShop.processOrder("input1.txt");
        assertEquals("G G G G M", result);
    }

    @Test
    public void testCase2(){
        PaintShop paintShop = new PaintShop();
        String result  = paintShop.processOrder("input2.txt");
        assertEquals("No solution exists", result);
    }

    @Test
    public void testCase3(){
        PaintShop paintShop = new PaintShop();
        String result  = paintShop.processOrder("input3.txt");
        assertEquals("G M G M G", result);
    }

    @Test
    public void testCase4(){
        PaintShop paintShop = new PaintShop();
        String result  = paintShop.processOrder("input4.txt");
        assertEquals("M M", result);
    }
}
