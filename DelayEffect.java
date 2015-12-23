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
    float da = 100.0f;
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
        del.input.connect(mlt.output);
        del.delay.set(da);

        adder.inputB.connect(del.output);
        output = adder.output;
    }
    
    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}