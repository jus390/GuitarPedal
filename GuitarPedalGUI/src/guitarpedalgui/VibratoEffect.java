package guitarpedalgui;

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

    /*PhaseShifter ps;
    SineOscillator osc;
    MixerMonoRamped mix;
    SquareOscillatorBL squareOsc;
    Multiply masterIn;*/
    
    Multiply masterIn;
    Add frequencyAdder;
    SineOscillator osc;
    PitchDetector pd;
    maxAmplitude mAmp;
    MixerMonoRamped mix;
    SineOscillator osc2;


    public VibratoEffect(){

        /*osc = new SineOscillator();
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

        //output = squareOsc.output;*/
        
        masterIn=new Multiply();
        add(masterIn);
        input=masterIn.inputA;
        masterIn.inputB.set(1);

        pd = new PitchDetector();
        add(pd);

        pd.input.connect(masterIn.output);

        frequencyAdder = new Add();
        add(frequencyAdder);

        frequencyAdder.inputA.connect(pd.frequency);
        osc2=new SineOscillator();
        add(osc2);
        osc2.amplitude.set(220);
        osc2.frequency.set(2);
        frequencyAdder.inputB.connect(osc2.output);

        osc=new SineOscillator();
        add(osc);
        /*mAmp=new maxAmplitude();
        add(mAmp);
        masterIn.output.connect(mAmp.input);*/
        
        osc.amplitude.connect(masterIn.output);
        osc.frequency.connect(frequencyAdder.output);

        output = osc.output;


    }

    public void setVibratoFreq(double freq){
        osc2.frequency.set(freq);
    }

    public void setVibratoAmplitude(double value) {
        osc2.amplitude.set(value);
    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
