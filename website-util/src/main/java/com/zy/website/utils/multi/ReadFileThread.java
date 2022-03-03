package com.zy.website.utils.multi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Description:分段读取文件
 */
public class ReadFileThread implements Callable<List<String>> {

    private static Logger logger = LogManager.getLogger(ReadFileThread.class);

    private Integer start_index;    //文件开始读取行数
    private Integer end_index;      //文件结束读取行数
    private InputStream is;         //输入流

    public ReadFileThread(int start_index, int end_index, InputStream is) {
        this.start_index = start_index;
        this.end_index = end_index;
        this.is = is;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public List<String> call() throws Exception {
        long start = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();
        List<String> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        int loc = 1;
        while (loc < start_index) {
            reader.readLine();
            loc++;
        }
        while (loc < end_index) {
//            result.append(reader.readLine()).append("\r\n"); // 读取成string字符串
            resultList.add(reader.readLine().trim());
            loc++;
        }
//        result.append(reader.readLine());
        resultList.add(reader.readLine().trim());
//        String strResult = result.toString();
        reader.close();
        is.close();
        logger.info("线程={} 文件读取完成 总耗时={}毫秒  读取数据={}条",
                Thread.currentThread().getName(), (System.currentTimeMillis() - start), resultList.size());
        return resultList;
    }
}