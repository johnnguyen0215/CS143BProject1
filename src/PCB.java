import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


// Encapsulates a process

public class PCB {
	private String name;
	private ArrayList<RCB> resources;
	private String type;
	private ArrayList<ArrayList<PCB>> readyList;
	private int priority;
	private PCB parent;
	private ArrayList<PCB> children;
	
	public PCB(String name, String type, ArrayList<ArrayList<PCB>> readyList, int priority, PCB parent){
		this.name = name;
		this.type = type;
		this.readyList = readyList;
		this.priority = priority;
		this.parent = parent;
		resources = new ArrayList<RCB>();
		children = new ArrayList<PCB>();
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public PCB getParent(){
		return parent;
	}
	
	public ArrayList<RCB> getResources(){
		return resources;
	}
	
	public ArrayList<PCB> getChildren(){
		return children;
	}
	
	public void addChild(PCB child){
		children.add(child);
	}
	
	public void addResource(RCB resource){
		resources.add(resource);
	}
	
	public void removeResource(RCB resource){
		resources.remove(resource);
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void scheduler(){
		PCB highestPriority = findHighestPriority();

		if (this.priority < highestPriority.getPriority() || 
				this.type != "running" ||
				this != null){
			preempt(highestPriority);
		}
	}
	
	public PCB findHighestPriority(){
		PCB highestPriority = null;
		for (int i = 0; i < readyList.size(); i++){
			if (readyList.get(i).size() > 0){
				highestPriority = readyList.get(i).get(0);
			}
		}
		return highestPriority;
	}
	
	public void preempt(PCB highestPriority){
		highestPriority.setType("running");
		System.out.println("*Process " + highestPriority.getName() + 
				" is running");
	}
	
}
