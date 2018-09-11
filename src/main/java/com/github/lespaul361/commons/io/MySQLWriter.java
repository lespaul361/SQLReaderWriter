/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lespaul361.commons.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.omg.CORBA.portable.OutputStream;

/**
 *
 * @author David Hamilton
 */
public class MySQLWriter extends Writer implements Closeable {

    private final BufferedWriter outWriter;

    /**
     * Creates a new MySQLWriter
     *
     * @param out the {@link Writer} to write to
     */
    public MySQLWriter(Writer out) {
        this.outWriter = new BufferedWriter(out);
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param out the {@link OutputStream} to use to write the data
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLWriter(OutputStream out) throws UnsupportedEncodingException {
        this(new OutputStreamWriter(out, "UTF-8"));
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param out the {@link OutputStream} to use to write the data
     * @param cs a {@link Charset} with the file encoding
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLWriter(OutputStream out, Charset cs) throws UnsupportedEncodingException {
        this(new OutputStreamWriter(out, cs));
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param out the {@link OutputStream} to use to write the data
     * @param enc the {@link CharsetEncoder} to use
     * @throws java.io.UnsupportedEncodingException
     * The Character Encoding is not supported.
     */
    public MySQLWriter(OutputStream out, CharsetEncoder enc) throws UnsupportedEncodingException {
        this(new OutputStreamWriter(out, enc));
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param out the {@link OutputStream} to use to write the data
     * @param charsetName the name of the character set to user
     */
    public MySQLWriter(OutputStream out, String charsetName) {
        this(new OutputStreamWriter(out));
    }

    /**
     * Creates a new MySQLWriter with a default encoding of UTF-8
     *
     * @param file the file to write to
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws UnsupportedEncodingException The Character Encoding is not supported.
     */
    public MySQLWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
        this(file, "UTF-8");
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param file the file to write to
     * @param cs a {@link Charset} with the file encoding
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws UnsupportedEncodingException The Character Encoding is not supported.
     */
    public MySQLWriter(File file, Charset cs) throws FileNotFoundException, UnsupportedEncodingException {
        this(getWriterFromFile(file, cs));
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param file the file to write to
     * @param enc the {@link CharsetEncoder} to use
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws UnsupportedEncodingException The Character Encoding is not supported.
     */
    public MySQLWriter(File file, CharsetEncoder enc) throws FileNotFoundException, UnsupportedEncodingException {
        this(getWriterFromFile(file, enc));
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param file the file to write to
     * @param charsetName the name of the character set to user
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws UnsupportedEncodingException The Character Encoding is not supported.
     */
    public MySQLWriter(File file, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {
        this(getWriterFromFile(file, charsetName));
    }

    /**
     * Creates a new MySQLWriter with a default encoding of UTF-8
     *
     * @param filePath the path of the file to write to
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws UnsupportedEncodingException The Character Encoding is not supported.
     */
    public MySQLWriter(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        this(new File(filePath), "UTF-8");
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param filePath the path of the file to write to
     * @param cs a {@link Charset} with the file encoding
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws UnsupportedEncodingException The Character Encoding is not supported.
     */
    public MySQLWriter(String filePath, Charset cs) throws FileNotFoundException, UnsupportedEncodingException {
        this(new File(filePath), cs);
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param filePath the path of the file to write to
     * @param enc the {@link CharsetEncoder} to use
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws UnsupportedEncodingException The Character Encoding is not supported.
     */
    public MySQLWriter(String filePath, CharsetEncoder enc) throws FileNotFoundException, UnsupportedEncodingException {
        this(new File(filePath), enc);
    }

    /**
     * Creates a new MySQLWriter
     *
     * @param filePath the path of the file to write to
     * @param charsetName the name of the character set to user
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     * @throws UnsupportedEncodingException The Character Encoding is not supported.
     */
    public MySQLWriter(String filePath, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {
        this(new File(filePath), charsetName);
    }

    private static Writer getWriterFromFile(File file, Object enc) throws FileNotFoundException, UnsupportedEncodingException {
        FileOutputStream out = new FileOutputStream(file);
        if (enc instanceof String) {
            return new OutputStreamWriter(out, (String) enc);
        }
        if (enc instanceof Charset) {
            return new OutputStreamWriter(out, (Charset) enc);
        }
        if (enc instanceof CharsetEncoder) {
            return new OutputStreamWriter(out, (CharsetEncoder) enc);
        }
        return null;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void flush() throws IOException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }
}
