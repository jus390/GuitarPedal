import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

/**
 * Created by Jure on 3.1.2016.
 */
public class PitchShiftEffect extends Circuit implements UnitSource {
    public final UnitInputPort input;
    public final UnitOutputPort output;

    public double pitchShift = 440;
    Multiply masterIn;
    SineOscillator osc;

    MixerMonoRamped mix;
    Add adder;

    public PitchShiftEffect(){
        masterIn=new Multiply();
        add(masterIn);
        input=masterIn.inputA;
        masterIn.inputB.set(1);


        osc=new SineOscillator();
        osc.amplitude.set(0.8);
        osc.frequency.set(pitchShift);
        add(osc);


        mix=new MixerMonoRamped(2);
        add(mix);
        mix.input.connect(0, osc.output, 0);
        mix.input.connect(1, masterIn.output, 0);
        output = mix.output;

    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }

    public void changePitchShift(double value){
        pitchShift = value;
    }

}
