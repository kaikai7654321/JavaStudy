package com.kaikai.java.spring;

public class BraveKnight implements Knight {
	private Quest quest;
	
	public BraveKnight(Quest quest) {
		this.quest = quest;
	}
	@Override
	public void embarkOnquest() {
		quest.embark();
	}

}
