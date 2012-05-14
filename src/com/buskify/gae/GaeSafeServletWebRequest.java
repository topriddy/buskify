package com.buskify.gae;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.upload.FileUploadException;

/**
 * @author uudashr
 * 
 */
public class GaeSafeServletWebRequest extends ServletWebRequest {

	public GaeSafeServletWebRequest(HttpServletRequest httpServletRequest,
			String filterPrefix) {
		super(httpServletRequest, filterPrefix);
		FileCleaner.reapFiles(2);
	}

	@Override
	public MultipartServletWebRequest newMultipartWebRequest(Bytes maxSize,
			String upload) throws FileUploadException {
		// TODO Auto-generated method stub
		return super.newMultipartWebRequest(maxSize, upload);
	}

	
	
//	@Override
//	public WebRequest newMultipartWebRequest(Bytes maxsize) {
//		try {
//			return new GaeSafeMultipartServletWebRequest(
//					getHttpServletRequest(), maxsize);
//		} catch (FileUploadException e) {
//			throw new WicketRuntimeException(e);
//		}
//	}
}
