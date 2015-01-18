package hello.service;

import hello.StringResultHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private StringResultHelper resultHelper;

    public String save(String fileName, MultipartFile file) {

        logger.debug("About to save: {}", fileName);

        if (file.isEmpty()) {
            return resultHelper.toErrorResponse(String.format("Empty file", fileName));
        }

        if (new File(fileName).exists()) {
            return resultHelper.toErrorResponse(String.format("File exists", fileName));
        }

        logger.info("Saving: {}", fileName);

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileName))) {
            stream.write(file.getBytes());
            return resultHelper.successResponse();
        } catch (Exception e) {
            logger.error("Failed to save file: {}", fileName, e);
            return resultHelper.toErrorResponse(e);
        }

    }

    public String listFileNamesUsingCommaSeparatedList(String endsWith) {
        
        // TODO Do it the Java 8 way ...

        final String normalizedEndsWithPart = endsWith.toLowerCase();

        logger.info("Listing files that ends with: {}", normalizedEndsWithPart);

        String[] listFiles = new File(System.getProperty("user.dir")).list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(normalizedEndsWithPart);
            }
        });

        return Arrays.toString(listFiles);
    }
}
