package com.coding.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
// for storing image in comment not for jwt
public  interface FileService {
	 String uploadImage(String path, MultipartFile file) throws IOException;
	
	 InputStream getResource(String path, String filename) throws FileNotFoundException;

}
