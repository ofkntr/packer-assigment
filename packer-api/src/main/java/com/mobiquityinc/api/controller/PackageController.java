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
     * A simple end point to calculate service is to consider all subsets
     * of items and calculate the total weight and value of all subsets.
     * Consider the only subsets whose total weight is smaller than W.
     * From all such subsets, pick the maximum value subset.
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
