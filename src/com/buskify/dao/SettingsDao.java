package com.buskify.dao;

import com.buskify.entity.Settings;

public class SettingsDao extends AbstractDao<Settings> {

	public SettingsDao() {
		super(Settings.class);
	}

	public Settings findByName(String name) {
		if (name == null || name.equals("")) {
			return null;
		}
		return ofy().query(Settings.class).filter("name", name).get();
	}
}
