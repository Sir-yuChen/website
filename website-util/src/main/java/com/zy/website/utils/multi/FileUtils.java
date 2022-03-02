package com.zy.website.utils.multi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//主要功能：删除文件、文件名校验、文件下载时进行字节流写入
public class FileUtils {
    private static Logger logger = LogManager.getLogger(FileUtils.class);

    //文件名正则校验
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    public static void writeBytes(String filePath, OutputStream os) {
        FileInputStream fi = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fi = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fi.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fi != null) {
                try {
                    fi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否成功
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param dir 被删除目录的文件路径
     * @return 目录删除成功返回true, 否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            logger.error("删除目录失败" + dir + "目录不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            logger.error("删除目录失败");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.info("删除目录" + dir + "成功！");
            return true;
        } else {
            logger.error("删除目录" + dir + "失败！");
            return false;
        }
    }

    // 删除文件夹
    // param folderPath 文件夹完整绝对路径
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 文件名校验
     *
     * @param fileName 文件名
     * @return true 正常， false 非法
     */
    public static boolean isValidName(String fileName) {
        return fileName.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 下载文件到服务器
     *
     * @param downloadUrl      要下载的文件的地址
     * @param downloadPath     服务器上存储的文件路径
     * @param downloadFileName 服务器上存储的文件名称
     * @return
     */
    public static boolean downloadToServer(String downloadUrl, String downloadPath, String downloadFileName) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        boolean flag = false;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            bis = new BufferedInputStream(connection.getInputStream());
            File file = new File(downloadPath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (!mkdirs) {
                    logger.error("【下载文件】创建文件目录失败");
                    return false;
                }
            }
            String filePathName = downloadPath + File.separator + downloadFileName;
            byte[] buf = new byte[1024];
            int size;
            fos = new FileOutputStream(filePathName);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            flag = true;
            logger.info("文件下载成功,文件路径[" + filePathName + "]");
            flag = true;
        } catch (Exception e) {
            logger.error("下载文件异常", e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                logger.error("关流异常", e);
                e.printStackTrace();
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        String downloadUrl = "https://gitee.com/Sir-yuChen/backstage_ant_upload/raw/master/file/upload_img/1642744541595_44befdc5-7191-4fd2-9a7c-46de603ef259.jpg";
        String filePath = "D:\\idea-develop-project\\Project_All\\backstage_ant\\web\\src\\main\\resources\\jxls_images";
        String fileName = "img.jpeg";
        FileUtils.downloadToServer(downloadUrl, filePath, fileName);
    }

}