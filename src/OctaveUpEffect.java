import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

/**
 * Created by jus390 on 12/27/15.
 */
public class OctaveUpEffect extends Circuit implements UnitSource {

    public final UnitInputPort input;
    public final UnitOutputPort output;

    Multiply masterIn;
    SineOscillator osc;
    InterpolatingDelay delay;
    Multiply chorusGain;
    Add adder;

    public OctaveUpEffect(){
        adder=new Add();
        input = adder.inputA;
        adder.inputB.set(440);
        output = adder.output;
    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
