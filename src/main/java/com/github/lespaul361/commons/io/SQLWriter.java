/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lespaul361.commons.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.omg.CORBA.portable.OutputStream;

/**
 *
 * @author David Hamilton
 */
public class SQLWriter implements Closable {

    private  OutputStream output;

    public SQLWriter(Writer out) {
        this.output=output;
    }

    public SQLWriter(OutputStream out) {
        this(new OutputStreamWriter(out));
    }

    public SQLWriter(File file) throws FileNotFoundException {
        this(new FileOutputStream(file));
    }

    public SQLWriter(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }
}
