import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.devices.javasound.JavaSoundAudioDevice;
import com.jsyn.unitgen.*;
import com.softsynth.jsyn.SynthContext;

public class AudioRecorder {
    static ChannelIn channel0;
    static ChannelIn channel1;
    static LineOut lineOut = new LineOut();
    static TriangleOscillator osc1;
    static SynthContext synthContext;
    static Multiply adder;

    public int getRocksmithIndex(){
        JavaSoundAudioDevice adm = new JavaSoundAudioDevice();
        int divRocksmith=0;
        for (int i=0;i<adm.getDeviceCount();i++){
            if(adm.getDeviceName(i).indexOf("Rocksmith")!=-1){
                divRocksmith=i;
            }
            //System.out.println(adm.getDeviceName(i));
        }
        //System.out.print(adm.getDeviceName(divRocksmith));
        return divRocksmith;
    }
    public static void run(){
        Synthesizer synth = JSyn.createSynthesizer();

        synth.getAudioDeviceManager().setSuggestedOutputLatency( 0.01 );
        int numInputChannels = 2;
        int numOutputChannels = 2;
        synth.start( 48000, AudioDeviceManager.USE_DEFAULT_DEVICE, numInputChannels, AudioDeviceManager.USE_DEFAULT_DEVICE,
                numOutputChannels );

        synth.add( channel0 = new ChannelIn(0));
        synth.add( channel1 = new ChannelIn(1));

        synth.add(lineOut);
        synth.add(adder=new Multiply());
        synth.add(osc1=new TriangleOscillator());
        osc1.frequency.set(4);
        osc1.amplitude.set(1.0);
        osc1.output.connect(adder.inputA);
        channel0.output.connect(adder.inputB);

        adder.output.connect( 0, lineOut.input, 0 );
        adder.output.connect( 0, lineOut.input, 1 );
        lineOut.start();

    }
    public static void main(String[] args){
        run();

    }
}
