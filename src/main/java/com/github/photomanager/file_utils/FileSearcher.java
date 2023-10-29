package com.github.photomanager.file_utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Interface for searching files in the specified directory.
 * The `FileSearcher` interface defines two methods: `searchFilesRecursive` and `searchFilesByWalkFileTree`.
 * Classes that implement this interface will need to provide implementations for these methods.
 *
 * @author Vladimir F.
 */
public interface FileSearcher {

    List<File> searchFilesRecursive(File rootFile);

    List<Path> searchFilesByWalkFileTree(Path rootPath) throws IOException;
}
