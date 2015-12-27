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
    SineOscillator osc;
    InterpolatingDelay delay;
    Multiply chorusGain;
    Add adder;

    public ChorusEffect(){

        osc=new SineOscillator();
        osc.amplitude.set(0.3);
        osc.frequency.set(5);
        add(osc);


        masterIn=new Multiply();
        add(masterIn);
        input=masterIn.inputA;
        masterIn.inputB.set(1);

        delay=new InterpolatingDelay();
        delay.allocate(88200);
        add(delay);

        osc.output.connect(delay.delay);

        masterIn.output.connect(delay.input);
        chorusGain=new Multiply();
        chorusGain.inputB.set(0.8);
        add(chorusGain);

        delay.output.connect(chorusGain.inputA);

        adder=new Add();
        chorusGain.output.connect(adder.inputA);
        adder.inputB.connect(masterIn.output);

        output=adder.output;

    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
