package clueGame;

public class Solution {
	public String person;
	public String weapon;
	public String room;
	
	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Solution)) {
			return false;	
		}
		if (obj == this) {
			return true;
		}
		return (this.person.equals(((Solution) obj).person) 
				&& this.weapon.equals(((Solution) obj).weapon)
				&& this.room.equals(((Solution) obj).room));
	}
}
