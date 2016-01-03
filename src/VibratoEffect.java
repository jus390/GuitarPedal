import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;
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
    MixerMonoRamped mix;
    SquareOscillatorBL squareOsc;
    Multiply masterIn;


    public VibratoEffect(){

        osc = new SineOscillator();
        add(osc);
        osc.amplitude.set(0.3);
        osc.frequency.set(10);

        masterIn=new Multiply();
        add(masterIn);
        input=masterIn.inputA;
        masterIn.inputB.set(1);

        mix=new MixerMonoRamped(2);
        add(mix);
        mix.input.connect(0, osc.output, 0);
        mix.input.connect(1, masterIn.output, 0);
        output = mix.output;

        squareOsc = new SquareOscillatorBL();
        add(squareOsc);
        squareOsc.frequency.connect(mix.output);

        //output = squareOsc.output;


    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
