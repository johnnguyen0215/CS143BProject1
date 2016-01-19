import java.util.ArrayList;
import java.util.Queue;

// Infrastructure of the Process and Resource management system.

public class PRManager {
	
	private ArrayList<ArrayList<PCB>> readyList;
	private ArrayList<PCB> waitingList;

	RCB R1;
	RCB R2;
	RCB R3;
	RCB R4;

	private PCB runningProcess = null;
	
	public PRManager(){
		readyList = new ArrayList<ArrayList<PCB>>();
		for (int i = 0; i < 3; i++){
			ArrayList<PCB> priorityList = new ArrayList<PCB>();
			readyList.add(priorityList);
		}
		
		create("Init", 0);
		
		waitingList = new ArrayList<PCB>();
	}
	
	public void create(String name, int priority){
		int id = readyList.size() + waitingList.size() + 1;
		PCB process = new PCB(id, name, "ready", readyList, priority, runningProcess);
		if (runningProcess != null){
			runningProcess.addChild(process);
		}
		readyList.get(priority).add(process);
		scheduler(process);
		
	}
	
	public void destroy(String name){
		
	}
	
	public void killTree(PCB p){
		
	}
	
	public void request(int rid, int units){
		RCB rcb = getRCB(rid);
		if (rcb.getStatus() == "free"){
			rcb.setStatus("allocated");
			runningProcess.addResource(rcb);
		}
		else{
			runningProcess.setType("blocked");
			runningProcess.
		}
	}
	
	public RCB getRCB(int rid){
		RCB resource = null;
		if (rid == 1){
			resource = R1;
		}
		else if (rid == 2){
			resource = R2;
		}
		else if (rid == 3){
			resource = R3;
		}
		else if (rid == 4){
			resource = R4;
		}
		return resource;
	}
	
	public void release(){
		
	}
	
	public void timeOut(){
		
	}
	
	public void scheduler(PCB process){
		
		
		if (process.getPriority() < runningProcess.getPriority() || 
					process.getType() != "running" ||
					process == null){
				preempt(runningProcess, process);
		}
		System.out.println(runningProcess.getName());
		System.out.println("scheduled");
	}
	
	public void preempt(PCB running, PCB process){
		
	}
	

	

	
	
	
}
