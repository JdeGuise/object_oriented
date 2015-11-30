//John deGuise CPS 181 Michael Stinson 9:30 AM T/TH Spring 2015

import java.util.*;
import java.io.*;

/* 
 * read file nameswithbirthdays.txt
 * place pieces of data into a BST ordered by last name
 * Search for Sharon Jones, then Ron Clark - use a comparison counter
 * print names and birthdays in alphabetical order (last name)
 * delete Kevin Nichols
 * delete Sharon Jones
 * Output info
 * */

public class BSTree {

	TreeNode root;

	class TreeNode{  /* just creating standard TreeNode class
					with variable declarations, constructor, and toString() method */
		String firstName;
		String lastName;
		String birthday;
		
		TreeNode leftChild;
		TreeNode rightChild;
		
		TreeNode(String firstName, String lastName, String birthday){ //constructor TreeNode
			this.lastName = lastName;
			this.firstName = firstName;
			this.birthday = birthday;
		}
		
		public String toString(){ //general toString for node display
		
			return lastName + ", " + firstName + ": born " + birthday;
		
		}
		
	}

	public static void main(String[] args) throws FileNotFoundException{		
		BSTree theTree = new BSTree(); //Initialize BSTree for Homework assignment
		
		Scanner sc = new Scanner(new File("nameswithbirthdays.rtf")); //initialize new Scanner (.txt wasn't working on mac)
		
		while(sc.hasNextLine()){ //while the input file has more lines
			String fullInfo = sc.nextLine(); //the full info gets stored as a string
			
			String arr[] = fullInfo.split(" ", 3); //full info is split into three pieces separated by two spaces
			String lastName = arr[0]; //last name gets set in array[0] (I reordered by last name first)
			String firstName = arr[1]; //first name gets set in array[1] 
			String birthday = arr[2]; //birthday gets set in array[2]
			
			theTree.addNode(firstName, lastName, birthday); //all three pieces get added as a node via addNode method
			
		}
		System.out.println("Nodes added to BSTree"); /* verification message of #2 on homework being done
													additionally tells me that #1 was done	*/ 
		
		theTree.findNode("Jones"); //#3 in homework
		theTree.findNode("Clarke");
		
		System.out.println("Sorting BSTree by ascending last names"); //#4 in homework
		theTree.inOrderTraverseTree(theTree.root); //traverse the tree normally
		System.out.println("Done sorting BSTree by alphabetizing last names.");
		
		theTree.remove("Nichols"); //#5 in homework
		theTree.remove("Jones"); //#6 in homework
		
		System.out.println("Sorting BSTree by ascending last names (amended without Nichols and Jones)"); //#7 in homework
		theTree.inOrderTraverseTree(theTree.root); //traverse the tree normally
		System.out.println("Done sorting BSTree by alphabetizing last names.");
		
		
		
//		theTree.preorderTraverseTree(theTree.root); //orders as root, sort(leftChild), sort(rightChild)
//		theTree.postOrderTraverseTree(theTree.root); //orders as descendingsort(leftChild), descendingsort(rightChild), root.  Check descending sort
		
	}
	
	public void addNode(String firstName, String lastName, String birthday){
		
		TreeNode newNode = new TreeNode(firstName, lastName, birthday); //new node made from name/bday strings
		
		if(root == null){ //if there's no root yet established, the new node becomes root
			root = newNode;
		}
		
		else{
			TreeNode focusNode = root; //if there is a root established, the focused node is root
			
			TreeNode parent; //declare a parent variable
			
			while(true){ //while loop to navigate the logic in finding where to place the added node
				parent = focusNode; //parent is what we focus on
				
				int compareValue = lastName.compareTo(focusNode.lastName);
				if(compareValue < 0){ 				/*if the key in add node is less 
														than focusNode's key (which is root's, which 
														is the method parameter when root was null), 
														shift focus to the left child */
					focusNode = focusNode.leftChild;
				
					if(focusNode == null){					/*If there's still nothing,
															the parent.leftChild (which is the current focusNode)
															 = a newNode*/
						parent.leftChild = newNode;
						return;
					}
				}
				else{
					focusNode = focusNode.rightChild;	/*Else, # is greater, so the focus goes to the rightChild,
					 									and if there's nothing in it, the rightChild 
					 									becomes a new Node*/
					
					if(focusNode == null){		//if our focusNode is null, the rightChild of parent is our newNode and we're done
						parent.rightChild = newNode;
						return;
					}
				}
			}
		}
	}
	
	public TreeNode findNode(String lastName){
		TreeNode focusNode = root;
		int count = 0;
		
		try{
				
			while(focusNode.lastName != lastName){ //while our focus and what we want aren't the same
				int compareValue = lastName.compareTo(focusNode.lastName); //produces an integer (solely using this as a comparison value. - or + means it's not the same. 0 is a match)
				System.out.println("Comparison #" + count + ". compareValue = " + compareValue); //output for the console
				
				if(compareValue == 0){ //if they're the same lastName, we found it and we're done.
					System.out.println("Match found at comparison #" + count + ". Last name " + focusNode);
					break;
				}
				else if(compareValue > 0){ //if our compareValue is too big, we move to the right child
					focusNode = focusNode.rightChild;
					count++;
				}
				
				else if(compareValue < 0){ //if our compareValue is too small, we move to the left child
					focusNode = focusNode.leftChild;
					count++;
				}
				
				else{ //the list doesn't contain the query and null is returned
					System.out.println("Name not found");
					return null;
				}
			}
		}
		catch(NullPointerException n){ //reaching the end of a list unsuccessfully
			System.out.println("NullPointerException " + n);
		}
	
		return focusNode; //return what we have if we have it.
	}
	
