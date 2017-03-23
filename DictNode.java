package ce325.hw1;




//a class that creates nodes for the dictionary tree database 
public class DictNode  implements java.io.Serializable{ 
    DictNode[] children = new DictNode[26];     //Each Node has 26 empty children one for each letter
    public char data;                       
    private DictNode parent = null;             //Node that points to parent
    boolean isWordEnd = false;                  
    
    
    ///////////////////////////////CONSTRUCTORS//////////////////////////////////////
	
    //root constructor
    public DictNode() {
        
    }
	
    //node constructor with inserted data
    public DictNode(char data) { 
        this.data = data;
    }
    ////////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////////METHODS/////////////////////////////////////////////
    
    public void setPosChild(DictNode newLeaf, DictNode parent, int n) { //this method sets the "pointers" of the new node, it points to currentNode as father and is pointed to a specific position in children array
        parent.children[n] = newLeaf;
        newLeaf.parent = parent;
          
    }
    


    



    
}
