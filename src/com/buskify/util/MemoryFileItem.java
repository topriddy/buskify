/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.buskify.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.wicket.util.upload.DiskFileItem;
import org.apache.wicket.util.upload.FileItem;
import org.apache.wicket.util.upload.ParameterParser;


/**
 * <p/>
 * The default implementation of the {@link org.apache.wicket.util.upload.FileItem FileItem}
 * interface.
 * <p/>
 * <p/>
 * After retrieving an instance of this class, you may either request all contents of file at once
 * using {@link #get()} or request an {@link java.io.InputStream InputStream} with
 * {@link #getInputStream()} and process the file without attempting to load it into memory, which
 * may come handy with large files.
 *
 * @author <a href="mailto:Rafal.Krzewski@e-point.pl">Rafal Krzewski</a>
 * @author <a href="mailto:sean@informage.net">Sean Legassick</a>
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:jmcnally@apache.org">John McNally</a>
 * @author <a href="mailto:martinc@apache.org">Martin Cooper</a>
 * @author Sean C. Sullivan
 */
public class MemoryFileItem implements FileItem {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------- Manifest constants


    /**
     * Default content charset to be used when no explicit charset parameter is provided by the
     * sender. Media subtypes of the "text" type are defined to have a default charset value of
     * "ISO-8859-1" when received via HTTP.
     */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";


    /**
     * Size of buffer to use when writing an item to disk.
     */
    private static final int WRITE_BUFFER_SIZE = 2048;


    // ----------------------------------------------------------- Data members


    /**
     * Counter used in unique identifier generation.
     */
    private static int counter = 0;


    /**
     * The name of the form field as provided by the browser.
     */
    private String fieldName;


    /**
     * The content type passed by the browser, or <code>null</code> if not defined.
     */
    private final String contentType;


    /**
     * Whether or not this item is a simple form field.
     */
    private boolean isFormField;


    /**
     * The original filename in the user's filesystem.
     */
    private final String fileName;


    /**
     * Cached contents of the file.
     */
//    private byte[] _data;

    protected ByteArrayOutputStream _out;



    // ----------------------------------------------------------- Constructors


    /**
     * Constructs a new <code>DiskFileItem</code> instance.
     *
     * @param fieldName     The name of the form field.
     * @param contentType   The content type passed by the browser or <code>null</code> if not specified.
     * @param isFormField   Whether or not this item is a plain form field, as opposed to a file upload.
     * @param fileName      The original filename in the user's filesystem, or <code>null</code> if not
     *                      specified.
     */
    public MemoryFileItem(String fieldName, String contentType, boolean isFormField, String fileName) {
        this.fieldName = fieldName;
        this.contentType = contentType;
        this.isFormField = isFormField;
        this.fileName = fileName;
        _out = new ByteArrayOutputStream();
        return;
    }


    // ------------------------------- Methods from javax.activation.DataSource


