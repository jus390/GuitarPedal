import com.jsyn.data.Function;
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
    FunctionEvaluator amplitude;
    Divide div;
    Maximum max;


    public CompressorEffect(){
        masterIn=new Multiply();
        add(masterIn);
        input=masterIn.inputA;
        masterIn.inputB.set(1);

        amplitude=new FunctionEvaluator();
        add(amplitude);

        div=new Divide();
        add(div);

        max=new Maximum();
        add(max);
        max.inputB.set(0.1);

        Function getMaxAmplitude=new Function() {
            double prev=0;
            double max=0;
            @Override
            public double evaluate(double v) {
                if(v<prev){
                    max=prev;
                }
                prev=v;
                return max;
            }
        };

        amplitude.function.set(getMaxAmplitude);

        masterIn.output.connect(amplitude.input);

        amplitude.output.connect(max.inputA);

        masterIn.output.connect(div.inputA);
        max.output.connect(div.inputB);

        output=div.output;

    }

    @Override
    public UnitOutputPort getOutput() {
        return output;
    }
}
