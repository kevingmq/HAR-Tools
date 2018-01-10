package features.methods;

import features.methods.timedomain.StandardDeviationMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StandardDeviationMethodTest {

    public static StandardDeviationMethod classUnderTest;

    @BeforeClass
    public static void InitTest_RootMeanSquareTest(){

        classUnderTest = new StandardDeviationMethod();

    }

    @Test
    public void Test1_StandardDeviationMethod(){

        double expected = 26.2964;
        double[] data_in = new double[]{
                2,4,6,10,43,12,11,27,83
        };
        double result = classUnderTest.calculeStandardDeviation(data_in);
        assertEquals(expected,result,0.001);
    }
    @Test
    public void Test2_StandardDeviationMethod(){

        double expected = 26.2964;
        double[] data_in = new double[]{
                -2,-4,-6,-10,-43,-12,-11,-27,-83
        };
        double result = classUnderTest.calculeStandardDeviation(data_in);
        assertEquals(expected,result,0.001);
    }
    @Test
    public void Test3_StandardDeviationMethod(){

        double expected = 0;
        double[] data_in = new double[]{
                0,0,0,0,0,0,0,0,0
        };
        double result = classUnderTest.calculeStandardDeviation(data_in);
        assertEquals(expected,result,0.001);
    }
}
