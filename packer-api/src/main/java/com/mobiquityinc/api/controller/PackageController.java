package com.mobiquityinc.api.controller;

import com.mobiquityinc.api.dto.ResultDto;
import com.mobiquityinc.api.service.FileStorageService;
import com.mobiquityinc.lib.packer.Packer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PackageController {

    private final FileStorageService fileStorageService;

    private final Packer packer;

    /**
     *
     * @param file
     * @return
     */
    @PostMapping("/send-package")
    public ResultDto sendPackage(@RequestParam("file") MultipartFile file) {
        String filePath = fileStorageService.storeFile(file);

        String result = packer.pack(filePath);

        return ResultDto.builder().result(result).build();
    }

}
