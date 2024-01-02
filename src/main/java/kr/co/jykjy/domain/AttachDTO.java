package kr.co.jykjy.domain;

import java.io.File;

import org.springframework.web.util.UriComponentsBuilder;

import kr.co.jykjy.controller.UploadController;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachDTO {
	private String uuid;
	private String origin;
	private String path;
	private boolean image;
	
	// 원본 이미지 경로
	public String getUrl(){
		return UriComponentsBuilder.fromPath("")
			.queryParam("uuid", uuid)
			.queryParam("origin", origin)
			.queryParam("path", path)
			.queryParam("image", image)
			.toUriString();
	}
	
	// 섬네일 이미지 경로
	public String getThumb(){
		
		if(!image) return "";
		
		return UriComponentsBuilder.fromPath("")
				.queryParam("uuid", "s_" + uuid)
				.queryParam("origin", origin)
				.queryParam("path", path)
				.toUriString();
		
	}
	
	public File toFile(){
		
		
		return toFile(false);
	}
	
	public File toFile(boolean thumb){
		
		File file = new File(UploadController.UPLOAD_PATH, path);
		file = new File(file, (thumb ? "s_" : "") + uuid + "_" + origin);
		
		return file;
	}
	
	// to VO
	public AttachVo toVo(){
		return AttachVo.builder().uuid(uuid).path(path).origin(origin).image(image).build();
	}
	
	// from VO to DTO
	public AttachDTO(AttachVo vo){
		uuid = vo.getUuid();
		path = vo.getPath();
		origin = vo.getOrigin();
		image = vo.isImage();
	}
}
