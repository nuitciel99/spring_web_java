package kr.co.jykjy.service;

import java.sql.SQLSyntaxErrorException;

import org.springframework.transaction.annotation.Transactional;

import kr.co.jykjy.mapper.S1Mapper;
import kr.co.jykjy.mapper.S2Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Transactional(noRollbackFor=SQLSyntaxErrorException.class)
public class SService {
	private S1Mapper s1Mapper;
	private S2Mapper s2Mapper;
	
	public void doSomething(long value){
		s1Mapper.insert(value);
		s2Mapper.insert(value);
	}
	
	// user1 doSome s1.insert 1234 s2.insert

	
	// user1 doSome s1.insert

}
