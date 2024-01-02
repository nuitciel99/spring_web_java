package kr.co.jykjy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SampleVo {
	private Integer mno;
	private String firstName;
	private String lastName;
}
