package com.isoler.studyim.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.*;

/**
 * @author liuwang
 * @Date 2022-09-06
 */
@Slf4j
public final class FileUtil {

    private static final String ROOT_TEMPORARY_PATH = System.getProperty("java.io.tmpdir");
    private static final List<String> FILE_SIZE_UNIT = ImmutableList.of("B", "KB", "MB", "GB", "TB", "PB");


    private FileUtil() {
        throw new RuntimeException("静态类不允许初始化");
    }

    /**
     * 下载文件到浏览器
     *
     * @param response
     * @param file
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    public static void downloadFile(HttpServletResponse response, InputStream file, String fileName) throws IOException {
        downloadFile(response, (outputStream) -> {
            try {
                IOUtils.copy(file, outputStream);
            } catch (IOException e) {
                log.error("文件下载失败", e);
                throw new RuntimeException("文件下载失败");
            }
        }, fileName);
    }

    /**
     * 下载文件到浏览器
     *
     * @param response
     * @param bytes
     * @param fileName
     */
    public static void downloadFile(HttpServletResponse response, byte[] bytes, String fileName) {
        downloadFile(response, (outputStream) -> {
            try {
                IOUtils.write(bytes, outputStream);
            } catch (IOException e) {
                log.error("文件下载失败", e);
                throw new RuntimeException("文件下载失败");
            }
        }, fileName);
    }

    /**
     * 下载文件到浏览器
     *
     * @param response
     * @param path
     * @param fileName
     */
    public static void downloadFile(HttpServletResponse response, Path path, String fileName) {
        downloadFile(response, (outputStream) -> {
            try {
                Files.copy(path, outputStream);
            } catch (IOException e) {
                log.error("文件下载失败", e);
                throw new RuntimeException("文件下载失败");
            }
        }, fileName);
    }

