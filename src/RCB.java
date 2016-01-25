/* Name: John Nguyen
 * ID: 14419724
 * Class: CS 143B
 * Date: 1/6/2016
 */


import java.util.LinkedHashMap;

public class RCB {
	private String name;
	private int id;
	private int availableUnits;
	private String status;
	private LinkedHashMap<PCB, Integer> waitingList;
	
	public RCB(String name){
		this.name = name;
		if (name == "R1"){
			id = 1;
		}
		else if (name == "R2"){
			id = 2;
		}
		else if (name == "R3"){
			id = 3;
		}
		else if (name == "R4"){
			id = 4;
		}
		this.availableUnits = id;
		this.status = "free";
		waitingList = new LinkedHashMap<PCB, Integer>();
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
	
	public int getAvailableUnits(){
		return availableUnits;
	}
	
	
	public String getStatus(){
		return status;
	}
	
	public LinkedHashMap<PCB, Integer> getWL(){
		return waitingList;
	}
	
	
	public void decrAvailableUnits(int units){
		availableUnits -= units;
	}
	
	public void incAvailableUnits(int units){
		availableUnits += units;
	}
	
	public void addToWaitingList(PCB p, int units){
		waitingList.put(p, units);
	}
	
	public PCB peakWL(){
		return waitingList.keySet().iterator().next();
	}
	
	public int removeFromWL(PCB pcb){
		int units = 0;
		if (waitingList.containsKey(pcb)){
			units = waitingList.get(pcb);
			waitingList.remove(pcb);
		}
		return units;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
}
