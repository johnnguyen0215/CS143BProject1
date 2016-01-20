import java.util.ArrayList;
import java.util.Queue;


public class RCB {
	private int id;
	private int availableUnits;
	private String status;
	private ArrayList<PCB> waitingList;
	
	public RCB(int id){
		this.id = id;
		this.availableUnits = id;
		this.status = "free";
		waitingList = new ArrayList<PCB>();
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
	
	
	public void decrAvailableUnits(int units){
		availableUnits -= units;
	}
	
	public void addToWaitingList(PCB p){
		waitingList.add(p);
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
}
