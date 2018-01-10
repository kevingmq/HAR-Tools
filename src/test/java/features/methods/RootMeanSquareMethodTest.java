package features.methods;
import features.methods.timedomain.RootMeanSquareMethod;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RootMeanSquareMethodTest {

    public static RootMeanSquareMethod classUnderTest;

    @BeforeClass
    public static void InitTest_RootMeanSquareTest(){

        classUnderTest = new RootMeanSquareMethod();

    }

    @Test
    public void Test1_RootMeanSquareMethod(){

        double expected = 33.1461;
        double[] data_in = new double[]{
                2,4,6,10,43,12,11,27,83
        };
        double result = classUnderTest.calculeRootMeanSquare(data_in);
        assertEquals(expected,result,0.001);
    }
    @Test
    public void Test2_RootMeanSquareMethod(){

        double expected = 33.1461;
        double[] data_in = new double[]{
                -2,-4,-6,-10,-43,-12,-11,-27,-83
        };
        double result = classUnderTest.calculeRootMeanSquare(data_in);
        assertEquals(expected,result,0.001);
    }
    @Test
    public void Test3_RootMeanSquareMethod(){

        double expected = 0;
        double[] data_in = new double[]{
                0,0,0,0,0,0,0,0,0
        };
        double result = classUnderTest.calculeRootMeanSquare(data_in);
        assertEquals(expected,result,0.001);
    }
}
