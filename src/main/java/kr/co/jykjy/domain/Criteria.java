package kr.co.jykjy.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
	private int pageNum = 1;
	private int amount = 10;
	
	// checkbox
	private String type = ""; //  TCI
	private String keyword;
	
	// 검색
	
	public int getOffset(){
		return (pageNum - 1) * amount;
	}
	
	public String getLink()	{
		return UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", pageNum)
				.queryParam("amount", amount)
				.queryParam("type", type)
				.queryParam("keyword", keyword)
				.toUriString();
	}
	
	public String getPageLink()	{
		return UriComponentsBuilder.fromPath("")
				.queryParam("amount", amount)
				.queryParam("type", type)
				.queryParam("keyword", keyword)
				.toUriString();
	}
	
	public String[] getTypeArr(){
		return type.split(",");
	}
}
