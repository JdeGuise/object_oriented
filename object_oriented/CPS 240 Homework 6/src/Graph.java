import java.util.*;

public class Graph {
	
	public Node rootNode;
	public List<Node> nodes = new ArrayList<Node>();
	Map<Integer, List<Integer>> adjList = new TreeMap<Integer, List<Integer>>(); 
	
	public static void main(String[] args){
		
		//Lets create nodes as given as an example in the article
		Node zero = new Node('0');
		Node one = new Node('1');
		Node two = new Node('2');
		Node three = new Node('3');
		Node four = new Node('4');
		Node five = new Node('5');
		Node six = new Node('6');
		Node seven = new Node('7');
		Node eight = new Node('8');


		//Create the graph, add nodes, create edges between nodes
		
		
		Graph g = new Graph();
		g.addNode(zero);
		g.addNode(one);
		g.addNode(two);
		g.addNode(three);
		g.addNode(four);
		g.addNode(five);
		g.addNode(six);
		g.addNode(seven);
		g.addNode(eight);
		
		g.setRootNode(zero);
		
		g.connectNode(zero, one);		
		g.connectNode(zero, three);
		g.connectNode(zero, eight);
		g.connectNode(three, two);
		g.connectNode(three, four);
		g.connectNode(five, two);
		g.connectNode(five, six);
		g.connectNode(seven, one);
		g.connectNode(seven, two);
		g.connectNode(eight,  four); 


		for (Node n : g.nodes){
			g.setRootNode(n);
			
			System.out.println("From node ");
			g.printNode(n);
			
			System.out.println("\nDFS (iterative): ");
			g.dfs();
			g.reset();
			
			System.out.println("\nDFS (recursive): ");
			g.dfs(g.getRootNode());
			g.reset();
			
			System.out.println("\nBFS (iterative) ");
			g.bfs();
			g.reset();
			System.out.println("\n");
			
		}
	
	}

	public void setRootNode(Node n)
	{
		rootNode = n;
	}

	public Node getRootNode()
	{
		return rootNode;
	}

	public void addNode(Node n)
	{
		nodes.add(n);
	}

	//This method will be called to make connect two nodes
	public void connectNode(Node src, Node dst){
		int srcIndex = nodes.indexOf(src);
		int dstIndex = nodes.indexOf(dst);
		
		if(adjList.containsKey(srcIndex)){
			List<Integer> nodes = adjList.get(srcIndex);
			nodes.add(dstIndex);
			Collections.sort(nodes);
		}
		else{
			List<Integer> nodes = new ArrayList<Integer>();
			nodes.add(dstIndex);
			adjList.put(srcIndex, nodes);
		}
		if(adjList.containsKey(dstIndex)){
			List<Integer> nodes = adjList.get(dstIndex);
			nodes.add(srcIndex);
			Collections.sort(nodes);
		}
		else{
			List<Integer> nodes = new ArrayList<Integer>();
			nodes.add(srcIndex);
			adjList.put(dstIndex,  nodes);
		}
		
	}

	private Node getUnvisitedChildNode(Node n){
		
		int index = nodes.indexOf(n); 
		
		List<Integer> neighbors = adjList.get(index);
		
		for (Integer i: neighbors){     
			if(!nodes.get(i).visited){
				return nodes.get(i);
			}
		}																		
		
		return null;
	}

	// BFS traversal: iterative version
	public void bfs(){
		
		//BFS uses a Queue data structure
		Queue<Node> q = new LinkedList<Node>();
		q.add(rootNode);
		printNode(rootNode);
		
		rootNode.visited = true;
		while (!q.isEmpty()){
			
			Node n = q.remove();
			Node child = null;
			
			while ((child = getUnvisitedChildNode(n)) != null){ //if = null, doesn't execute
				child.visited = true;
				
				printNode(child);
					q.add(child);
			}		
		}
	}
	
	//DFS traversal: recursive version
	public void dfs(Node n){
		printNode(n);
		n.visited = true;
		for(Node node : getNeighbors(n)){
			if (!node.visited){
				dfs(node);
			}
		}
	}

	//DFS traversal:  iterative version
	public void dfs(){
		
		//DFS uses a Stack data structure
		Stack<Node> s = new Stack<Node>();
		s.push(rootNode);
		rootNode.visited = true;
		
		printNode(rootNode);
		
		while (!s.isEmpty()){
			
			Node n = s.peek();
			Node child = getUnvisitedChildNode(n);
			
			if (child != null){
				
				child.visited = true;
				printNode(child);
				s.push(child);
			
			}
			
			else
				s.pop();
		}
	}
	
	// get all neighboring nodes of node n.
	public List<Node> getNeighbors(Node n)
	{
		int srcIndex = nodes.indexOf(n); //srcIndex = our rootNode source
		List<Node> neighbors = new ArrayList<Node>();
		for (int i : adjList.get(srcIndex)){
			neighbors.add(nodes.get(i));
		
		}
		
		return neighbors;
	}
	
	//Utility methods for clearing visited property of node
	private void reset(){
		
		for (Node n : nodes)
			n.visited = false;
	
	}
	
	//Utility methods for printing the node's label
	private void printNode(Node n){
		
		System.out.print(n.label + " ");
	
	}
		
	static class Node{			
		public char label;
		public int edges;
		public boolean visited = false;
			
		public Node(char label){ //Node constructor with node number, char label, and # of edges
			this.label = label;
		}
	}

}