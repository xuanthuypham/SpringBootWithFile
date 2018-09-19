package com.xuanthuy.springbootdemo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xuanthuy.springbootdemo.utils.MediaTypeUtils;

@Controller
public class MyFileDowloadController {
	
	private static final String DIRECTORY = "H:/pdf";
	private static final String DEFAULT_FILE_NAME = "java-tutorial.pdf";
	
	@Autowired
	private ServletContext servletContext;
	
    // http://localhost:8080/download1?fileName=abc.zip
    // Using ResponseEntity<InputStreamResource>
	@GetMapping(value = "/dowloadfile")
	public ResponseEntity<InputStreamResource> dowloadFile(@RequestParam(defaultValue=DEFAULT_FILE_NAME) String fileName) throws IOException {
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
		System.out.println("file name: "+fileName);
		System.out.println("mediaType: "+mediaType);
		
		File file = new File(DIRECTORY+File.separator+fileName);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok()
				//Content-Dispostion
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+file.getName())
				//Content-Type
				.contentType(mediaType)
				//Content-Length
				.contentLength(file.length())
				.body(resource);
	}
}
