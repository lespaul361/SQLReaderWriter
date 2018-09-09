/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lespaul361.commons.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author David Hamilton
 */
public class SQLReader implements Closeable {

    private char[] codeChars = new char[100];
    private int commandSize = 0;
    private final String endofCommand = ";";
    private BufferedReader sourceReader = null;
    private final String LINE_COMMENT = "/*";
    private final String LINE_COMMENT_END = "*\\";
    private final String In_LINE_COMMENT = "--";
    private final char OPEN_PARENTHESES = "(".charAt(0);

    public SQLReader(Reader in) {
        this.sourceReader = new BufferedReader(in);
    }

    public SQLReader(InputStream in) {
        this(new InputStreamReader(in));
    }

    public SQLReader(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    public SQLReader(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }

    @Override
    public void close() throws IOException {
        this.sourceReader.close();
    }

    public String readCommand() throws IOException {
        StringBuffer sb = new StringBuffer(100);
        String line = null;
        boolean isEndOfCommand = false;
        while (!isEndOfCommand) {
            StringBuilder lineBuilder = new StringBuilder(50);
            line = sourceReader.readLine();
            if (line == null) {
                return null;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.contains(LINE_COMMENT)) {
                StringBuilder tmpBuilder = new StringBuilder(50);
                int pos = 0;
                while (!line.trim().isEmpty() && !line.contains(LINE_COMMENT)) {
                    tmpBuilder.append(line.substring(0, line.indexOf(LINE_COMMENT)));
                    line = line.substring(line.indexOf(LINE_COMMENT_END + LINE_COMMENT_END.length()));
                    pos = line.indexOf(LINE_COMMENT_END) + 2;
                    line = line.substring(pos);
                }
                lineBuilder.append(tmpBuilder);
            }
            if (line.contains(In_LINE_COMMENT)) {
                line = line.substring(0, line.indexOf(In_LINE_COMMENT));
            }
            if (line.trim().isEmpty() && lineBuilder.length() == 0) {
                continue;
            }
            lineBuilder.append(line);
            if (sb.length() > 0) {
                if (sb.charAt(sb.length() - 1) != OPEN_PARENTHESES) {
                    sb.append(" ");
                }
            }
            sb.append(lineBuilder.toString().trim());
            if (line.contains(endofCommand)) {
                isEndOfCommand = true;
            }
        }
        return sb.toString();
    }
}
