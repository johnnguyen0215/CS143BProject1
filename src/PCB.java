import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


// Encapsulates a process

public class PCB {
	private int id;
	private String name;
	private ArrayList<RCB> resources;
	private String type;
	private ArrayList<ArrayList<PCB>> readyList;
	private int priority;
	private PCB parent;
	private ArrayList<PCB> children;
	
	public PCB(int id, String name, String type, ArrayList<ArrayList<PCB>> readyList, int priority, PCB parent){
		this.id = id;
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
	
	public ArrayList<PCB> getChildren(){
		return children;
	}
	
	public void addChild(PCB child){
		children.add(child);
	}
	
	public void addResource(RCB resource){
		resources.add(resource);
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	
}
