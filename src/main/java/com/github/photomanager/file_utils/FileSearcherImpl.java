package com.github.photomanager.file_utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileSearcherImpl class is an implementation of the {@code FileSearcher} interface.
 * Performs search for files in the specified directory.
 *
 * @author Vladimir F.
 * @see FileSearcher
 */
@Component
public class FileSearcherImpl implements FileSearcher {

    private static final String FILE_EXTENSION = ".jpg";

    /**
     * The `searchFiles` method is a recursive method that takes a `File` object called `rootFile` as a parameter. It
     * searches for files with the extension ".jpg" in the directory specified by the `rootFile` parameter and its subdirectories.
     *
     * @param rootFile root directory for search files
     * @return list of files
     */
    @Override
    public List<File> searchFilesRecursive(File rootFile) {
        List<File> resultList = new ArrayList<>();
        if (rootFile.isDirectory()) {
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        resultList.addAll(searchFilesRecursive(file));
                    } else {
                        if (file.getName().toLowerCase().endsWith(FILE_EXTENSION)) {
                            resultList.add(file);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * The `searchFilesByWalkFileTree` method is using the `Files.walkFileTree` method to perform a recursive search for
     * files in the specified directory (`rootPath`) and its subdirectories.
     *
     * @param rootPath root directory for search files
     * @return list of files
     * @throws IOException - if an I/O error is thrown by a visitor method (in method Files.walkFileTree)
     */
    @Override
    public List<Path> searchFilesByWalkFileTree(Path rootPath) throws IOException {
        final List<Path> fileList = new ArrayList<>();

        Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (attrs.isRegularFile()) {
                    fileList.add(file);
                }
                return super.visitFile(file, attrs);
            }
        });
        return fileList;
    }
}
