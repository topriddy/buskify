
package com.buskify.dao;

import java.util.List;

import com.buskify.entity.Admin;
import com.buskify.entity.Project;
import com.buskify.entity.Settings;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

public abstract class AbstractDao<T> extends DAOBase{
	private Class<T> clazz;
	static{
		ObjectifyService.register(Student.class);
		ObjectifyService.register(Admin.class);
		ObjectifyService.register(Supervisor.class);
		ObjectifyService.register(Project.class);
		ObjectifyService.register(Settings.class);
	}
	
	public AbstractDao(Class<T> clazz){
		this.clazz = clazz;
	}
	public void delete(Object o){
		ofy().delete(o);
	}
	
	public void deleteAll(){
		ofy().delete(findAll());
	}

    public T load(long id){
    	return ofy().find(clazz, id);
    }
    
    public void save(Object o){
    	ofy().put(o);
    }
    public void saveAll(List<T> olist){
    	ofy().put(olist);
    }

    public List<T> findAll(){
    	return ofy().query(clazz).list();
    }
    
    public List<T>findAll(int start, int count){
    	return ofy().query(clazz).list().subList(start, start + count);
    }
    
    public int countAll() {
    	return ofy().query(clazz).list().size();
    }
}
