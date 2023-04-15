package com.example.PortfolioBackend.DTOs;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MultipartFileImpl implements MultipartFile {


    private final String name;
    private final String originalFileName;
    private final String contentType;
    byte[] bytes;


    public MultipartFileImpl(File file) throws IOException {
        this.name = file.getName();
        this.originalFileName = file.getName();
        this.contentType = Files.probeContentType(file.toPath());
        this.bytes = Files.readAllBytes(file.toPath());
    }



    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes==null?0:bytes.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }
}
