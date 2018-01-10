package features.methods.frequency;


import features.methods.frequencydomain.SpectralEntropyMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpectralEntropyMethodTest {
    public static SpectralEntropyMethod classUnderTest;

    @BeforeClass
    public static void InitTest_DCComponentMethod(){
        classUnderTest = new SpectralEntropyMethod();
    }

    public void Test1_InformationEntropyMethod(){

        double expected = 3.1699;
        double[] data_in = new double[]{
                2,4,6,10,43,12,11,27,83
        };
        double result = classUnderTest.calculeSpectralEntropy(data_in);
        assertEquals(expected,result,0);
    }

}
