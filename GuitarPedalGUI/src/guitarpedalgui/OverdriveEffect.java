package guitarpedalgui;

import com.jsyn.data.DoubleTable;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FunctionEvaluator;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.UnitSource;


/**
 * Created by jus390 on 12/27/15.
 */
public class OverdriveEffect extends Circuit implements UnitSource {

    public final UnitInputPort input;
    public final UnitOutputPort output;

    Multiply gain;
    DoubleTable waveShape;
    FunctionEvaluator shaper;

    double shapeClean[] = { -0.8, -0.8, -0.8, -0.6, -0.2, 0.0, 0.2, 0.6, 0.8, 0.8, 0.8 };


    public OverdriveEffect(){ //type=1-fuzz else normal dist

        gain=new Multiply();
        add(gain);


        waveShape=new DoubleTable(shapeClean);

        shaper=new FunctionEvaluator();
        shaper.function.set(waveShape);
        add(shaper);

        input=gain.inputA;
        gain.inputB.set(1.4);
        gain.output.connect(shaper.input);

        output=shaper.output;

    }


    public void setGain(double gain){
        this.gain.inputB.set(gain);
    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
