import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PhaseShifter;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitSource;

/**
 * Created by Jure on 23.12.2015.
 */
public class VibratoEffect extends Circuit implements UnitSource {

    public final UnitInputPort input;
    public final UnitOutputPort output;

    PhaseShifter ps;
    SineOscillator osc;

    public VibratoEffect(){

        ps = new PhaseShifter();
        add(ps);
        osc = new SineOscillator();
        add(osc);
        osc.amplitude.set(1);
        osc.frequency.set(10);


        input = ps.input;


        ps.offset.connect(osc.output);

        output = ps.output;


    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
