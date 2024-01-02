package kr.co.jykjy.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("board")
public class BoardVo {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	
	private int replyCnt;
	private List<AttachVo> attachs = new ArrayList<AttachVo>();
//	private List<Integer> is;
//	private AttachVo attach; // attach.uuid
//	private Map<String, Object> map // map.name
}
