
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.TriangleOscillator;

/**
 * Created by Juš on 9.11.2015.
 */
public class SimpleEffect extends Circuit {

    TriangleOscillator triOsc;
    public UnitInputPort modulationFrequency;

    public SimpleEffect(){
        triOsc = new TriangleOscillator();
        add( triOsc );
        modulationFrequency = triOsc.frequency;
        addPort( modulationFrequency );
    }
}
