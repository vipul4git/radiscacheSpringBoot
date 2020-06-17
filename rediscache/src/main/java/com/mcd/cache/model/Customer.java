/**
 * 
 */
package com.mcd.cache.model;

import java.io.Serializable;

/**
 * @author 
 *
 */
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;

	private String Id;

	private String name;
	
	private String age;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}


}
