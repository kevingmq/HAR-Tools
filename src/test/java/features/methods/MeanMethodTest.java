package features.methods;
import features.methods.timedomain.MeanMethod;
import org.junit.Test;
import static org.junit.Assert.*;



public class MeanMethodTest {
    @Test
    public void Test1_MeanMethod(){
        MeanMethod classUnderTest = new MeanMethod();
        double expected = 22;
        double[] data_in = new double[]{
                2,4,6,10,43,12,11,27,83
        };
        double result = classUnderTest.calculeMean(data_in);
        assertEquals(expected,result,0);
    }
    @Test
    public void Test2_MeanMethod(){
        MeanMethod classUnderTest = new MeanMethod();
        double expected = -22;
        double[] data_in = new double[]{
                -2,-4,-6,-10,-43,-12,-11,-27,-83
        };
        double result = classUnderTest.calculeMean(data_in);
        assertEquals(expected,result,0);
    }
    @Test
    public void Test3_MeanMethod(){
        MeanMethod classUnderTest = new MeanMethod();
        double expected = 0;
        double[] data_in = new double[]{
                0,0,0,0,0,0,0,0,0
        };
        double result = classUnderTest.calculeMean(data_in);
        assertEquals(expected,result,0);
    }
}
