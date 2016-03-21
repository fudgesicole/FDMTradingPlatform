package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.IStorable;

public interface IStorage<T extends IStorable> {
	List<T> readAll();
	boolean create(T t);
	T read(int id);
	T update(T t);
	boolean delete(T t);
	boolean deleteById(int id);
}
