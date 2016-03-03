package org.com.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.com.model.EntityObject;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("rawtypes")
@Transactional
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	
	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(EntityObject entity){
		logger.info("saveOrUpdate(EntityObject)");
		try{
			getSessionFactory().getCurrentSession().saveOrUpdate(entity);
		}catch(Exception e){
			System.out.println("SaveOrUpdate : "+e);
		}
	}

	@Override
	public EntityObject getEntityById(Class cls, int id){
		logger.info("getEntityById");
		return (EntityObject)getSessionFactory().getCurrentSession().get(cls, id);
	}

	/*
	public void delete(Class cls, long id) throws Exception {
		EntityObject entity = (EntityObject) getSessionFactory()
				.getCurrentSession().load(cls, id);
		getSessionFactory().getCurrentSession().delete(entity);
	}*/

	@Override
	public List setQuery(String query) {
		logger.info("Query invoked : "+query);
		return getSessionFactory().getCurrentSession().createQuery(query).list();
	}
	
	@Override
	public List setCriteria(String key) {
		Query query = getSessionFactory().getCurrentSession().createQuery("from User where firstname like :firstname");
		return query.setParameter("firstname", key + "%").list();
	}

	@Override
	public int getUniqueValues(String query) {
		return ((Long)getSessionFactory().getCurrentSession().createQuery(query).uniqueResult()).intValue();
	}
	
	@Override
	public boolean saveOrupdateByQuery(String query) {
		logger.info("saveOrUpdate(EntityObject)");
		try{
			getSessionFactory().getCurrentSession().createQuery(query).executeUpdate();
			return true;
		}catch(Exception e){
			System.out.println("saveOrupdateByQuery : "+e);
		}
		return false;
	}
}
