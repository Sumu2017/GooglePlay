package com.sumu.googleplay.protocol;

import android.os.SystemClock;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sumu.googleplay.http.HttpHelper;
import com.sumu.googleplay.utils.FileUtils;
import com.sumu.googleplay.utils.MD5Encoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * ==============================
 * 作者：苏幕
 * <p>
 * 时间：2015/11/23   22:29
 * <p>
 * 描述：
 * <p>数据读取协议基类
 * ==============================
 */
public abstract class BaseProtocol<T> {
    protected Gson gson;
    public BaseProtocol() {
        gson=new Gson();
    }

    public T load(int index) {
        SystemClock.sleep(1000);
        //加载本地数据
        String result = loadLocal(index);
        if (TextUtils.isEmpty(result)) {
            //请求服务器
            result = loadServer(index);
            if (!TextUtils.isEmpty(result)) {
                saveLocal(index,result);
            }
        }
        if (!TextUtils.isEmpty(result)) {
            return parseJson(result);
        } else {
            return null;
        }
    }


    /**
     * 保存数据到本地
     *
     * @param index
     * @param result
     */
    private void saveLocal(int index, String result) {
        File file = null;
        try {
            file = new File(FileUtils.getCacheDir(), MD5Encoder.encode(getUrl()+index));
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(System.currentTimeMillis() + 1000 * 100 + "");
            bufferedWriter.newLine();
            bufferedWriter.write(result);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从网络读取数据
     *
     * @param index
     * @return
     */
    private String loadServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(getUrl()+index);
        String result = null;
        if (httpResult!=null) {
            result = httpResult.getString();
        }
        return result;
    }

    /**
     * 从本地读取数据
     *
     * @return
     * @param index
     */
    private String loadLocal(int index) {
        //  如果发现文件已经过期了 就不要再去复用缓存了
        File file = null;
        try {
            file = new File(FileUtils.getCacheDir(), MD5Encoder.encode(getUrl()+index));
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            long outOfDate = Long.parseLong(bufferedReader.readLine());
            /*if (System.currentTimeMillis() > outOfDate) {
                return null;
            } else {*/
                String str = null;
                StringWriter result = new StringWriter();
                while ((str = bufferedReader.readLine()) != null) {
                    result.write(str);
                }
                return result.toString();
           /* }*/
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析JSON数据
     *
     * @param result
     * @return
     */
    protected abstract T parseJson(String result);

    /**
     * url地址
     *
     * @return
     */
    protected abstract String getUrl();
}
