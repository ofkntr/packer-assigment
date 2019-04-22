package com.mobiquityinc.lib.packer;


import com.mobiquityinc.lib.factory.PackageFactory;
import com.mobiquityinc.lib.model.Package;
import com.mobiquityinc.lib.util.PackageFormatter;
import com.mobiquityinc.lib.util.PackageReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Packer {

    private final PackageFactory factory;

    private final PackageFormatter formatter;

    private final PackageReader reader;

    public String pack(String path) {
        String definitions = reader.read(path);
        List<Package> packages = factory.create(definitions);
        return formatter.format(packages);
    }

}
