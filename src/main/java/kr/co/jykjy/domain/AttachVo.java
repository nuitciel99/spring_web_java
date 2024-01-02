package kr.co.jykjy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // mybatis
@AllArgsConstructor // builder
public class AttachVo {
	
	private String uuid;
	private String origin;
	private String path;
	private boolean image;
	
	private Long bno;
	private Long rno;
}
