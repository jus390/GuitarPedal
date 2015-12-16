import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

/**
 * Created by Jure on 16.12.2015.
 */

public class Delay extends Circuit implements UnitSource {

    public final UnitInputPort input;
    public final UnitOutputPort output;

    SineOscillator osc;
    Multiply mlt;
    Multiply delayMultiplyer;
    InterpolatingDelay del;
    Add adder;
    float inGain = 1;
    float delayGain = 1;
    float da = 0.3f;

    public Delay(){

        adder = new Add();
        add(adder);
        input = adder.inputA;


        /* Multiply for first gain -> gain of basic input*

         */
        mlt=new Multiply();
        add(mlt);

        mlt.inputA.connect(adder.output);
        mlt.inputB.set(inGain);

        output = mlt.output;

        del = new InterpolatingDelay();
        del.input.connect(mlt.output);
        del.delay.set(da);

        delayMultiplyer = new Multiply();

        delayMultiplyer.inputA.connect(del.output);
        delayMultiplyer.inputB.set(delayGain);

        adder.inputB.connect(delayMultiplyer.output);
        



    }

    public void setAmplitude(double amp) {
        osc.amplitude.set(amp);
    }

    public void setFrequency(double freq) {
        osc.frequency.set(freq);
    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}