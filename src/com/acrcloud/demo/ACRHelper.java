package com.acrcloud.demo;

import com.acrcloud.utils.ACRCloudRecognizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class ACRHelper {

    ArrayList<String> results = new ArrayList<String>();
    //int resetStart = 0;
    String result = null;


    public ArrayList<String> recognizeByFile(int START, int STOP, int INTERVAL, Map config, File file) {
        ACRCloudRecognizer re = acrCloudRecognizerInitailization(config);
       // resetStart = START;
        for (; START < STOP; START += INTERVAL) {
            result = re.recognizeByFile(file.toString(), START, INTERVAL);
            results.add(result);
        }
       // START = resetStart;
        return results;
    }

    public ArrayList<String> recognizeByFileStart(int START, int INTERVAL, Map config, File file) {
        ACRCloudRecognizer re = acrCloudRecognizerInitailization(config);
       // resetStart = START;
        while (true) {
            result = re.recognizeByFile(file.toString(), START, INTERVAL);
            if (result.contains("2005") || result.contains("1001")) { //|| result.contains("1001")
                break;
            }
            results.add(result);
            START += INTERVAL;
        }
      //  START = resetStart;
        return results;
    }

    public ACRCloudRecognizer acrCloudRecognizerInitailization(Map config){
        ACRCloudRecognizer re = new ACRCloudRecognizer(config);
        return re;
    }

}
