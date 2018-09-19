package com.xuanthuy.springbootdemo.utils;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;

public class MediaTypeUtils {
	//abc.zip,abc.pdf,application/pdf,....
	public static MediaType getMediaTypeForFileName(ServletContext context,String fileName) {
		
		String mineType = context.getMimeType(fileName);
		try {
		MediaType mediaType = MediaType.parseMediaType(mineType);
		return mediaType;
		} catch (Exception e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
}
