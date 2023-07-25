package com.example.androiddemos.util;

import android.util.Log;

import com.blankj.utilcode.util.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/25
 */
public class FileWriteUtil {
    private static int sBufferSize = 524288;
    private static final String TAG = "superdome/FileWriteUtil";

    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,double totalSize) {
        return fileWriteFromIS(file, is, totalSize,null);
    }

    public static boolean writeFileFromIS(final File file,
                                          final InputStream is, double totalSize,
                                          final FileWriteUtil.OnFileProgressUpdateListener listener) {
        return fileWriteFromIS(file, is, totalSize,listener);
    }

    private static boolean fileWriteFromIS(final File file,
                                          final InputStream is,
                                          double totalSize,
                                          final FileWriteUtil.OnFileProgressUpdateListener listener) {
        Log.d(TAG, "#writeFileFromIS------totalSize:" + totalSize);
        if (is == null || !FileUtils.createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            int len;
            if (listener == null) {
                byte[] data = new byte[sBufferSize];
                while ((len = is.read(data, 0, sBufferSize)) != -1) {
                    os.write(data, 0, len);
                }
            } else {
                byte data[] = new byte[sBufferSize];
                double currentLength = 0;
                int progress = 0;
                listener.onProgressUpdate(0);
                while ((len = is.read(data, 0, sBufferSize)) != -1) {
                    os.write(data, 0, len);
                    currentLength += len;
                    //计算当前下载进度
                    progress = (int) (100 * currentLength / totalSize);
                    Log.d(TAG, "#writeFileFromIS------currentLength:" + currentLength);
                    Log.d(TAG, "#writeFileFromIS------progress:" + progress);
                    listener.onProgressUpdate(progress);
                }
                Log.d(TAG, "#writeFileFromIS------currentLength finish:" + currentLength);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnFileProgressUpdateListener {
        void onProgressUpdate(int progress);
    }
}
