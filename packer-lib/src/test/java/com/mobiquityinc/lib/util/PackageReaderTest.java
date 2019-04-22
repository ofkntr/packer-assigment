package com.mobiquityinc.lib.util;

import com.mobiquityinc.lib.exception.APIException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.file.Path;

import static com.mobiquityinc.lib.util.TempFileUtil.createTempFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class PackageReaderTest {

    @InjectMocks
    PackageReader reader;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test(expected = APIException.class)
    public void should_throw_exception_if_path_is_null() {
        reader.read(null);
    }

    @Test(expected = APIException.class)
    public void should_throw_exception_if_path_is_not_exists() {
        reader.read("fake-path");
    }

    @Test
    public void should_return_content_from_file() {
        String content = "some-content";
        Path file = createTempFile(content, temporaryFolder);
        assertThat(reader.read(file.toString()), is(content));
    }

}