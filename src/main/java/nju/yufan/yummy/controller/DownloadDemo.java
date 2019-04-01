package nju.yufan.yummy.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class DownloadDemo {

	@RequestMapping("/download")
	public ResponseEntity<byte[]> hello(HttpServletRequest req, @RequestParam("filename")String filename) throws Exception {
		Resource resource = new ClassPathResource("/static/img/"+filename);
		System.out.println(resource.getURL());
		if(resource.exists()) {
			byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
			HttpHeaders httpHeaders=  new HttpHeaders();
			httpHeaders.setContentDispositionFormData("attachment","fen.jpg");
			return ResponseEntity.ok().headers(httpHeaders).body(bytes);
		}else
			return ResponseEntity.status(404).build();
	}
}
