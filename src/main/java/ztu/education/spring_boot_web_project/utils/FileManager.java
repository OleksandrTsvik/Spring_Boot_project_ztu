package ztu.education.spring_boot_web_project.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileManager {
    public static String uploadFile(MultipartFile file, String pathDir) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        File uploadDir = new File(pathDir);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String filename = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(pathDir + "\\" + filename));

        return filename;
    }

    public static boolean deleteFile(String pathFile) throws IOException {
        Path path = Paths.get(pathFile);
        return Files.deleteIfExists(path);
    }
}