	public boolean remove(String lastName){
		TreeNode focusNode = root;
		TreeNode parent = root;
		
		boolean isItALeftChild = true; 
		
		while(focusNode.lastName != lastName){ //while our focus isn't the right last name
			
			parent = focusNode; //the parent becomes the new focus node (likely root)
			int compareValue = lastName.compareTo(focusNode.lastName); //compare string lastName and focused name, set as int
			if(compareValue < 0){ //if the comp value is negative, it's a left child
				isItALeftChild = true;
				
				focusNode = focusNode.leftChild; //focusnode becomes left child
			}
			else{ //it's positive, so it's a right child
				isItALeftChild = false;
				focusNode = focusNode.rightChild;
			}
			if(focusNode == null){ //else we're at the end of the line and it wasn't found
				return false;
			}
		}
		
		if(focusNode.leftChild == null && focusNode.rightChild == null){ //if focusNode has no children
			if(focusNode == root){ //if we're on the root
				root = null; //the root becomes null
			} else if(isItALeftChild){ //if we're not on the root and it's a left child we got it.
				parent.leftChild = null; //remove the left child
			}
			else{	//else we got it and it's the right child
				parent.rightChild = null;
			}
		}
		
		else if(focusNode.rightChild == null){ //if there's no right child
			if(focusNode == root){ //if we're on the root
				root = focusNode.leftChild; //the root becomes the left child
			}
			else if(isItALeftChild){ //if we're on a left child
				parent.leftChild = focusNode.leftChild; //the leftChild becomes the focusNode
				
			}
			else{
				parent.rightChild = focusNode.leftChild; //the rightChild becomes the focusNode
			}	
		}
		
		else if(focusNode.leftChild == null){ //if our focus's left child doesn't exist
			if(focusNode == root){ //if our focus is root
				root = focusNode.rightChild; //the root becomes the focus' right child
			}
			else if(isItALeftChild){ //else if it's already a left child
				parent.leftChild = focusNode.rightChild; //the left child of the parent becomes the rightChild of focusNode
			}
			else{
				parent.rightChild = focusNode.leftChild; //else, vice versa
			}
		}
		
		else{ //if focusNode has children
			TreeNode replacement = getReplacementNode(focusNode); //replacement node generated based on focusNode
			
			if(focusNode == root){ //if it's root
				root = replacement; //root becomes replacement
			}
			else if(isItALeftChild){ //if it's a left child
				parent.leftChild = replacement; //leftChild becomes replacement
			}
			
			else{
				parent.rightChild = replacement; //else, rightChild becomes replacement
			}
			
			replacement.leftChild = focusNode.leftChild; //finally, left child of replacement becomes left child of focusNode
		}
		
		return true; //return true to signify being done
	}
	
	public TreeNode getReplacementNode(TreeNode replacedNode){ //one parameter, the replacedNode in question
		TreeNode replacementParent = replacedNode; //replacementParent initialized as parameter
		TreeNode replacement = replacedNode; //replacement initialized as parameter
		
		TreeNode focusNode = replacedNode.rightChild; //focusNode initialized as parameter's right child
		
		while(focusNode != null){ //while focusNode is actually something
			replacementParent = replacement; //the replacementParent becomes the replacement
			replacement = focusNode; //the replacement becomes the focusNode
			
			focusNode = focusNode.leftChild; //the focusNode becomes the leftChild of its former self
		}
		
		if(replacement != replacedNode.rightChild){ //if the replacement isn't equal to the parameter's rightChild
			replacementParent.leftChild = replacement.rightChild; //the leftChild of the replacement's parent becomes the replacement's rightChild
			
			replacement.rightChild = replacedNode.rightChild; //the replacement rightChild becomes the parameter's rightChild
		
		}
		
		return replacement; //return replacement and we're done.
	}

	public void inOrderTraverseTree(TreeNode focusNode){ //if the tree is populated, recursively sort the left, recursively sort the right
		if(focusNode != null){
			inOrderTraverseTree(focusNode.leftChild);
			
			System.out.println(focusNode);
			
			inOrderTraverseTree(focusNode.rightChild);
		}
	}
	
	public void preorderTraverseTree(TreeNode focusNode){ //orders values left of root, then orders values right of root. 
		if(focusNode != null){
			System.out.println(focusNode);
			
			preorderTraverseTree(focusNode.leftChild);
						
			preorderTraverseTree(focusNode.rightChild);
		}
	}
	
	public void postOrderTraverseTree(TreeNode focusNode){ //orders values left of root, then orders values right of root. 
		if(focusNode != null){
			
			postOrderTraverseTree(focusNode.leftChild);
						
			postOrderTraverseTree(focusNode.rightChild);
			System.out.println(focusNode);

		}
	}
	
}
