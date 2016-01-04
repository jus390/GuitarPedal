import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

/**
 * Created by jus390 on 12/27/15.
 */
public class ChorusEffect extends Circuit implements UnitSource {

    public final UnitInputPort input;
    public final UnitOutputPort output;

    Multiply masterIn;
    TriangleOscillator osc;
    InterpolatingDelay delay;
    Multiply chorusGain;
    Add adder;
    Add chorUp;
    MixerMonoRamped mix;

    public ChorusEffect(){

        osc=new TriangleOscillator();
        osc.amplitude.set(0.01);
        osc.frequency.set(0.5);
        add(osc);

        chorUp=new Add();
        osc.output.connect(chorUp.inputA);
        chorUp.inputB.set(0.01);


        masterIn=new Multiply();
        add(masterIn);
        input=masterIn.inputA;
        masterIn.inputB.set(1);

        delay=new InterpolatingDelay();
        delay.allocate(2*88200);
        add(delay);

        chorUp.output.connect(delay.delay);

        masterIn.output.connect(delay.input);
        chorusGain=new Multiply();
        chorusGain.inputB.set(0.8);
        add(chorusGain);

        delay.output.connect(chorusGain.inputA);

        adder=new Add();
        add(adder);
        //adder.input[0];
        mix=new MixerMonoRamped(2);
        add(mix);
        mix.input.connect(0,chorusGain.output,0);
        mix.input.connect(1,masterIn.output,0);
        //chorusGain.output.connect(adder.inputA);
        //adder.inputB.connect(masterIn.output);
        //adder.fade.set(0);

        output=mix.output;

    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
