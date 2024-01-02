package kr.co.jykjy.mapper;

import org.apache.ibatis.annotations.Insert;

public interface S1Mapper {
	@Insert("insert into tbl_s1 values (#{value}")
	int insert(long value);
}

