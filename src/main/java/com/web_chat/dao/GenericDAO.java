package com.web_chat.dao;

import java.util.List;

import com.web_chat.mapper.RowMapper;

public interface GenericDAO<T> {
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object...parameters);
	public int save(String sql, Object...parameters);
	public void update(String sql, Object...parameters);
}
