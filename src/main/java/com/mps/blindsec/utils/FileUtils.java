package com.mps.blindsec.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static void createPath(Path path) throws IOException {
        if (!Files.exists(path))
            Files.createDirectory(path);
    }
}
