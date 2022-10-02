package com.isoler.studyim.common.minio.util;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件类型
 *
 * @author liuwang
 * @Date 2022-10-1
 */
public final class FileTypeUtils {
    private static final String TEXT_PLAIN = "text/plain";
    private static final String OCTET_STREAM = "application/octet-stream";
    private static final Map<String, String> EXT_MEDIA_MAP = new HashMap();

    public static String getMediaTypeValue(String ext) {
        String mediaTypeValue = EXT_MEDIA_MAP.get(ext);
        return mediaTypeValue != null ? mediaTypeValue : MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    public Map<String, String> getExtMediaMap() {
        return EXT_MEDIA_MAP;
    }

    private FileTypeUtils() {
    }

    static {
        EXT_MEDIA_MAP.put("jpg", "image/jpeg");
        EXT_MEDIA_MAP.put("jpeg", "image/jpeg");
        EXT_MEDIA_MAP.put("png", "image/png");
        EXT_MEDIA_MAP.put("gif", "image/gif");
        EXT_MEDIA_MAP.put("svg", "image/svg+xml");
        EXT_MEDIA_MAP.put("bmp", "image/bmp");
        EXT_MEDIA_MAP.put("tif", "image/tiff");
        EXT_MEDIA_MAP.put("tiff", "image/tiff");
        EXT_MEDIA_MAP.put("ico", "image/x-icon");
        EXT_MEDIA_MAP.put("webp", "image/webp");
        EXT_MEDIA_MAP.put("swf", "application/x-shockwave-flash");
        EXT_MEDIA_MAP.put("avi", "video/x-msvideo");
        EXT_MEDIA_MAP.put("mp3", "audio/mpeg");
        EXT_MEDIA_MAP.put("mp4", "video/mp4");
        EXT_MEDIA_MAP.put("wav", "audio/wav");
        EXT_MEDIA_MAP.put("pdf", "application/pdf");
        EXT_MEDIA_MAP.put("json", "application/json;charset=UTF-8");
        EXT_MEDIA_MAP.put("html", "text/html");
        EXT_MEDIA_MAP.put("htm", "text/html");
        EXT_MEDIA_MAP.put("mht", "message/rfc822");
        EXT_MEDIA_MAP.put("mhtml", "message/rfc822");
        EXT_MEDIA_MAP.put("js", "text/plain");
        EXT_MEDIA_MAP.put("css", "text/css");
        EXT_MEDIA_MAP.put("xml", "text/xml");
        EXT_MEDIA_MAP.put("txt", "text/plain");
        EXT_MEDIA_MAP.put("log", "text/plain");
        EXT_MEDIA_MAP.put("java", "text/plain");
        EXT_MEDIA_MAP.put("yml", "text/plain");
        EXT_MEDIA_MAP.put("md", "text/markdown");
        EXT_MEDIA_MAP.put("xps", "application/vnd.ms-xpsdocument");
        EXT_MEDIA_MAP.put("doc", "application/msword");
        EXT_MEDIA_MAP.put("dot", "application/msword");
        EXT_MEDIA_MAP.put("docm", "application/vnd.ms-word.document.macroenabled.12");
        EXT_MEDIA_MAP.put("dotm", "application/vnd.ms-word.template.macroenabled.12");
        EXT_MEDIA_MAP.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        EXT_MEDIA_MAP.put("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
        EXT_MEDIA_MAP.put("wps", "application/vnd.ms-works");
        EXT_MEDIA_MAP.put("xls", "application/vnd.ms-excel");
        EXT_MEDIA_MAP.put("xlt", "application/vnd.ms-excel");
        EXT_MEDIA_MAP.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        EXT_MEDIA_MAP.put("xltm", "application/vnd.ms-excel.template.macroenabled.12");
        EXT_MEDIA_MAP.put("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        EXT_MEDIA_MAP.put("ppt", "application/vnd.ms-powerpoint");
        EXT_MEDIA_MAP.put("pot", "application/vnd.ms-powerpoint");
        EXT_MEDIA_MAP.put("pptm", "application/vnd.ms-powerpoint.presentation.macroenabled.12");
        EXT_MEDIA_MAP.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        EXT_MEDIA_MAP.put("zip", "application/zip");
        EXT_MEDIA_MAP.put("tar", "application/x-tar");
        EXT_MEDIA_MAP.put("tgz", "application/x-compressed");
        EXT_MEDIA_MAP.put("gtar", "application/x-gtar");
        EXT_MEDIA_MAP.put("gz", "application/x-gzip");
        EXT_MEDIA_MAP.put("rtf", "application/rtf");
    }
}