package com.mobiquityinc.lib.util;

import com.mobiquityinc.lib.exception.APIException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

@Slf4j
@Component
@NoArgsConstructor
public class PackageReader {

    /**
     * Read path according to given path
     * @param path
     * @return
     */
    public String read(String path) {
        if (path == null) {
            throw new APIException("Path should not be null or empty.");
        }
        Path file = get(path);
        if (!exists(file)) {
            throw new APIException("Path should exists.");
        }
        return getFileContent(path);
    }

    /**
     * Get file content according to given path
     * @param path
     * @return
     */
    private String getFileContent(String path) {
        try {
            return new String(readAllBytes(get(path)), UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
