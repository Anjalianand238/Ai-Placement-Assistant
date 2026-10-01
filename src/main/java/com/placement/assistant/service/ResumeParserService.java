package com.placement.assistant.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ResumeParserService {

    public String extractText(MultipartFile file) throws IOException {

        byte[] pdfBytes = file.getBytes();

        try (PDDocument doc = Loader.loadPDF(pdfBytes)) {
            return new PDFTextStripper().getText(doc);
        }
    }
}