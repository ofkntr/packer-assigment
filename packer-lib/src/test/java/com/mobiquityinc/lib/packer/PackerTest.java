package com.mobiquityinc.lib.packer;

import com.mobiquityinc.lib.factory.PackageFactory;
import com.mobiquityinc.lib.model.Package;
import com.mobiquityinc.lib.util.PackageFormatter;
import com.mobiquityinc.lib.util.PackageReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PackerTest {

    private static final String PATH_STUB = "some-path";

    @InjectMocks
    private Packer packer;

    @Mock
    private PackageFormatter formatter;

    @Mock
    private PackageFactory factory;

    @Mock
    private PackageReader reader;

    @Test
    public void should_call_reader_with_provided_path() {
        packer.pack(PATH_STUB);
        verify(reader, times(1)).read(PATH_STUB);
    }

    @Test
    public void should_call_packages_factory_with_definition() {
        String definition = "fake-definition";
        when(reader.read(PATH_STUB)).thenReturn(definition);
        packer.pack(PATH_STUB);
        verify(factory, times(1)).create(definition);
    }

    @Test
    public void should_call_formatter_with_created_packages() {
        List<Package> packages = singletonList(new Package(0, emptyList()));
        String packagesOutput = "some-output";
        when(factory.create(any())).thenReturn(packages);
        when(formatter.format(packages)).thenReturn(packagesOutput);
        assertThat(packer.pack(PATH_STUB), is(packagesOutput));
    }

}