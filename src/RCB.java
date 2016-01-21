import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashMap;
import java.util.Queue;


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
	
	public PCB popFromWL(){
		PCB pcb;
		pcb = peakWL();
		waitingList.remove(pcb);
		return pcb;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
}