    /**
     * Returns an {@link java.io.InputStream InputStream} that can be used to retrieve the contents
     * of the file.
     *
     * @return An {@link java.io.InputStream InputStream} that can be used to retrieve the contents
     *         of the file.
     * @throws IOException if an error occurs.
     */
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream( _out.toByteArray() );
    }


    /**
     * Returns the content type passed by the agent or <code>null</code> if not defined.
     *
     * @return The content type passed by the agent or <code>null</code> if not defined.
     */
    public String getContentType() {
        return contentType;
    }


    /**
     * Returns the content charset passed by the agent or <code>null</code> if not defined.
     *
     * @return The content charset passed by the agent or <code>null</code> if not defined.
     */
    public String getCharSet() {
        ParameterParser parser = new ParameterParser();
        parser.setLowerCaseNames(true);
        // Parameter parser can handle null input
        Map<?, ?> params = parser.parse(getContentType(), ';');
        return (String) params.get("charset");
    }


    /**
     * Returns the original filename in the client's filesystem.
     *
     * @return The original filename in the client's filesystem.
     */
    public String getName() {
        return fileName;
    }


    // ------------------------------------------------------- FileItem methods


    /**
     * Provides a hint as to whether or not the file contents will be read from memory.
     *
     * @return <code>true</code> if the file contents will be read from memory; <code>false</code>
     *         otherwise.
     */
    public boolean isInMemory() {
        return true;
    }


    /**
     * Returns the size of the file.
     *
     * @return The size of the file, in bytes.
     */
    public long getSize() {
        return _out.size();
    }


    /**
     * Returns the contents of the file as an array of bytes. If the contents of the file were not
     * yet cached in memory, they will be loaded from the disk storage and cached.
     *
     * @return The contents of the file as an array of bytes.
     */
    public byte[] get() {
        return _out.toByteArray();
    }


    /**
     * Returns the contents of the file as a String, using the specified encoding. This method uses
     * {@link #get()} to retrieve the contents of the file.
     *
     * @param charset The charset to use.
     * @return The contents of the file, as a string.
     * @throws UnsupportedEncodingException if the requested character encoding is not available.
     */
    public String getString(final String charset) throws UnsupportedEncodingException {
        return new String(get(), charset);
    }


    /**
     * Returns the contents of the file as a String, using the default character encoding. This
     * method uses {@link #get()} to retrieve the contents of the file.
     *
     * @return The contents of the file, as a string.
     * @todo Consider making this method throw UnsupportedEncodingException.
     */
    public String getString() {
        byte[] rawdata = get();
        String charset = getCharSet();
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        try {
            return new String(rawdata, charset);
        }
        catch (UnsupportedEncodingException e) {
            return new String(rawdata);
        }
    }


    /**
     * A convenience method to write an uploaded item to disk. The client code is not concerned with
     * whether or not the item is stored in memory, or on disk in a temporary location. They just
     * want to write the uploaded item to a file.
     * <p/>
     * This implementation first attempts to rename the uploaded item to the specified destination
     * file, if the item was originally written to disk. Otherwise, the data will be copied to the
     * specified file.
     * <p/>
     * This method is only guaranteed to work <em>once</em>, the first time it is invoked for a
     * particular item. This is because, in the event that the method renames a temporary file, that
     * file will no longer be available to copy or rename again at a later time.
     *
     * @param file The <code>File</code> into which the uploaded item should be stored.
     * @throws Exception if an error occurs.
     */
    public void write(File file) throws IOException {
        return;
    }


    /**
     * Deletes the underlying storage for a file item, including deleting any associated temporary
     * disk file. Although this storage will be deleted automatically when the <code>FileItem</code>
     * instance is garbage collected, this method can be used to ensure that this is done at an
     * earlier time, thus preserving system resources.
     */
    public void delete() {
        _out.reset();
        return;
    }


    /**
     * Returns the name of the field in the multipart form corresponding to this file item.
     *
     * @return The name of the form field.
     * @see #setFieldName(java.lang.String)
     */
    public String getFieldName() {
        return fieldName;
    }


    /**
     * Sets the field name used to reference this file item.
     *
     * @param fieldName The name of the form field.
     * @see #getFieldName()
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    /**
     * Determines whether or not a <code>FileItem</code> instance represents a simple form field.
     *
     * @return <code>true</code> if the instance represents a simple form field;
     *         <code>false</code> if it represents an uploaded file.
     * @see #setFormField(boolean)
     */
    public boolean isFormField() {
        return isFormField;
    }


    /**
     * Specifies whether or not a <code>FileItem</code> instance represents a simple form field.
     *
     * @param state <code>true</code> if the instance represents a simple form field;
     *              <code>false</code> if it represents an uploaded file.
     * @see #isFormField()
     */
    public void setFormField(boolean state) {
        isFormField = state;
    }


    /**
     * Returns an {@link java.io.OutputStream OutputStream} that can be used for storing the
     * contents of the file.
     *
     * @return An {@link java.io.OutputStream OutputStream} that can be used for storing the
     *         contensts of the file.
     * @throws IOException if an error occurs.
     */
    public OutputStream getOutputStream() throws IOException {
        return _out;
    }

    // --------------------------------------------------------- Public methods

    // ------------------------------------------------------ Protected methods

    // -------------------------------------------------------- Private methods


    /**
     * Returns an identifier that is unique within the class loader used to load this class, but
     * does not have random-like apearance.
     *
     * @return A String with the non-random looking instance identifier.
     */
    private static String getUniqueId() {
        int current;
        synchronized (DiskFileItem.class) {
            current = counter++;
        }
        String id = Integer.toString(current);

        // If you manage to get more than 100 million of ids, you'll
        // start getting ids longer than 8 characters.
        if (current < 100000000) {
            id = ("00000000" + id).substring(id.length());
        }
        return id;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
	{
		return "name=" + getName() +
			", size=" + getSize() + "bytes, " + "isFormField=" + isFormField() + ", FieldName=" +
			getFieldName();
	}
}