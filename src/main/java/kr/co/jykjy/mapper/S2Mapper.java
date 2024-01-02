package kr.co.jykjy.mapper;

import org.apache.ibatis.annotations.Insert;

public interface S2Mapper {
	@Insert("insert into tbl_s2 values (#{value}")
	int insert(long value);
}

