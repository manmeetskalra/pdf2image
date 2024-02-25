package com.project.pdf2image.controller;

import com.project.pdf2image.service.Pdf2ImageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class Pdf2ImageController {

    final String outputDirectory = "/Users/manme/Development/projects/pdf2image/src/main/resources/static/images";
    @PostMapping(value = "/convert")
    public List<String> convert(@RequestParam("file")MultipartFile file, @RequestParam("formatName") String formatName) throws IOException {
        System.out.println("Inside controller");
        Pdf2ImageConverter pdf2ImageConverter = new Pdf2ImageConverter();
        List<String> outputFilesDestination = pdf2ImageConverter.convertPdf2Image(file, outputDirectory, formatName);
        return outputFilesDestination;
    }
}
