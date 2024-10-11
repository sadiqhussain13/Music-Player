import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

@SuppressWarnings("unused")
public class MusicPlayer extends JFrame {

    private Clip clip;
    private AudioInputStream audioStream;
    private FloatControl volumeControl;
    private boolean isPlaying = false;
    private boolean isLooping = false;
    private double volume = 0.8;
    private JLabel volumeLabel;

    public MusicPlayer() {
        super("Music Player");
        setLayout(new FlowLayout());

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new PlayButtonListener());
        add(playButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new StopButtonListener());
        add(stopButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetButtonListener());
        add(resetButton);

        JButton loopButton = new JButton("Loop");
        loopButton.addActionListener(new LoopButtonListener());
        add(loopButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new QuitButtonListener());
        add(quitButton);

        JButton volumeButton = new JButton("Volume");
        volumeButton.addActionListener(new VolumeButtonListener());
        add(volumeButton);

        volumeLabel = new JLabel("Volume: " + String.format("%.1f", volume));
        add(volumeLabel);

        try {
            File file = new File("1-01-Broken-Road.wav");
            audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue((float) (Math.log(volume) / Math.log(10.0) * 20.0));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isPlaying) {
                clip.start();
                isPlaying = true;
            }
        }
    }

    private class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isPlaying) {
                clip.stop();
                isPlaying = false;
            }
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isPlaying) {
                clip.stop();
                clip.setMicrosecondPosition(0);
                isPlaying = false;
            }
        }
    }

    private class LoopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                isLooping = true;
            } else {
                clip.stop();
                isLooping = false;
            }
        }
    }

    private class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class VolumeButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        double volume = Double.parseDouble(JOptionPane.showInputDialog("Enter volume (0.0 to 1.0):"));
        if (volume >= 0.0 && volume <= 1.0) {
            volumeControl.setValue((float) (Math.log(volume) / Math.log(10.0) * 20.0));
            MusicPlayer.this.volume = volume;
            volumeLabel.setText("Volume: " + String.format("%.1f", volume));
        } else {
            JOptionPane.showMessageDialog(MusicPlayer.this, "Invalid volume value");
        }
    }
}

    public static void main(String[] args) {
        new MusicPlayer();
    }
}