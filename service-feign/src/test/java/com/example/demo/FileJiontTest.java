package com.example.demo;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Collectors;

public class FileJiontTest {
    public class MyFileVisitor extends SimpleFileVisitor<Path> {
        Path writepath = Paths.get("G:\\ssc-workspace\\SSCrobot\\b.txt");
        private void find(Path path) {
            System.out.printf("访问-%s:%s%n", (Files.isDirectory(path) ? "目录" : "文件"), path.getFileName());
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if(file.getFileName().toString().indexOf(".java")==-1) return FileVisitResult.CONTINUE;
            find(file);
            List<String> readline = Files.readAllLines(file);
            Files.write(writepath,readline,StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException e) {
            System.out.println(e);
            return FileVisitResult.CONTINUE;
        }
    }

        @Test
    public void JiontTest(){
        String filename = "G:\\ssc-workspace\\SSCrobot\\code\\trunk\\risk\\src";
        Path path = Paths.get(filename);
        try {
            Files.walkFileTree(path, new MyFileVisitor());
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
