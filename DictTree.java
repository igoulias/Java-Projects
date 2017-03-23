package ce325.hw1;



public class DictTree implements java.io.Serializable {
    DictNode root = new DictNode();
    int size;
    
    
    ///////////////////////////////CONSTRUCTORS//////////////////////////////////////
    //
    // create a tree
    public DictTree () {
        root = null;
        size = 0;
        
    }
    
	// create a tree with just one node, the root node
    public DictTree(DictNode Node) {
        root = Node;
        size = 1;
    }
    ////////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////////METHODS/////////////////////////////////////////////
    public int size() {
        return size;
    }
    
 
    
    // add a new node to the tree
    public DictNode addNode(char data, DictNode currentNode, int n) {
        DictNode newLeaf = new DictNode(data);
        newLeaf.setPosChild(newLeaf, currentNode, n); //set parent and children position to new node
        
        
        size++;
        return newLeaf;        
        
    }
    

    
    
    
    
    
    
}
