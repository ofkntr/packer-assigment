package com.mobiquityinc.api.controller;

import com.mobiquityinc.api.dto.ResultDto;
import com.mobiquityinc.api.service.FileStorageService;
import com.mobiquityinc.lib.packer.Packer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PackageControllerTest {

    @InjectMocks
    PackageController packageController;

    @Mock
    FileStorageService fileStorageService;

    @Mock
    Packer packer;

    @Mock
    MultipartFile file;

    ResultDto expectedResultDto;

    @Before
    public void setUp() {
        expectedResultDto = ResultDto.builder().result("4\\n-\\n2,7\\n8,9\\n").build();
    }

    @Test
    public void should_verify_file_Storage_Service() {

        when(fileStorageService.storeFile(file)).thenReturn("/Users/kunter/workspace/packer-assigment/");

        when(packer.pack("/Users/kunter/workspace/packer-assigment/")).thenReturn("4\\n-\\n2,7\\n8,9\\n");

        ResultDto resultDto = packageController.sendPackage(file);

        assertEquals(expectedResultDto.getResult(), resultDto.getResult());

        verify(fileStorageService).storeFile(file);

    }


    @Test
    public void should_verify_packer() {

        when(fileStorageService.storeFile(file)).thenReturn("/Users/kunter/workspace/packer-assigment/");

        when(packer.pack("/Users/kunter/workspace/packer-assigment/")).thenReturn("4\\n-\\n2,7\\n8,9\\n");

        ResultDto resultDto = packageController.sendPackage(file);

        assertEquals(expectedResultDto.getResult(), resultDto.getResult());

        verify(packer).pack("/Users/kunter/workspace/packer-assigment/");

    }



}