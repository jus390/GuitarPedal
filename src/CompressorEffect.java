
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

/**
 * Created by jus390 on 12/31/15.
 */
public class CompressorEffect extends Circuit implements UnitSource {

    public final UnitInputPort input;
    public final UnitOutputPort output;

    Multiply masterIn;
    Multiply outGain;
    EnvelopeAttackDecay ead;


    public CompressorEffect(){
        masterIn=new Multiply();
        add(masterIn);
        input=masterIn.inputA;
        masterIn.inputB.set(1);

        outGain=new Multiply();
        outGain.inputA.connect(masterIn.output);
        add(outGain);

        ead=new EnvelopeAttackDecay();
        ead.amplitude.connect(masterIn.output);
        ead.attack.set(0.05);
        ead.decay.set(0.8);
        ead.input.set(0.8);
        add(ead);

        outGain.inputB.connect(ead.output);
        output=outGain.output;

    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
