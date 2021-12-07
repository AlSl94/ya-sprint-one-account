package dev.struchkov.yandex.report.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class FileUtils {

    private static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
    }

    private static final String FILE_NAME_SEPARATOR = ".";
    private static final String LOCK = "lock";

    private FileUtils() {
        throw new IllegalStateException();
    }

    /**
     * Распаковка zip архива.
     *
     * @param archivePath     путь к архиву
     * @param outputDirectory целевая директория
     */
    public static void unzipFile(Path archivePath, Path outputDirectory) throws IOException {
        Files.createDirectory(outputDirectory);
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(archivePath.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = createFileFromZipEntry(outputDirectory.toFile(), zipEntry);
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    zipEntry = zis.getNextEntry();
                }
            }
            zis.closeEntry();
        }
    }

    /**
     * Создание файла из zip сущности.
     *
     * @param destDir  директория назначения
     * @param zipEntry zip сущность
     * @return сформированный файл
     */
    public static File createFileFromZipEntry(File destDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destDir, zipEntry.getName());
        String destDirPath = destDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }

    /**
     * Удаление файла без возможного возбуждения исключений.
     */
    public static synchronized void deleteFile(Path filePath) {
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Копирование файла без возможного возбуждения исключений.
     */
    public static synchronized void copyFile(Path filePath, Path targetPath) {
        try {
            Files.createDirectories(targetPath);
            Files.copy(filePath, targetPath.resolve(FileUtils.getFileName(filePath)));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Удаление директории.
     *
     * @param directory путь к директории
     */
    public static synchronized void deleteFolder(Path directory) {
        try (Stream<Path> walk = Files.walk(directory)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Метод проверяет наличие .lock файла по дазанному пути.
     *
     * @param path путь (фактически файл) по которому необхордимо проверить наличие .lock
     * @return true если файл существует
     */
    public static synchronized boolean isLockExists(Path path, String nodeName) {
        Path lockPath = Paths.get(String.join(FILE_NAME_SEPARATOR, path.toString(), nodeName, LOCK));
        return lockPath.toFile().exists();
    }

    /**
     * Метод создаёт .lock файл при его отсутствии.
     *
     * @param path путь (фактически файл) по которому необхордимо проверить и удалить при наличии .lock файд
     */
    public static synchronized Path createLock(Path path, String nodeName) {
        try {
            if (!isLockExists(path, nodeName)) {
                Path lockPath = Paths.get(String.join(FILE_NAME_SEPARATOR, path.toString(), nodeName, LOCK));
                return Files.createFile(lockPath);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Метод удаляет .lock файл при наличии.
     *
     * @param path путь (фактически файл) по которому необхордимо проверить и удалить при наличии .lock файд
     */
    public static synchronized void releaseLock(Path path) {
        try {
            Path lockPath = Paths.get(String.join(FILE_NAME_SEPARATOR, path.toString(), LOCK));
            Files.deleteIfExists(lockPath);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Получение имени файла из объекта пути (Path).
     *
     * @param path путь к файлу
     * @return наименования файла
     */
    public static String getFileName(Path path) {
        return path.getFileName().toString();
    }

    /**
     * Получение имени файла без его расширения.
     *
     * @see #getFileNameWithoutExtension(String fileName)
     */
    public static String getFileNameWithoutExtension(Path path) {
        return getFileNameWithoutExtension(FileUtils.getFileName(path));
    }

    /**
     * Получение имени файла без его расширения.
     */
    public static String getFileNameWithoutExtension(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    /**
     * Получение расширения файла.
     */
    public static String getFileExtension(Path path) {
        return getFileExtension(getFileName(path));
    }

    /**
     * Получение расширения файла.
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    /**
     * Получение корректного пути к директории.
     */
    public static String getCorrectlyFolderLocation(String folderPath) {
        Path targetPath = Paths.get(folderPath);
        StringBuilder folderLocation = new StringBuilder("file:///").append(targetPath.toAbsolutePath());
        if (!targetPath.endsWith(File.pathSeparator)) {
            folderLocation.append(File.separator);
        }
        return folderLocation.toString();
    }

    public static String getFileChecksum(Path path) {
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
