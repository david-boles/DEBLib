package com.deb.lib.speech;

import java.io.File;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class BaseTTS {
	
	private Voice voice;
	private VoiceManager voiceManager;
	
	public BaseTTS(File base, int voiceIn, boolean enhance) {
		System.setProperty("mbrola.base", base.getAbsolutePath());
		
		voiceManager = VoiceManager.getInstance();
		if (voiceIn>0)voice = voiceManager.getVoice("mbrola_us" + String.valueOf(voiceIn));
		else voice = voiceManager.getVoice("kevin16");
		
		if ((voiceIn == 1 || voiceIn == 3) && enhance) {
			voice.setRate(130);
			voice.setPitch(240);
			voice.setPitchRange(4);
		}
		
		voice.allocate();
	}
	
	public void speak(String in) {
		voice.speak(in);
	}
	
}
