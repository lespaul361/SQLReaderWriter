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
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 *
 * @author David Hamilton
 */
public class MySQLReader extends Reader implements Closeable {

    private char[] codeChars = new char[100];
    private int commandSize = 0;
    private final String endofCommand = ";";
    private BufferedReader sourceReader = null;
    private final String LINE_COMMENT = "/*";
    private final String LINE_COMMENT_END = "*\\";
    private final String In_LINE_COMMENT = "--";
    private final String In_LINE_COMMENT_2 = "#";
    private final char OPEN_PARENTHESES = "(".charAt(0);
    private final char CLOSED_PARENTHESES = ")".charAt(0);

    /**
     * Creates a new MySQLReader
     *
     * @param in a reader with the SQL commands
     */
    public MySQLReader(Reader in) {
        this.sourceReader = new BufferedReader(in);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param in an {@link InputStream} with the SQL commands
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLReader(InputStream in) throws UnsupportedEncodingException {
        Reader r = new InputStreamReader(in, "UTF-8");
        this.sourceReader = new BufferedReader(r);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param in an {@link InputStream} with the SQL commands
     * @param dec the {@link CharsetDecoder} to use
     */
    public MySQLReader(InputStream in, CharsetDecoder dec) {
        Reader r = new InputStreamReader(in, dec);
        this.sourceReader = new BufferedReader(r);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param in an {@link InputStream} with the SQL commands
     * @param cs a {@link Charset} with the file encoding
     */
    public MySQLReader(InputStream in, Charset cs) {
        Reader r = new InputStreamReader(in, cs);
        this.sourceReader = new BufferedReader(r);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param in an {@link InputStream} with the SQL commands
     * @param charsetName a {@link String} with the name of the character set to
     * use
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLReader(InputStream in, String charsetName) throws UnsupportedEncodingException {
        Reader r = new InputStreamReader(in, charsetName);
        this.sourceReader = new BufferedReader(r);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param file the file with the SQL commands
     * @param charsetName a {@link String} with the name of the character set to
     * use
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLReader(File file, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {
        this(new FileInputStream(file), charsetName);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param file the file with the SQL commands
     * @param cs a {@link Charset} with the file encoding
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    public MySQLReader(File file, Charset cs) throws FileNotFoundException {
        this(new FileInputStream(file), cs);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param file the file with the SQL commands
     * @param dec the {@link CharsetDecoder} to use
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    public MySQLReader(File file, CharsetDecoder dec) throws FileNotFoundException {
        this(new FileInputStream(file), dec);
    }

    /**
     * Creates a new MySQLReader using a default of UTF-8 encoding.
     *
     * @param file the file with the SQL commands
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLReader(File file) throws FileNotFoundException, UnsupportedEncodingException {
        this(new FileInputStream(file), "UTF-8");
    }

    /**
     * Creates a new MySQLReader
     *
     * @param filePath the path to the SQL file
     * @param charsetName a {@link String} with the name of the character set to
     * use
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLReader(String filePath, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {
        this(new File(filePath), charsetName);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param filePath the path to the SQL file
     * @param cs a {@link Charset} with the file encoding
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    public MySQLReader(String filePath, Charset cs) throws FileNotFoundException {
        this(new File(filePath), cs);
    }

    /**
     * Creates a new MySQLReader
     *
     * @param filePath the path to the SQL file
     * @param dec the {@link CharsetDecoder} to use
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    public MySQLReader(String filePath, CharsetDecoder dec) throws FileNotFoundException {
        this(new File(filePath), dec);
    }

    /**
     * Creates a new MySQLReader using a default of UTF-8 encoding.
     *
     * @param filePath the path to the SQL file
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLReader(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        this(new File(filePath), "UTF-8");
    }

    @Override
    public void close() throws IOException {
        this.sourceReader.close();
    }

    public String readCommand() throws IOException {
        StringBuilder sb = new StringBuilder(100);
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
            if (line.contains(In_LINE_COMMENT_2)) {
                line = line.substring(0, line.indexOf(In_LINE_COMMENT_2));
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

    /**
     * This method is not supported for this reader. User {@link #readCommand() }
     * @param cbuf
     * @param off
     * @param len
     * @return UnsupportedOperationException exception
     * @throws IOException 
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        throw new UnsupportedOperationException("Not supported. Use readCommand");
    }
}
