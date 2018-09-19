package com.xuanthuy.springbootdemo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xuanthuy.springbootdemo.form.MyUploadForm;

@RestController
public class MyUpoloadRESTController {

	//Linux /home/{user}/test
	//Windows C:/User/{user}/test
	private static final String UPLOAD_DIR = System.getProperty("user.home")+File.separator+"test";
	
	@PostMapping(value="/rest/uploadmultifiles")
	public ResponseEntity<?> multiUploadFile(@ModelAttribute MyUploadForm uploadForm ){
		System.out.println(uploadForm.getDescription());
		String result = null;
		System.out.println("hihihahahahahahahhaahahhahahahahahha");
		if(uploadForm.getFileDatas()==null) {
			System.out.println("null me no roi");
		}
		try {
			result = this.saveUploadFile(uploadForm.getFileDatas());
		} catch(IOException e) {
			//Ngoai le bat IOException
			//Cac ngoai le khac se duoc bat boi RestGlobalException
			e.printStackTrace();
			return new ResponseEntity<>("Error: "+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Upload file for <br>"+result,HttpStatus.OK);
	}
	
	//Save file
	public String saveUploadFile(MultipartFile[] files) throws IOException {
		File uploadDir = new File(UPLOAD_DIR);
		uploadDir.mkdirs();
		StringBuilder sb = new StringBuilder();
		for(MultipartFile file:files) {
			if(file.isEmpty()) {
				continue;
			}
		//	String dir = System.getProperty("user.dir");
	//		String uploadFilePath = dir+File.separator+"src/main/resources/static/images"+File.separator+file.getOriginalFilename();
			String uploadFilePath = UPLOAD_DIR+File.separator+file.getOriginalFilename();
			System.out.println(uploadFilePath);
			byte[] bytes = file.getBytes();
			Path path = Paths.get(uploadFilePath);
			Files.write(path, bytes);
			sb.append(uploadFilePath).append("<br>");
		}
		return sb.toString();
	}
}
