package com.example.fhome.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    public String uploadFile(MultipartFile multipartFile) throws IOException;
}
