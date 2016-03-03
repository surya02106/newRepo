package org.com.dao;

import java.util.List;

import org.com.model.EntityObject;


@SuppressWarnings("rawtypes")
public interface BaseDao {
	public void saveOrUpdate(EntityObject entity);
	public List setQuery(String query);
	List setCriteria(String query);
	EntityObject getEntityById(Class cls, int id);
	int getUniqueValues(String query);
	boolean saveOrupdateByQuery(String query);
}
