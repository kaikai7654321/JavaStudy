package com.kaikai.java.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KnightMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext
		= new ClassPathXmlApplicationContext("knight.xml");
		
		Knight knight = classPathXmlApplicationContext.getBean(Knight.class);
		knight.embarkOnquest();
		classPathXmlApplicationContext.close();
		
	}

}
