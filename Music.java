package Mplayer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music 
{
	 @SuppressWarnings("static-access")
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	 {
	  @SuppressWarnings("resource")
	  Scanner scanner = new Scanner(System.in);
	  File file = new File("1-01-Broken-Road.wav");
	  AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	  Clip clip = AudioSystem.getClip();
	  clip.open(audioStream);
	  
	  String response = "";
	  
	  FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    // set the percent (between 0.0 and 1.0)
	    double percent = 0.8;   
	    float dB = (float) (Math.log(percent) / Math.log(10.0) * 20.0);
	    volume.setValue(dB);
	 
	  while(!response.equals("Q")) 
	  {
	   System.out.println("P = Play, S = Stop, R = Reset, Q = Quit, L = Loop");
	   System.out.print("Enter your choice: ");
	   
	   response = scanner.next();
	   response = response.toUpperCase();
	   
	   switch(response) 
	   {
	    case ("P"): clip.start();
	    break;
	    case ("S"): clip.stop();
	    break;
	    case ("R"): clip.setMicrosecondPosition(0);
	    break;
	    case ("Q"): clip.close();
	    break;
	    case ("L"): clip.loop(clip.LOOP_CONTINUOUSLY);
	    break;
	    
	    default: System.out.println("Not a valid response");
	   }   
	  }
	  System.out.println("Signing out!"); 
	 }
}


