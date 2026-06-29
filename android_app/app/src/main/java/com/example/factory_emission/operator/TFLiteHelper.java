package com.example.factory_emission.operator;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TFLiteHelper {
    private Interpreter interpreter;

    public TFLiteHelper(Context context, String modelPath) {
        try {
            // 1. Create Interpreter Options
            Interpreter.Options options = new Interpreter.Options();
            // 2. Enable Flex Delegate
            // This is the "key" that unlocks the ONNX_SCALER
            options.setAllowFp16PrecisionForFp32(true);
            // 3. Initialize the interpreter with options
            interpreter = new Interpreter(loadModelFile(context, modelPath), options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private MappedByteBuffer loadModelFile(Context context, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    public float[][] doInference(float[] inputData) {
        // 1. The error says the model output is [1, 4]
        // So we must create a container that matches exactly.
        float[][] output = new float[1][4];
        // 2. Run inference
        // Make sure inputData is wrapped in an array if the model expects [1, N]
        interpreter.run(inputData, output);
        // 3. Return the 4 probabilities
        return output;
    }
}
