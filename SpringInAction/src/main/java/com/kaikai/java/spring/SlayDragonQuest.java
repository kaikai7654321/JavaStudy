package com.kaikai.java.spring;

import java.io.PrintStream;

public class SlayDragonQuest implements Quest {
	private PrintStream stream;
	SlayDragonQuest (PrintStream stream)
	{
		this.stream = stream;
	}
	@Override
	public void embark() {
		stream.println("embarking on quest to slay the dragon");
	}

}
