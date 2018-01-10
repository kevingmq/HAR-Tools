package features.methods.frequency;
import features.methods.frequencydomain.DCComponetMethod;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DCComponentMethodTest {
    public static DCComponetMethod classUnderTest;

    @BeforeClass
    public static void InitTest_DCComponentMethod(){
        classUnderTest = new DCComponetMethod();
    }
    @Test
    public void Test1_DCComponentMethod(){

        double expected = 22;
        double[] data_in = new double[]{
                2,4,6,10,43,12,11,27,83
        };
        double result = classUnderTest.calculeDCComponent(data_in);
        assertEquals(expected,result,0);
    }
    @Test
    public void Test2_DCComponentMethod(){

        double expected = -22;
        double[] data_in = new double[]{
                -2,-4,-6,-10,-43,-12,-11,-27,-83
        };
        double result = classUnderTest.calculeDCComponent(data_in);
        assertEquals(expected,result,0);
    }
    @Test
    public void Test3_DCComponentMethod(){

        double expected = 0;
        double[] data_in = new double[]{
                0,0,0,0,0,0,0,0,0
        };
        double result = classUnderTest.calculeDCComponent(data_in);
        assertEquals(expected,result,0);
    }
}
