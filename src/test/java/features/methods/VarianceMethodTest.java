package features.methods;

import features.methods.timedomain.VarianceMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VarianceMethodTest {

    public static VarianceMethod classUnderTest;

    @BeforeClass
    public static void InitTest_RootMeanSquareTest(){

        classUnderTest = new VarianceMethod();

    }

    @Test
    public void Test1_VarianceMethod(){

        double expected = 691.5;
        double[] data_in = new double[]{
                2,4,6,10,43,12,11,27,83
        };
        double result = classUnderTest.calculeVariance(data_in);
        assertEquals(expected,result,0.001);
    }
    @Test
    public void Test2_VarianceMethod(){

        double expected = 691.5;
        double[] data_in = new double[]{
                -2,-4,-6,-10,-43,-12,-11,-27,-83
        };
        double result = classUnderTest.calculeVariance(data_in);
        assertEquals(expected,result,0.001);
    }
    @Test
    public void Test3_VarianceMethod(){

        double expected = 0;
        double[] data_in = new double[]{
                0,0,0,0,0,0,0,0,0
        };
        double result = classUnderTest.calculeVariance(data_in);
        assertEquals(expected,result,0.001);
    }
}
