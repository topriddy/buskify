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
package com.buskify.gae;

import java.io.File;

import org.apache.wicket.util.upload.FileItem;
import org.apache.wicket.util.upload.FileItemFactory;

/**
 * <p>
 * The default {@link org.apache.wicket.util.upload.FileItemFactory} implementation. This
 * implementation creates {@link org.apache.wicket.util.upload.FileItem} instances which keep their
 * content either in memory, for smaller items, or in a temporary file on disk, for larger items.
 * The size threshold, above which content will be stored on disk, is configurable, as is the
 * directory in which temporary files will be created.
 * </p>
 * 
 * <p>
 * If not otherwise configured, the default configuration values are as follows:
 * <ul>
 * <li>Size threshold is 10KB.</li>
 * <li>Repository is the system default temp directory, as returned by
 * <code>System.getProperty("java.io.tmpdir")</code>.</li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:martinc@apache.org">Martin Cooper</a>
 */
public class GaeFileItemFactory implements FileItemFactory
{

	// ----------------------------------------------------- Manifest constants


	/**
	 * The default threshold above which uploads will be stored on disk.
	 */
	public static final int DEFAULT_SIZE_THRESHOLD = 10240;


	// ----------------------------------------------------- Instance Variables



	/**
	 * The threshold above which uploads will be stored on disk.
	 */
	private int sizeThreshold = DEFAULT_SIZE_THRESHOLD;


	// ----------------------------------------------------------- Constructors


	/**
	 * Constructs an unconfigured instance of this class. The resulting factory may be configured by
	 * calling the appropriate setter methods.
	 */
	public GaeFileItemFactory()
	{
	}


	/**
	 * Constructs a preconfigured instance of this class.
	 * 
	 * @param sizeThreshold
	 *            The threshold, in bytes, below which items will be retained in memory and above
	 *            which they will be stored as a file.
	 */
	public GaeFileItemFactory(int sizeThreshold, File repository)
	{
		this.sizeThreshold = sizeThreshold;
	}


	// ------------------------------------------------------------- Properties



	/**
	 * Returns the size threshold beyond which files are written directly to disk. The default value
	 * is 1024 bytes.
	 * 
	 * @return The size threshold, in bytes.
	 * 
	 * @see #setSizeThreshold(int)
	 */
	public int getSizeThreshold()
	{
		return sizeThreshold;
	}


	/**
	 * Sets the size threshold beyond which files are written directly to disk.
	 * 
	 * @param sizeThreshold
	 *            The size threshold, in bytes.
	 * 
	 * @see #getSizeThreshold()
	 * 
	 */
	public void setSizeThreshold(int sizeThreshold)
	{
		this.sizeThreshold = sizeThreshold;
	}


	// --------------------------------------------------------- Public Methods

	/**
	 * Create a new {@link org.apache.wicket.util.upload.DiskFileItem} instance from the supplied
	 * parameters and the local factory configuration.
	 * 
	 * @param fieldName
	 *            The name of the form field.
	 * @param contentType
	 *            The content type of the form field.
	 * @param isFormField
	 *            <code>true</code> if this is a plain form field; <code>false</code> otherwise.
	 * @param fileName
	 *            The name of the uploaded file, if any, as supplied by the browser or other client.
	 * 
	 * @return The newly created file item.
	 */
	public FileItem createItem(String fieldName, String contentType, boolean isFormField,
			String fileName)
	{
		return new GaeFileItem(fieldName, contentType, isFormField, fileName, sizeThreshold);
	}
}