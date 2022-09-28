package com.isoler.studyim.zip;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: liuwang
 * @Date: 2022-07-26
 */
@Slf4j
public class ZipTest {
    private static ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    @Test
    public void compressZip() {
        compressZip(Paths.get("D:\\测试\\中文目录"));
    }

    public static byte[] compressZip(Path path) {
        Path targetZip = Paths.get(path.toString() + ".zip");
        try {
            ZipOutputStream zipOutputStream = null;
            zipOutputStream = new ZipOutputStream(Files.newOutputStream(targetZip));
            toZip(zipOutputStream, path.toFile(), path.toFile());
            zipOutputStream.flush();
            zipOutputStream.close();
            return Files.readAllBytes(targetZip);
        } catch (IOException e) {
            log.error("压缩文件失败", e);
            throw new RuntimeException("文件压缩异常", e);
        } finally {
//            try {
//                Files.deleteIfExists(targetZip);
//            } catch (IOException e) {
//                log.error("删除临时压缩文件失败");
//            }
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("删除临时文件夹失败");
            }
        }
    }


    private static void toZip(ZipOutputStream zipOutputStream, File file, File root) {
        if (file.isFile()) {
            try {
                zipOutputStream.putNextEntry(new ZipEntry(file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1)));
                FileChannel channel = new FileInputStream(file).getChannel();
                while (true) {
                    byteBuffer.clear();
                    int read = channel.read(byteBuffer);
                    if (read == -1) break;
                    zipOutputStream.write(byteBuffer.array());
                }
                channel.close();
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                log.error("压缩文件失败", e);
            }
        } else {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                try {
                    zipOutputStream.putNextEntry(new ZipEntry(file.getAbsolutePath().substring(root.getAbsolutePath().length()) + File.separator));
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    log.error("压缩文件失败", e);
                }
            } else {
                for (File file2 : files) {
                    toZip(zipOutputStream, file2, root);
                }
            }
        }
    }

}
