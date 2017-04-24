package main;

import com.google.common.collect.Lists;
import face.Pattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Or on 21/04/2017.
 */
public class SampleFileGenerator {

    public static void main(String[] args) throws IOException {

        generateSampleFile("sample.txt");
    }

    public static Path generateSampleFile(String fileName) throws IOException {
        ArrayList<Pattern> patterns = getPatterns();
        int size = 100;

        Path path = Paths.get(fileName);
        if(Files.exists(path)){
            Files.delete(path);
        }
        Path file = Files.createFile(path);
        for (int i = 0; i < size-1; i++) {
            String msg = generateLogMsg(patterns)+System.lineSeparator();
            Files.write(file, msg.getBytes(), StandardOpenOption.APPEND);
        }
        String msg = generateLogMsg(patterns);
        Files.write(file, msg.getBytes(),StandardOpenOption.APPEND);
        return path;
    }

    private static String generateLogMsg(ArrayList<Pattern> patterns) {
        Random random = new Random();
        int patternIndex = random.nextInt(patterns.size());
        return patterns.get(patternIndex).generatePattern();
    }
    private static ArrayList<Pattern> getPatterns() {
        Pattern p1 = () -> getDate() + " " + generateRandomWord() + " is getting into the car";

        Pattern p2 = () -> getDate() + " " + generateRandomWord() + " is eating at a diner";

        Pattern p3 = () -> getDate() + " someone approaches " + generateRandomWord() + " at the driveway";

        return Lists.newArrayList(p1, p2, p3);
    }

    private static String generateRandomWord() {
        Random random = new Random();
        char[] word = new char[random.nextInt(5) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }


    private static String getDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
