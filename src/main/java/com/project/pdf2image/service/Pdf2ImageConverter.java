package com.project.pdf2image.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class Pdf2ImageConverter {
    public List<String> convertPdf2Image(MultipartFile cfile, String outputDirectory, String formatName) throws IOException {
        List<String> outputFilesDestination = new ArrayList<>();
        File file = File.createTempFile("temp", null);
        cfile.transferTo(file);
        //System.out.println(cfile.getName());
        try {
            PDDocument pdDocument = Loader.loadPDF(file);
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            int totalPages = pdDocument.getNumberOfPages();
            for (int page = 0; page < totalPages; page++) {
                String destinationFile = outputDirectory + "image-" + (page + 1) + "." + formatName;
                File outputFile = new File(destinationFile);
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIO.write(bufferedImage, formatName, outputFile);
                outputFilesDestination.add(destinationFile);
            }
            pdDocument.close();
            file.delete();
            return outputFilesDestination;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
