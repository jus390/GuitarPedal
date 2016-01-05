package guitarpedalgui;

import com.jsyn.engine.*;
import com.jsyn.unitgen.UnitFilter;

public class CompressorEffect2 extends UnitFilter{

    private double prev=0;
    private double max=0.1;
    private double multip=0.2;

    @Override
    public void generate( int start, int limit )
    {
        // Get signal arrays from ports.
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for( int i = start; i < limit; i++ )
        {
            double v = inputs[i];

            if(v<prev && prev>0.0){
                max=(max+(prev-max)*0.8);
                multip=max;
                System.out.println(v/multip);
            }else{
                max=(max+(0.0-max)*0.8);
                multip=max;
            }
            prev=v;


            outputs[i] = (-1/(8*multip+1))+1;
        }
    }

}
