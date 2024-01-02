package kr.co.jykjy.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.jykjy.domain.AttachDTO;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;

@Controller
@Log4j
public class UploadController {
	
	public static final String UPLOAD_PATH = "c:/upload";
	
	@GetMapping("uploadForm")
	public void uploadForm(){
		log.info("uploadForm"); // health check
	}
	
	@PostMapping("uploadForm")
	public void uploadForm(MultipartFile[] files, String common) throws Exception{
		for(MultipartFile mf : files){
			log.info(mf.getOriginalFilename());
			log.info(mf.getSize());
			
			// 파일 저장
			mf.transferTo(new File(UPLOAD_PATH, mf.getOriginalFilename()));
		}
		log.info(files); 
		log.info(common);
	}
	
	@GetMapping("uploadAjax")
	public void uploadAjax(){
		log.info("uploadAjax"); // health check
	}
	
	@PostMapping("uploadAjax") @ResponseBody
	public List<AttachDTO> uploadAjax(List<MultipartFile> files) throws Exception{
		List<AttachDTO> list = new ArrayList<>();
		
		File uploadPath = new File(UPLOAD_PATH, getFolder());
		if(!uploadPath.exists()){
			uploadPath.mkdirs();
		}
		
		for(MultipartFile mf : files){
			
			AttachDTO dto = new AttachDTO();

			dto.setOrigin(mf.getOriginalFilename());
			dto.setUuid(UUID.randomUUID().toString());
			dto.setPath(getFolder());
			
			String fileName = dto.getUuid() + "_" + dto.getOrigin();
			
			
			File file = new File(uploadPath, fileName);
			mf.transferTo(file);
			
			boolean image = checkImageType(file);
			
			if(image){
				File thumbFile = new File(uploadPath, "s_" + fileName);
				
				try(FileOutputStream fos = new FileOutputStream(thumbFile)) {
					Thumbnailator.createThumbnail(mf.getInputStream(), fos, 200, 200);
					
				} catch (UnsupportedFormatException ignore) {  // image/svg+xml
					image = false;
					thumbFile.delete();
				}
			}
			dto.setImage(image);
			list.add(dto);
			// 원본 파일명, 이미지 여부, uuid, path (getFolder)
			
		}
		log.info(files); 
		
		return list;
	}
	
	@GetMapping("display") @ResponseBody
	public ResponseEntity<byte[]> getFile(AttachDTO dto) throws IOException{
		
		log.info(dto);
		
		File file = dto.toFile();
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", Files.probeContentType(file.toPath()));
		
		
		return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
	}
	
	@PostMapping("deleteFile") @ResponseBody
	public ResponseEntity<String> deleteFile(AttachDTO dto) throws IOException{
		
		log.info(dto);
		log.info(dto.toFile());
		
		dto.toFile().delete();
		log.info(dto.toFile(dto.isImage()));
		dto.toFile(dto.isImage()).delete();
		
		return new ResponseEntity<>("delete", HttpStatus.OK);
	}
	
	@GetMapping("download") @ResponseBody
	public ResponseEntity<byte[]> getDownload(AttachDTO dto, @RequestHeader("user-agent") String userAgent) throws IOException{
		log.info(dto);
		log.info(userAgent);
		
		File file = dto.toFile();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
		headers.add("content-disposition", "attachment; filename=" + new String(dto.getOrigin().getBytes(), "iso-8859-1"));
		
		return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
	}
	
	
	private String getFolder(){
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}
	
	private boolean checkImageType(File file){
		try {
			String contentType = Files.probeContentType(file.toPath());
			log.info(contentType);
			return contentType.startsWith("image");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			return false;
		}
		return false;
	}
}
