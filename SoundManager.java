package java_game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.sound.sampled.*;

public class SoundManager implements Serializable {
	private static final long serialVersionUID = -214851194313030678L;

	private transient Clip clip;
	private transient FloatControl volumeControl;
	private String filePath;

	public SoundManager(String filePath) {
		this.filePath = filePath;
		initializeClip();

	}

	private void initializeClip() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(getClass().getResourceAsStream(filePath));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);

			volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void loop() {
		if (clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public void play() {
		if (clip != null) {
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void setVolume(float volume) {
		if (volumeControl != null) {
			float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
			volumeControl.setValue(dB);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    in.defaultReadObject();
	}
	private void writeObject(ObjectOutputStream out) throws IOException {
	    out.defaultWriteObject();
	}
}
