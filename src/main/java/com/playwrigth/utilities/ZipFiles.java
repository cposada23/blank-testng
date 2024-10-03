package com.playwrigth.utilities;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFiles {

    /**
     * Compress the folder and put the compressed result inside the same path that you sent
     * @param sourceFolderPath The folder that you want to compress, also the compressed result with all the info will be put in this folder
     * @return @{@link Path} to the compressed folder
     * @throws Exception
     */
    public static Path zipFolder(Path sourceFolderPath) throws Exception {
        Path tempFolder = Paths.get("Reports/compressed");
        Files.createDirectories(tempFolder);
        tempFolder = Paths.get(tempFolder.toString(), "compressedReport.zip");
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempFolder.toFile()));
        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("File: " + file);
                zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                Files.copy(file, zos);
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        zos.close();

        File zippedFile = new File(tempFolder.toUri());
        Path destinationPath = Paths.get(
                sourceFolderPath.toString(),
                "compressed",
                zippedFile.getName()
        );
        File destinationFile = new File(
                destinationPath.toString()
        );

        // move to sourceFolderPath
        FileUtils.moveFile(zippedFile, destinationFile);
        return destinationPath;
    }
}
