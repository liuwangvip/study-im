package com.isoler.studyim.common.util;

public class FileNameUtil {
    private static final int NOT_FOUND = -1;
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';


    public static String getName(final String fileName) {
        if (fileName == null) {
            return null;
        }
        requireNonNullChars(fileName);
        final int index = indexOfLastSeparator(fileName);
        return fileName.substring(index + 1);
    }

    private static void requireNonNullChars(final String path) {
        if (path.indexOf(0) >= 0) {
            throw new IllegalArgumentException("Null byte present in file/path name. There are no "
                    + "known legitimate use cases for such data, but several injection attacks may use it");
        }
    }

    public static int indexOfLastSeparator(final String fileName) {
        if (fileName == null) {
            return NOT_FOUND;
        }
        final int lastUnixPos = fileName.lastIndexOf(UNIX_SEPARATOR);
        final int lastWindowsPos = fileName.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }
}