    /**
     * 下载文件到浏览器
     *
     * @param response
     * @param consumer
     * @param fileName
     */
    public static void downloadFile(HttpServletResponse response, Consumer<OutputStream> consumer, String fileName) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        try {
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="
                    + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            log.error("文件下载失败，不支持文件编码");
            throw new RuntimeException("文件下载失败，不支持文件编码");
        }
        try (OutputStream os = response.getOutputStream()) {
            consumer.accept(os);
            os.flush();
        } catch (IOException e) {
            log.error("文件下载失败", e);
            throw new RuntimeException("文件下载失败");
        }
    }


    /**
     * 获取文件的md5
     *
     * @param file
     * @return
     */
    public static String getFileMd5(byte[] file) {
        return DigestUtils.md5DigestAsHex(file);
    }

    /**
     * 获取文件的md5
     *
     * @param file
     * @return
     */
    public static String getFileMd5(InputStream file) {
        try {
            return DigestUtils.md5DigestAsHex(file);
        } catch (IOException e) {
            log.error("生成文件md5失败", e);
            throw new RuntimeException("生成文件md5失败");
        }
    }

    /**
     * 获取文件大小 （单位字节）
     *
     * @param file
     * @return
     */
    public static long fileSize(File file) {
        return FileUtils.sizeOf(file);
    }

    /**
     * 获取文件大小str
     *
     * @param fileSize
     * @return
     */
    public static String fileSizeStr(long fileSize) {
        BigDecimal size = BigDecimal.valueOf(fileSize);
        BigDecimal sizeUnit = BigDecimal.valueOf(1024);
        if (fileSize <= 0) {
            return "OKB";
        }
        for (int i = 0; i < FILE_SIZE_UNIT.size(); i++) {
            if (size.compareTo(sizeUnit) < 0) {
                size.toPlainString();
                return new StringBuilder(StringUtils.removeEnd(size.toPlainString(), ".0")).append(FILE_SIZE_UNIT.get(i)).toString();
            }
            size = size.divide(sizeUnit, 1, BigDecimal.ROUND_HALF_UP);
        }
        return new StringBuilder(StringUtils.removeEnd(size.toPlainString(), ".0")).append(FILE_SIZE_UNIT.get(FILE_SIZE_UNIT.size() - 1)).toString();
    }


    public static byte[] toByteArr(InputStream file) {
        try {
            return IOUtils.toByteArray(file);
        } catch (IOException e) {
            log.error("获取文件的字节流失败", e);
            throw new RuntimeException("获取文件的字节流失败");
        }
    }


    public static Path copyFileWithDate(Path src, String prefix) {
        LocalDate now = LocalDate.now();
        Path target = Paths.get(prefix, String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()),
                String.valueOf(now.getDayOfMonth()), IdWorker.getTimeId());
        try {
            Files.createDirectories(target.getParent());
            Files.copy(src, target);
            return target;
        } catch (IOException e) {
            log.error("复制文件失败", e);
            throw new RuntimeException("复制文件失败");
        }
    }

    /**
     * 写入文件到临时路径
     *
     * @param inputStream 文件
     * @return 结果
     */
    public static Path copyToTempFile(InputStream inputStream) {
        Path tempPath = createTempFile(String.valueOf(System.currentTimeMillis()));
        try {
            Files.copy(inputStream, tempPath);
        } catch (IOException e) {
            log.error("写入文件到临时路径失败", e);
            throw new RuntimeException("写入文件到临时路径失败");
        }
        return tempPath;
    }

    /**
     * 写入文件到临时路径
     *
     * @param file 文件
     * @return 结果
     */
    public static Path writeToTempFile(byte[] file) {
        Path tempPath = createTempFile(String.valueOf(System.currentTimeMillis()));
        try {
            Files.write(tempPath, file);
        } catch (IOException e) {
            log.error("写入文件到临时路径失败", e);
            throw new RuntimeException("写入文件到临时路径失败");
        }
        return tempPath;
    }

    /**
     * 创建临时文件
     *
     * @param fileName 文件名
     * @return
     */
    public static Path createTempFile(String fileName) {
        try {
            Path path = Paths.get(ROOT_TEMPORARY_PATH, "file-server", fileName);
            Files.createDirectories(path.getParent());
            return path;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("生成文件失败！");
        }
    }

    /**
     * 创建临时文件夹
     *
     * @param directoryName
     * @return
     */
    public static Path createTempDirectory(String directoryName) {
        try {
            return Files.createDirectories(Paths.get(ROOT_TEMPORARY_PATH, "file-server", directoryName));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("生成文件夹失败！");
        }
    }

    /**
     * 创建文件
     *
     * @param path     目录名
     * @param fileName 文件名
     * @return
     */
    public static Path createFile(Path path, String fileName) {
        Path result = Paths.get(path.toString(), fileName);
        try {
            Files.createDirectories(result.getParent());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("生成文件夹失败！");
        }
        return result;
    }

    /**
     * 解压文件
     *
     * @param file      压缩文件
     * @param targetDir 解压文件输出的目录
     * @throws IOException
     */

    public static void unCompressZip(Path file, Path targetDir) {
        try {
            Files.createDirectories(targetDir);
            try (ZipFile zipFile = new ZipFile(file.toFile())) {
                // 读取zip流
                try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(file))) {
                    ZipEntry zipEntry = null;
                    while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                        String entryName = zipEntry.getName();
                        Path targetPath = targetDir.resolve(entryName);
                        Files.createDirectories(targetPath.getParent());
                        if (zipEntry.isDirectory()) {
                            Files.createDirectories(targetDir);
                        } else {
                            try (InputStream zipEntryInputStream = zipFile.getInputStream(zipEntry)) {
                                Files.copy(zipEntryInputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                            }
                        }

                    }
                }
            }
        } catch (ZipException e) {
            log.error("zip解压失败", e);
            throw new RuntimeException("zip解压失败");
        } catch (IOException e) {
            log.error("zip解压失败", e);
            throw new RuntimeException("zip解压失败");
        }

    }


    /**
     * 压缩文件
     *
     * @param path
     * @return
     */
    public static byte[] compressZip(Path path) {
        Path targetZip = Paths.get(path.toString() + ".zip");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(targetZip));) {
            toZip(zipOutputStream, path.toFile(), path.toFile());
            zipOutputStream.flush();
            zipOutputStream.close();
            return Files.readAllBytes(targetZip);
        } catch (IOException e) {
            log.error("压缩文件失败", e);
            throw new RuntimeException("文件压缩异常", e);
        } finally {
            try {
                Files.deleteIfExists(targetZip);
            } catch (IOException e) {
                log.error("删除临时压缩文件失败");
            }
            try {
                FileUtil.deleteFileRecv(path);
            } catch (IOException e) {
                log.error("删除临时文件夹失败");
            }
        }
    }

    /**
     * 文件压缩
     *
     * @param path
     * @param targetZip
     */
    public static void compressZip(String path, String targetZip) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(Paths.get(targetZip)))) {
            toZip(zipOutputStream, Paths.get(path).toFile(), Paths.get(path).toFile());
            zipOutputStream.flush();
        } catch (IOException e) {
            log.error("压缩文件失败", e);
            throw new RuntimeException("压缩文件失败");
        }
    }

    /**
     * 递归压缩文件
     *
     * @param zipOutputStream
     * @param file
     * @param root
     * @throws IOException
     */
    private static void toZip(ZipOutputStream zipOutputStream, File file, File root) throws IOException {
        if (file.isFile()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                zipOutputStream.putNextEntry(new ZipEntry(file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1)));
                IOUtils.copy(fileInputStream, zipOutputStream);
                zipOutputStream.closeEntry();
                return;
            }
        }
        File[] files = file.listFiles();
        if (ArrayUtils.isEmpty(files)) {
            zipOutputStream.putNextEntry(new ZipEntry(file.getAbsolutePath().substring(root.getAbsolutePath().length()) + File.separator));
            zipOutputStream.closeEntry();
            return;
        }
        for (File file2 : files) {
            toZip(zipOutputStream, file2, root);
        }

    }

    /**
     * 压缩文件（新）
     *
     * @param path
     */
    public static byte[] compressZipNew(Path path) {
        Path targetZip = Paths.get(path.toString() + ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(targetZip))) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    ZipEntry zipEntry = new ZipEntry(file.toString().substring(path.toString().length() + 1));
                    zos.putNextEntry(zipEntry);
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
            zos.flush();
        } catch (FileNotFoundException e) {
            log.error("压缩失败", e);
            throw new RuntimeException("文件压缩失败");
        } catch (IOException e) {
            log.error("压缩失败", e);
            throw new RuntimeException("文件压缩失败");
        } catch (Exception e) {
            log.error("压缩失败", e);
            throw new RuntimeException("文件压缩失败");
        } catch (ZipError e) {
            log.error("压缩失败", e);
            throw new RuntimeException("文件压缩失败");
        } finally {
            try {
                Files.deleteIfExists(targetZip);
            } catch (IOException e) {
                log.error("删除压缩文件夹失败", e);
            }
            try {
                FileUtil.deleteFileRecv(path);
            } catch (IOException e) {
                log.error("删除压缩文件夹失败", e);
            }
        }
        try {
            return Files.readAllBytes(targetZip);
        } catch (IOException e) {
            log.error("删除压缩文件夹失败", e);
            throw new RuntimeException("文件压缩失败");
        }
    }

    /**
     * 递归删除文件
     *
     * @param path
     * @throws IOException
     */
    public static void deleteFileRecv(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
