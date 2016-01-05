package guitarpedalgui;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.devices.javasound.JavaSoundAudioDevice;
import com.jsyn.unitgen.*;
import com.softsynth.jsyn.SynthContext;
import com.softsynth.jsyn.circuits.PluckedString;

public class IOinterface {
    static ChannelIn channel0;
    static ChannelIn channel1;
    static LineOut lineOut = new LineOut();
    static SynthContext synthContext;
    
    static SineOscillator tone;
    //static DelayEffect effect;
    static ReverbEffect effect;
    //static TremoloEffect effect;
    //static ChorusEffect effect;
    //static DistortionEffect effect;
    //static CompressorEffect effect;

    public static int getRocksmithIndex(){
        JavaSoundAudioDevice adm = new JavaSoundAudioDevice();
        int divRocksmith=0;
        for (int i=0;i<adm.getDeviceCount();i++){
            if(adm.getDeviceName(i).indexOf("Adapter")!=-1){
                divRocksmith=i;

            }
            System.out.println("Device: " + adm.getDeviceName(i));
        }
        System.out.println(adm.getDeviceName(divRocksmith));
        return divRocksmith;
    }
    public static void run() throws InterruptedException {
    	boolean noMic=false;
    	
        Synthesizer synth = JSyn.createSynthesizer();

        synth.getAudioDeviceManager().setSuggestedOutputLatency( 0.3 );
        int numInputChannels = 2;
        int numOutputChannels = 2;
        synth.start( 44100, AudioDeviceManager.USE_DEFAULT_DEVICE, numInputChannels, AudioDeviceManager.USE_DEFAULT_DEVICE,
                numOutputChannels );

        synth.add( channel0 = new ChannelIn(0));
        synth.add( channel1 = new ChannelIn(1));
        
        
        //synth.add(gtr=new PluckedString());

        synth.add(lineOut);
        synth.add(effect=new ReverbEffect());
        //effect.setAmplitude(0.5);
        //effect.setFrequency(4);
        effect.output.connect( 0, lineOut.input, 0 );
        effect.output.connect( 0, lineOut.input, 1 );
        
        if (noMic){
	        //no mic debug
	        synth.add( tone=new SineOscillator());
	        //synth.add( tone=new SineOscillator());
	        tone.frequency.set(440);
	        tone.amplitude.set(0);
	        tone.output.connect(effect.input);
        }


        channel0.output.connect(effect.input);



        lineOut.start();
        //Thread.sleep(1000);
        //Thread.sleep(2000);
        //tone.amplitude.set(0.8);
        //Thread.sleep(1000);
        //tone.amplitude.set(0);
        //tone.amplitude.set(1);
        //Thread.sleep(1000);
        //tone.amplitude.set(0);
    }
    public static void main(String[] args) throws InterruptedException {
        run();

    }
}
