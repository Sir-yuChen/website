package com.zy.website.service.impl;

import com.zy.website.code.ApiReturnCode;
import com.zy.website.exception.WebsiteBusinessException;
import com.zy.website.model.vo.FileThreadVO;
import com.zy.website.utils.multi.ReadFileThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service("fileService")
public class FileService{
    //日志
    private static Logger logger = LogManager.getLogger(FileService.class);

    @Value("${file.thread.num}")
    private Integer threadNum; //线程数

    @Resource(name = "asyncFilmService")
    private ThreadPoolTaskExecutor executor;  //线程池

    /**
     * 启用多个线程分段读取文件
     * PS:若文件行数小于线程数会造成线程浪费
     * 适用于读取一行一行的数据报文
     * @return
     */
    public List uploadByThread(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return null;
        }
        InputStream is = file.getInputStream();
        List<FileThreadVO> threadVOS = new ArrayList<>(threadNum); //自定义线程实体对象
        //为线程分配读取行数
        Integer lines = getLineNum(is);     //文件总行数
        Integer line;                       //每个线程分配行数
        Integer start_line;                 //线程读取文件开始行数
        Integer end_line;                   //线程读取文件结束行数

        //根据文件行数和线程数计算分配的行数，这里有点繁琐了，待优化
        if (lines < threadNum) {
            for (int i = 1; i <= lines; i++) {
                FileThreadVO fileThreadVO = new FileThreadVO();
                start_line = end_line = i;
                InputStream stream = file.getInputStream();

                ReadFileThread readFileThread = new ReadFileThread(start_line, end_line, stream);
                fileThreadVO.setStart_line(start_line);
                fileThreadVO.setIs(stream);
                fileThreadVO.setEnd_line(end_line);
                fileThreadVO.setResult(executor.submit(readFileThread).get());
                threadVOS.add(fileThreadVO);
            }
        } else {
            for (int i = 1, tempLine = 0; i <= threadNum; i++, tempLine = ++end_line) {
                InputStream stream = file.getInputStream();
                FileThreadVO fileThreadVO = new FileThreadVO();
                Integer var1 = lines / threadNum;
                Integer var2 = lines % threadNum;
                line = (i == threadNum) ? (var2 == 0 ? var1 : var1 + var2) : var1;
                start_line = (i == 1) ? 1 : tempLine;
                end_line = (i == threadNum) ? lines : start_line + line - 1;

                ReadFileThread readFileThread = new ReadFileThread(start_line, end_line, stream);
                fileThreadVO.setStart_line(start_line);
                fileThreadVO.setIs(stream);
                fileThreadVO.setEnd_line(end_line);
                fileThreadVO.setResult(executor.submit(readFileThread).get());
                threadVOS.add(fileThreadVO);
            }
        }
        List resultCompleteList = new ArrayList<>(); //整合多个线程的读取结果
        threadVOS.forEach(record->{
            List<String> result = record.getResult();
            resultCompleteList.addAll(result);
        });

        boolean isComplete = false;
        if (resultCompleteList != null ) {
            //校验行数 由于本项目使用的是读取行为一个条件 所以只校验行数 也可以校验字节
            int i = resultCompleteList.size() - lines;
            if (i == 0) {
                isComplete = true;
            }
        }
        if (!isComplete) {
            logger.error(">>>>>====uploadByThread====>>>>>>文件完整性校验失败！");
            throw new WebsiteBusinessException("The file is incomplete！", ApiReturnCode.HTTP_ERROR.getCode());//自定义异常以及错误码
        } else {
            return resultCompleteList;
        }
    }

    /**
     * 获取文件行数
     * @param is
     * @return
     * @throws IOException
     */
    public int getLineNum(InputStream is) throws IOException {
        int line = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (reader.readLine() != null) {
            line++;
        }
        reader.close();
        is.close();
        return line;
    }
}