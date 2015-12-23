import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

/**
 * Created by Jure on 23.12.2015.
 */

public class ReverbEffect extends Circuit implements UnitSource {

    public final UnitInputPort input;
    public final UnitOutputPort output;

    Add adder1;
    Add adder2;
    InterpolatingDelay intDelay;
    Multiply m1;
    Multiply m2;
    Multiply initialInputSplitter;
    float fade = 0.5f;
    float delayValue = 1;

    public ReverbEffect(){
        initialInputSplitter = new Multiply();
        add(initialInputSplitter);
        m1 = new Multiply();
        m2 = new Multiply();
        add(m1);
        add(m2);

        intDelay = new InterpolatingDelay();
        add(intDelay);

        input = initialInputSplitter.inputA;
        initialInputSplitter.inputB.set(1);
        adder1 = new Add();
        adder2 = new Add();

        add(adder2);
        add(adder1);

        adder1.inputA.connect(initialInputSplitter.output);

        intDelay.input.connect(adder1.output);
        intDelay.allocate(88200);

        intDelay.delay.set(delayValue);


        m1.inputA.connect(intDelay.output);
        m1.inputB.set(fade);

        adder1.inputB.connect(m1.output);

        m2.inputA.connect(intDelay.output);
        m2.inputB.set(fade);

        adder2.inputA.connect(m2.output);
        adder2.inputB.connect(initialInputSplitter.output);

        output = adder2.output;
    }


    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}