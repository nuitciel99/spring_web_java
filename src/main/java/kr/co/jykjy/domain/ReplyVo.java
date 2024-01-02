package kr.co.jykjy.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("reply")
public class ReplyVo {
	private Long rno;
	private String reply;
	private Date replyDate;
	private Date updateDate;
	
	private String replyer;
	private Long bno;
}
