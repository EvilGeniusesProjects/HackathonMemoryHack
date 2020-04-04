package com.evilgeniuses.memoryhack.services;

import android.util.Log;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmException;
import com.algorithmia.Algorithmia;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.AlgoResponse;
import com.algorithmia.algo.Algorithm;
import com.algorithmia.data.DataDirectory;
import com.algorithmia.data.DataFile;
import com.evilgeniuses.memoryhack.Model.ColorizeRequestModel;
import com.evilgeniuses.memoryhack.Model.ColorizeResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ColorizeService {
    private static final String DIR_URI = "data://.my/photos";
    private static final String LOG_TAG = "COLORIZE_ERROR";
    private static final String COLORIZE_API = "deeplearning/ColorfulImageColorization/1.0.1";

    private AlgorithmiaClient client;

    public ColorizeService(String apyKey) {
        client = Algorithmia.client(apyKey);
    }

    public String uploadFile(File file) {
        DataDirectory dataDirectory = client.dir(DIR_URI);
        if (!checkDirExists(dataDirectory)) {
            createDirectory(dataDirectory);
        }

        try {
            DataFile dataFile = dataDirectory.putFile(file);

            return dataFile.toString();
        } catch (APIException e) {
            Log.e(LOG_TAG, "API exception expected: ", e);
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "File not found: ", e);
        }

        return null;
    }

    public byte[] downloadFile(ColorizeRequestModel colorizeRequestModel) {
        try {
            Algorithm colorizeAlgo = client.algo(COLORIZE_API);
            AlgoResponse response = colorizeAlgo.pipe(colorizeRequestModel);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            ColorizeResponseModel colorizeResponseModel = gson.fromJson(response.asJsonString(), ColorizeResponseModel.class);

            String path = colorizeResponseModel.getOutput();
            DataFile file = client.file(path);
            if (checkFileExists(file)) {
                return file.getBytes();
            }
        } catch (APIException e) {
            Log.e(LOG_TAG, "API exception expected: ", e);
        } catch (AlgorithmException e) {
            Log.e(LOG_TAG, "AlgorithmException exception expected: ", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException exception expected: ", e);
        }

        return null;
    }

    private boolean checkFileExists(DataFile file) {
        boolean exists = false;
        try {
            exists = file.exists();
        } catch (APIException e) {
            Log.e(LOG_TAG, "API exception expected: ", e);
        }
        return exists;
    }

    private boolean checkDirExists(DataDirectory directory) {
        boolean exists = false;
        try {
            exists = directory.exists();
        } catch (APIException e) {
            Log.e(LOG_TAG, "API exception expected: ", e);
        }
        return exists;
    }

    private void createDirectory(DataDirectory directory) {
        try {
            directory.create();
        } catch (APIException e) {
            Log.e(LOG_TAG, "API exception expected: ", e);
        }
    }
}
