package com.buskify.dao;

import java.util.List;

import com.buskify.entity.Admin;
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
	}
	
	public AbstractDao(Class<T> clazz){
		this.clazz = clazz;
	}
	public void delete(Object o){
		ofy().delete(o);
	}

    public T load(long id){
    	return ofy().find(clazz, id);
    }
    
    public void save(Object o){
    	ofy().put(o);
    }

    public List<T> findAll(){
    	return ofy().query(clazz).list();
    }
    
    public int countAll() {
    	return ofy().query(clazz).list().size();
    }
}
