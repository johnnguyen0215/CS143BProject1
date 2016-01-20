import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Queue;


public class RCB {
	private int id;
	private int availableUnits;
	private String status;
	private TreeMap<PCB, Integer> waitingList;
	
	public RCB(int id){
		this.id = id;
		this.availableUnits = id;
		this.status = "free";
		waitingList = new TreeMap<PCB, Integer>();
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
	
	public TreeMap<PCB, Integer> getWL(){
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
		return waitingList.firstKey();
	}
	
	public PCB popFromWL(){
		PCB pcb;
		pcb = waitingList.firstKey();
		waitingList.remove(pcb);
		return pcb;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
}
