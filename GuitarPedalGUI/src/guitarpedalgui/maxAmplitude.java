/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitarpedalgui;

import com.jsyn.unitgen.UnitFilter;

/**
 *
 * @author Juš
 */
public class maxAmplitude extends UnitFilter{

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
                max=(max+(prev-max)*0.4);
                multip=max;
                //System.out.println(v/multip);
            }else{
                max=(max+(0.0-max)*0.4);
                multip=max;
            }
            prev=v;


            outputs[i] = multip*2;
        }
    }

}
