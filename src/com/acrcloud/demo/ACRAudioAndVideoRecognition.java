package com.acrcloud.demo; /**
 * @author qinxue.pan E-mail: xue@acrcloud.com
 * @version 1.0.0
 * @create 2015.10.01
 **/

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import com.acrcloud.utils.ACRCloudRecognizer;
import com.google.gson.Gson;
import org.json.simple.parser.ParseException;

public class ACRAudioAndVideoRecognition {

    public static void main(String[] args) throws ParseException {
        Map<String, Object> config = new HashMap<String, Object>();
        // Replace "xxxxxxxx" below with your project's access_key and access_secret.
        config.put("host", "identify-ap-southeast-1.acrcloud.com");
        config.put("access_key", "8c85b19fc3ec2cb31ded2eed662d7eeb");
        config.put("access_secret", "fK4wVKtniCZ0r30Bki75qeAWqzgqniEiPdU0UPK9");
        config.put("rec_type", ACRCloudRecognizer.RecognizerType.AUDIO); // AUDIO, HUMMING, BOTH
        config.put("debug", false);
        config.put("timeout", 10); // seconds

        ACRCloudRecognizer re = new ACRCloudRecognizer(config);

        /**
         *
         *  recognize by file path of (Formatter: Audio/Video)
         *     Audio: mp3, wav, m4a, flac, aac, amr, ape, ogg ...
         *     Video: mp4, mkv, wmv, flv, ts, avi ...
         *
         *
         **/
        ArrayList<String> results = new ArrayList<String>();
        ACRHelper helper = new ACRHelper();
        String result = null;
        int START = 0;
        int STOP = 0;
        int INTERVAL = 10;
        int TotalDuration;
        int resetStart = 0;
        Scanner in = new Scanner(System.in);
        System.out.printf("Enter INTERVAL Seconds:  ");
        INTERVAL = in.nextInt();
        System.out.printf("Enter START Seconds or ZERO:  ");
        START = in.nextInt();
        System.out.printf("Enter STOP Seconds or ZERO:  ");
        STOP = in.nextInt();

        File folder = new File("D:/ACRTest/FileScan/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {

                String ext = file.getName().substring(file.getName().indexOf(".") + 1);

                if (ext.equalsIgnoreCase("mp4")
                        || ext.equalsIgnoreCase("mkv")
                        || ext.equalsIgnoreCase("wmv")
                        || ext.equalsIgnoreCase("flv")
                        || ext.equalsIgnoreCase("ts")
                        || ext.equalsIgnoreCase("avi")) {
                    if (START != 0 && STOP != 0) {
                        resetStart = START;
                        results = helper.recognizeByFile(START, STOP, INTERVAL, config, file);
                        START = resetStart;
                    } else {
                        resetStart = START;
                        results = helper.recognizeByFileStart(START, INTERVAL, config, file);
                        START = resetStart;
                    }
                } else {
                    if (START != 0 && STOP != 0) {
                        resetStart = START;
                        results = helper.recognizeByFile(START, STOP, INTERVAL, config, file);
                        START = resetStart;
                    } else if (START != 0 && STOP == 0) {
                        resetStart = START;
                        results = helper.recognizeByFileStart(START, INTERVAL, config, file);
                        START = resetStart;
                    } else {
                        if (START == 0 && STOP == 0) {
                            TotalDuration = re.getFileDurationMS(file.toString());
                            STOP = (TotalDuration / 1000) % 60;
                            System.out.println("TotalDuration=" + STOP);
                        }
                        helper.recognizeByFile(START, STOP, INTERVAL, config, file);
                        STOP = 0;
                        START = 0;
                    }
                }
            }
        }

        String Matching_Fp_Files = new Gson().toJson(results);
        System.out.println("Matching_Fp_Files:" + Matching_Fp_Files);
    }

}