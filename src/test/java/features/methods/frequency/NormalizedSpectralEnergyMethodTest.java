package features.methods.frequency;


import features.methods.frequencydomain.NormalizedSpectralEnergyMethod;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NormalizedSpectralEnergyMethodTest {
    public static NormalizedSpectralEnergyMethod classUnderTest;

    @BeforeClass
    public static void InitTest_NormalizedSpectralEnergyMethod(){
        classUnderTest = new NormalizedSpectralEnergyMethod();
    }

    public void Test1_NormalizedSpectralEnergyMethod(){

        double expected = 3.1699;
        double[] data_in = new double[]{
                2,4,6,10,43,12,11,27,83
        };
        double result = classUnderTest.calculeNormalizedSpectralEnergy(data_in);
        assertEquals(expected,result,0);
    }

}
