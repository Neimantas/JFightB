package main.Services.Impl;

import main.Services.ILoggerService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerService implements ILoggerService {

    public void error(String message) {
        try (FileWriter fw = new FileWriter(pathFinder("main/External/Logger.txt"), true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()) + " Error -> " + message);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    private String pathFinder(String relitivePath) {
        String path = new File(getClass().getClassLoader().getResource(relitivePath).getFile()).getAbsolutePath();
        String[] pathArr = path.split("\\\\");
        String[] relitivePathArr = relitivePath.split("/");
        StringBuilder newPath = new StringBuilder();
        boolean targetIsFound = false;
        for (int i = 0; i < pathArr.length; i++) {
            if (pathArr[i].equals("target")) {
                targetIsFound = true;
                newPath.append("src/");
            }
            if (!targetIsFound) {
                newPath.append(pathArr[i]).append("/");
            }

        }
        for (int i = 0; i < relitivePathArr.length; i++) {
            if (i != relitivePathArr.length - 1) {
                newPath.append(relitivePathArr[i]).append("/");
            } else {
                newPath.append(relitivePathArr[i]);
            }
        }
        System.out.println(newPath.toString());
        return newPath.toString();
    }
}
