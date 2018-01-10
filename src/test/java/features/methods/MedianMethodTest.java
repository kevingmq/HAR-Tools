package features.methods;
import features.methods.timedomain.MeanMethod;
import features.methods.timedomain.MedianMethod;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MedianMethodTest {
    public static MedianMethod classUnderTest;

    @BeforeClass
    public static void InitTest_MedianMethod(){
        classUnderTest = new MedianMethod();
    }

    @Test
    public void Test1_MedianMethod(){

        double expected = 11;
        double[] data_in = new double[]{
                2,4,6,10,43,12,11,27,83
        };
        double result = classUnderTest.calculeMedian(data_in);
        assertEquals(expected,result,0);
    }

    @Test
    public void Test2_MedianMethod(){

        double expected = -11;
        double[] data_in = new double[]{
                -2,-4,-6,-10,-43,-12,-11,-27,-83
        };
        double result = classUnderTest.calculeMedian(data_in);
        assertEquals(expected,result,0);
    }
    @Test
    public void Test3_MedianMethod(){

        double expected = 0;
        double[] data_in = new double[]{
                0,0,0,0,0,0,0,0,0
        };
        double result = classUnderTest.calculeMedian(data_in);
        assertEquals(expected,result,0);
    }
}
