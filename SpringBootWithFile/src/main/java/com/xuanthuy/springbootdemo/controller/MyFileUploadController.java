package com.xuanthuy.springbootdemo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.xuanthuy.springbootdemo.form.MyUploadForm;

@Controller
public class MyFileUploadController {
	
	@RequestMapping(value="/")
	public String homePage() {
		return "index";
	}
	
	//GET: hien thi form upload
	@GetMapping(value="/uploadonefile")
	public String viewUploadOnefile(Model model) {
		MyUploadForm form = new MyUploadForm();
		model.addAttribute("uploadform",form);
		return "uploadonefile";
	}
	
	//POST: Xu ly upload
	@PostMapping(value="/uploadonefile")
	public String uploadOneFilePost(HttpServletRequest request,Model model,@ModelAttribute("uploadform") MyUploadForm form) {
		return this.doUpload(request, model, form);
	}
	
	//Hien thi form uploadmulti
	@GetMapping(value="/uploadmultifile")
	public String viewUploadMutilFile(Model model) {
		MyUploadForm form = new MyUploadForm();
		model.addAttribute("uploadform", form);
		return "uploadmultifile";
	}
	
	//Xu ly upload multifile
	@PostMapping(value="/uploadmultifile")
	public String uploadMultiFile(HttpServletRequest request,Model model,@ModelAttribute("uploadform") MyUploadForm form) {
		return this.doUpload(request, model, form);
	}
	
	private String doUpload(HttpServletRequest request,Model model,MyUploadForm form) {
		String description = form.getDescription();
		
		//Thu muc goc upload file
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		System.out.println(" Thu muc goc upload "+uploadRootPath);
		File uploadRootDir = new File(uploadRootPath);
		//Tao thu muc goc upload neu chua co
		if(!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		MultipartFile[] fileDatas = form.getFileDatas();
		List<File> uploadedFile = new ArrayList<File>();
		List<String> failedfile = new ArrayList<String>();
		
		//Ten file goc tai Client
		for(MultipartFile fileData:fileDatas) {
			String name = fileData.getOriginalFilename();
			
			if(name != null && name.length()>0) {
				//Tao file tren server
				File serverFile = new File(uploadRootDir.getAbsolutePath()+File.separator+name);				
				BufferedOutputStream stream;
				try {
					stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					
					uploadedFile.add(serverFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					failedfile.add(name);
				}
			}
		}
		model.addAttribute("description",description);
		model.addAttribute("uploadedFile", uploadedFile);
		model.addAttribute("failedFile",failedfile);
		return "uploadresult";
	}
}
