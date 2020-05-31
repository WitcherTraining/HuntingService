package com.epam.huntingService.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import static com.epam.huntingService.util.ParameterNamesConstants.PDF_CONTENT_TYPE;

public class FileManager {
    private static final int MAX_SIZE = 4096;
    private static final int FILE_OVER = -1;
    private static final int START_OFFSET = 0;
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String DEFAULT_FILE_NAME = "attachment;filename = doc.pdf";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String EMPTY_STRING = "";

    public String takeFileFromDataBase(Blob blob) throws SQLException, IOException {

        String base64String;
        byte[] buffer;
        int bytesRead;

        if (blob == null) {
            return EMPTY_STRING;
        } else {
            buffer = new byte[MAX_SIZE];

            InputStream inputStream = blob.getBinaryStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while ((bytesRead = inputStream.read(buffer)) != FILE_OVER) {
                outputStream.write(buffer, START_OFFSET, bytesRead);
            }

            byte[] imageBytes = outputStream.toByteArray();
            base64String = Base64.getEncoder().encodeToString(imageBytes);
            inputStream.close();
            outputStream.close();
            return base64String;
        }
    }

    public String convertToBase64(ByteArrayOutputStream out) throws IOException {

        String base64String;

        if (out == null) {
            return EMPTY_STRING;
        } else {
            byte[] imageBytes = out.toByteArray();
            base64String = Base64.getEncoder().encodeToString(imageBytes);
            out.close();
            return base64String;
        }
    }

    public void downloadFile(HttpServletResponse response, Blob document) throws IOException, SQLException {
        response.setContentType(PDF_CONTENT_TYPE);
        response.setHeader(CONTENT_DISPOSITION, DEFAULT_FILE_NAME);
        response.setHeader(CONTENT_LENGTH, String.valueOf(document.length()));

        InputStream inputStream = document.getBinaryStream();

        ServletOutputStream outputStream = response.getOutputStream();

        byte[] outputByte = new byte[MAX_SIZE];
        int bytesRead;

        while ((bytesRead = inputStream.read(outputByte)) != FILE_OVER) {
            outputStream.write(outputByte, START_OFFSET, bytesRead);
        }

        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }
}
