package com.mobiquityinc.api.service;

import com.mobiquityinc.lib.util.PackageReader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.verify;

import java.nio.file.Path;

import static com.mobiquityinc.api.util.TempFileUtil.createTempFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class FileStorageServiceTest {

    @InjectMocks
    FileStorageService fileStorageService;

    @Mock
    MultipartFile multipartFile;

    @InjectMocks
    PackageReader reader;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private String uploadDirectory = "/Users/kunter/workspace/packer-assigment/~/uploaded/data.txt";

    @Test
    public void should_store_file() {
        Path file = createTempFile(uploadDirectory, temporaryFolder);
        assertThat(reader.read(file.toString()), is(uploadDirectory));
    }



}