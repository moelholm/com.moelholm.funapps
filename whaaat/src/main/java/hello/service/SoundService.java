package hello.service;

import hello.StringResultHelper;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoundService {

    private static final Logger logger = LoggerFactory.getLogger(SoundService.class);

    @Autowired
    private StringResultHelper resultHelper;

    public String playSound(String clipName) {

        logger.debug("about to play: {}", clipName);

        File clipFile = new File(clipName);

        if (!clipFile.exists()) {
            return resultHelper.toErrorResponse(String.format("File could not be found: %s", clipName));
        }

        logger.info("playing {}", clipName);

        return playWav(clipFile);

    }

    private String playWav(File clipFile) {
        try {
            final Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            clip.open(AudioSystem.getAudioInputStream(clipFile));
            clip.start();
            return resultHelper.successResponse();
        } catch (Exception e) {
            logger.error(String.format("Failed to play sound clip: %s", clipFile), e);
            return resultHelper.toErrorResponse(e);
        }
    }
}
