package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.IStorable;

/**
 * Methods for all Data Access Objects
 * @param <T> Any class marked with the IStorable marker interface.
 */
public interface IStorage<T extends IStorable> {
	/**
	 * Find all T's in storage
	 * @return A list of all T's from storage, or null if storage could not be read. 
	 */
	List<T> readAll();
	
	/**
	 * Create a new T in storage.
	 * @param t The storable to be added to storage.
	 * @return true on success, false on failure.
	 */
	boolean create(T t);
	
	/**
	 * Find a T in storage based on its unique integer identifier.
	 * @param id the id of the object in storage.
	 * @return The T from storage with the provided id.
	 */
	T read(int id);
	
	/**
	 * Update an existing T in storage
	 * @param t the updated T object that already exists in storage.
	 * @return The updated persistent object on success, null on failure.
	 */
	T update(T t);
	
	/**
	 * Delete an existing T from storage
	 * @param t The persistent target object to be deleted from storage
	 * @return true on success, false on failure.
	 */
	boolean delete(T t);
	
	/**
	 * Delete an existing T from storage by its unique integer identifier
	 * @param id the unique integer identifier of the target object in storage
	 * @return true on success, false on failure.
	 */
	boolean deleteById(int id);
}
