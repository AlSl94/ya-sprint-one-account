package dev.struchkov.yandex.report.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.io.IOException;
import java.nio.file.Path;

public final class FileUtils {

    private FileUtils() {
        throw new IllegalStateException("Утилитарный класс");
    }

    public static String getFileName(Path path) {
        return path.getFileName().toString();
    }

    public static String getFileChecksum(Path path) {
        try {
            final HashCode hash;
            hash = com.google.common.io.Files
                    .hash(path.toFile(), Hashing.md5());
            return hash.toString()
                    .toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
