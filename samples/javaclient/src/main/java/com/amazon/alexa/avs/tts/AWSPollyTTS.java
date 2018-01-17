package com.amazon.alexa.avs.tts;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.InputStream;

public class AWSPollyTTS {

    private AmazonPollyClient polly = null;
    private Voice voice = null;

    public AWSPollyTTS() {
        try {
            // Need credentials file at '~/.aws/credentials' with
            //      [default]
            //      aws_access_key_id=""
            //      aws_secret_access_key=""
            //
            // Or environment variable "AWS_ACCESS_KEY" or "AWS_ACCESS_KEY_ID"
            // and "AWS_SECRET_KEY" or "AWS_SECRET_ACCESS_KEY"

            // create an Amazon Polly client in a specific region
            polly = new AmazonPollyClient(new DefaultAWSCredentialsProviderChain(),
                    new ClientConfiguration());
            polly.setRegion(Region.getRegion(Regions.US_EAST_1));
            // Create describe voices request.
            DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

            // Synchronously ask Amazon Polly to describe available TTS voices.
            DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
            // speaker:
            // Joanna, female
            // Salli, female
            // Kimberly, female
            // Kendra, female
            // Ivy, female
            // Matthew, man
            // Justin, man
            // Joey, man
            voice = describeVoicesResult.getVoices().get(0);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void synthesize(String text) throws JavaLayerException {
        if (voice != null && polly != null) {
            SynthesizeSpeechRequest synthReq =
                    new SynthesizeSpeechRequest().withText(text).withVoiceId(voice.getId())
                            .withOutputFormat(OutputFormat.Mp3);
            SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

            InputStream speechStream = synthRes.getAudioStream();
            //create an MP3 player
            AdvancedPlayer player = new AdvancedPlayer(speechStream,
                    javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackStarted(PlaybackEvent evt) {
                    System.out.println("TTS Playback started");
                    System.out.println(text);
                }

                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    System.out.println("TTS Playback finished");
                }
            });
            // play it!
            player.play();
        }
    }
}
