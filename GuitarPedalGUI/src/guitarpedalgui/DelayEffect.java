package guitarpedalgui;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

/**
 * Created by Jure on 16.12.2015.
 */

public class DelayEffect extends Circuit implements UnitSource {
    /* Basic schema: https://en.wikipedia.org/wiki/Delay_%28audio_effect%29 */
    public final UnitInputPort input;
    public final UnitOutputPort output;

    SineOscillator osc;
    Multiply mlt;
    Multiply delayMultiplyer;
    InterpolatingDelay del;
    Add adder;
    float inGain = 1;
    float delayGain = 1;
    double da = 0.5;

    public DelayEffect(){

        adder = new Add();
        mlt = new Multiply();
        del = new InterpolatingDelay();
        add(adder);
        add(mlt);
        add(del);

        input = mlt.inputA;
        mlt.inputB.set(1.0);


        adder.inputA.connect(mlt.output);
        del.allocate(88200);
        del.delay.set(da);
        del.input.connect(mlt.output);


        adder.inputB.connect(del.output);
        output = adder.output;
    }
    
    public void setDelay(double sec){
        del.delay.set(sec);
    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}