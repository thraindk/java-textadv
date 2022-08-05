package de.hsma.info.pr2.textadventure.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

	// Speichert den Spielfortschritt und ggf. Zeit (nur GUI)

	private String name;
	private int currentStage = 0;
	private long playTime = 0;
	private ArrayList<String> inventory; // nur für Besitzkontrolle, Daten in jew. Stage

	public Player(String name, int currenStage, long playTime) {
		this.name = name;
		this.currentStage = currenStage;
		this.playTime = playTime;
		inventory = new ArrayList<String>();
	}

	public boolean hasItem(String s) {

		if (inventory.size() != 0) {
			for (String item : inventory) {
				if (item.equals(s)) {
					return true;
				}
			}		
		}
		return false;
	}



	public long getPlayTime() {
		return playTime;
	}

	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}

	public ArrayList<String> getInventory() {
		return inventory;	// Später schöner mit Stringbuilder?

	}

	public void putItem(String s) {
		inventory.add(s);
	}

	public void removeItem(String s) {

		if (inventory.size() != 0) {
			inventory.remove(s);
		}
	}

	public int getCurrenStagePos() {
		return currentStage;
	}

	public void setCurrenStagePos(int currentStagePos) {
		this.currentStage = currentStagePos;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;

	}


}
