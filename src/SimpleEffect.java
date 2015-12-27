
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitSource;

/**
 * Created by Juš on 9.11.2015.
 */
public class SimpleEffect extends Circuit implements UnitSource {
	
	public final UnitInputPort input;
	public final UnitOutputPort output;	
	
    SineOscillator osc;
	Multiply mlt;

    public SimpleEffect(){
    	
        mlt=new Multiply();
		add(mlt);
		input = mlt.inputA;
		
		osc=new SineOscillator();
		add( osc );
		
		osc.output.connect(mlt.inputB);

		output= mlt.output;
    }
    
    public void setAmplitude(double amp) {
		osc.amplitude.set(amp);
	}
    
    public void setFrequency(double freq) {
		osc.frequency.set(freq);
	}

	@Override
	public UnitOutputPort getOutput() {
		return output;
	}
}
