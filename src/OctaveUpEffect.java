import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

/**
 * Created by Jure on 01/02/16.
 */
public class OctaveUpEffect extends Circuit implements UnitSource {

    public final UnitInputPort input;
    public final UnitOutputPort output;

    Multiply masterIn;
    Multiply octaveMult;
    SineOscillator osc;
    PitchDetector pd;

    MixerMonoRamped mix;
    Add adder;

    public OctaveUpEffect(){
        masterIn=new Multiply();
        add(masterIn);
        input=masterIn.inputA;
        masterIn.inputB.set(1);

        pd = new PitchDetector();
        add(pd);

        pd.input.connect(masterIn.output);

        octaveMult = new Multiply();
        add(octaveMult);

        octaveMult.inputA.connect(pd.frequency);
        octaveMult.inputB.set(2);

        osc=new SineOscillator();
        add(osc);
        osc.amplitude.connect(masterIn.output);
        osc.frequency.connect(octaveMult.output);



        mix=new MixerMonoRamped(2);
        add(mix);
        mix.input.connect(0, osc.output, 0);
        mix.input.connect(1, masterIn.output, 0);
        output = mix.output;

    }

    public void setOctave(int oct){
        if(oct == 0){
            octaveMult.inputB.set(2);
        }
        else{
            octaveMult.inputB.set(0.5);
        }

    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
