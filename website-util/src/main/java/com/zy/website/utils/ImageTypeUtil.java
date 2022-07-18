package com.zy.website.utils;


import java.net.URLConnection;

public enum ImageTypeUtil {

    PNG(".png", "image/png"),
    JPG(".jpg", "image/jpeg"),
    BMP(".bmp", "image/bmp"),
    JPEG(".jpeg", "image/jpeg"),
    GIF(".gif", "image/gif"),
    TIF(".tif", "image/tiff"),//标签图像文件格式（Tagged Image File Format，简写为TIFF）是一种主要用来存储包括照片和艺术图在内的图像的文件格式。它最初由Aldus公司与微软公司一起为PostScript打印开发。
    TIFF(".tiff", "image/tiff"),
    FAX(".fax", "image/fax"),
    ICO(".ico", "image/x-icon"),
    JFIF(".jfif", "image/jpeg"),
    JPE(".jpe", "image/jpeg"),
    NET(".net", "image/pnetvue"),
    WBMP(".wbmp", "image/vnd.wap.wbmp");
    //如果有其他的mime类型，

    /**
     * 后缀名
     */
    final String mSuffix;
    final String mMIME;

    ImageTypeUtil(String suffix, String mime) {
        this.mSuffix = suffix;
        this.mMIME = mime;
    }

    public static String getSuffixFromUrl(String url) {

        for (ImageTypeUtil fileType : values()) {
            if (url.contains(fileType.suffix())) {
                return fileType.suffix();
            }
        }
        String contentType = getMIMETypeFromUrl(url);
        if (contentType == null) {
            return null;
        }
        return mimeMapingSuffix(contentType);
    }

    public static String getMIMETypeFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        return URLConnection.guessContentTypeFromName(url);
    }

    /**
     * mime类型对应的后缀名
     */
    public static String mimeMapingSuffix(String mime) {
        for (ImageTypeUtil fileType : values()) {
            if (fileType.mime().equals(mime)) {
                return fileType.suffix();
            }
        }
        return null;
    }

    public String mime() {
        return mMIME;
    }

    /**
     * 获取后缀名 * * @return 指定类型的后缀名，如'.mp4'
     */
    public String suffix() {
        return this.mSuffix;
    }

}