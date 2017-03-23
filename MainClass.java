package ce325.hw1;

import java.io.*;
import static java.lang.Character.isLowerCase;
import java.util.*;
import java.util.regex.*;


public class MainClass implements java.io.Serializable{
    
    
    
    //Traverse the tree when loading from a serialized file and return words to suggest.
    public static void traverse(DictTree tree, DictNode root, List<String> suggestions){
        DictNode currentNode = root;
        String letters;
        letters = "";
       
        //call recursiveSuggest to create a list for suggestion.
        suggestions = recursiveSuggest(root, letters, suggestions);           
    }
    
    //Create a tree structure from a serialized file.
    public static DictTree deserialize(String FromFilePath) throws FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException {
        
        DictTree tree = new DictTree() ;
        

        try {
            FileInputStream loadedTree = new FileInputStream(FromFilePath);
            ObjectInputStream in = new ObjectInputStream(loadedTree);
            
            tree = (DictTree) in.readObject();
            in.close();
            loadedTree.close();
            
        } catch(IOException ex) {
           System.out.println("Please give a valid location of a .ser file.");
           return null;
        }
        return tree;
    }
    
    //Serialize a tree structure.
    public static void serialize(DictTree tree, String FilePath) {
            
           
        try (FileOutputStream serializedDict = new FileOutputStream(FilePath);){         
            ObjectOutputStream out = new ObjectOutputStream(serializedDict);
            out.writeObject(tree);
            
            out.close();
            serializedDict.close();
         } catch(IOException ex) {
            ex.printStackTrace();
            
         }   
    }
    
    //Get a word and return, for every letter, the position in the 26letter array.
    public static int setLetterPosition(String word, int i) {
        int n;
        n = 0;
        char currentChar = word.charAt(i);
        
        if (isLowerCase(currentChar)) {
            n = (int) currentChar - (int) 'a';
        }
        else{
            n = (int) currentChar - (int) 'A';
        }
    
        return n;
    }
     
    //Adds a word to the dictionary when the file it is populated by is a text file.
    public static void addDictWordForTextFile(String word, DictNode root, DictTree Tree) {
          
        DictNode newLeaf = null;
        DictNode currentNode = root;
        int n;
        char currentChar;
        	
        for(int i = 0; i < word.length(); i++) {
            
            n = setLetterPosition(word, i);
            currentChar = word.charAt(i);
            
        		
            //each for loop that isn't for the first word, has the following check, to see if the current child already exists
            if(currentNode.children[n] == null) {
                newLeaf = Tree.addNode(currentChar, currentNode, n);    
                currentNode = newLeaf;
            }
            else {
                currentNode = currentNode.children[n];   
            }
            
            if(i==word.length() - 1) {
                currentNode.isWordEnd = true;
            }               
        }  
    }
    
    //Check if the string given is only letters.
    public  static boolean isAlpha(String  word) {
        return word.matches("[a-zA-Z]+");
    }


    
    //Read multiple files from a directory or a single file from a path.
    public static boolean readFiles(String filePath, DictNode root, DictTree tree, Set set){
        try {
            
            File dir = new File(filePath);
            
            if(dir.isDirectory()) {
                for(File file : dir.listFiles()) {
                    extractWordsFromFile(file, set, tree, root);
                }
            }
            else if(dir.isFile()) {
                extractWordsFromFile(dir, set, tree, root);
                                    
            }
            else return false;
        } catch(FileNotFoundException ex) {
            System.out.println("File not found.");
            return false;
        }
        return true;
    }
    //Extracts the words of the file in a formatted way.
    public static void extractWordsFromFile(File file, Set set, DictTree tree, DictNode root) throws FileNotFoundException {
       
        if(file.isFile()) {
            Scanner sc = new Scanner(file);
            
            // thisPunct is true when the current word ends with Punctuation
            // afterPunct is true when the current word follows a word ending with Punctuation
            boolean thisPunct = false;
            boolean afterPunct = false;
            
            //while not end of file is reached
            while(sc.hasNext()) {
                
                String word = sc.next();
                if(thisPunct) {
                    afterPunct = true;
                }
                thisPunct = false;
                
                // find patterns in words with mid punctuation
                Pattern midpunct = Pattern.compile("\\p{Alpha}\\p{Punct}\\p{Alpha}");
                Matcher midmatcher = midpunct.matcher(word);
                
                if(midmatcher.find()) {
                    continue;
                }
 
                // find words with punctuation in the end
                Pattern p = Pattern.compile("\\p{Punct}");
                Matcher m = p.matcher(word);
                
                if(m.find()) {
                    // if a word starts with punctuation
                    if(m.start() == 0) {
                        word = word.substring(1,word.length());
                    }
                    //if a word with punctuation in the end is found save the word without the punctuation
                    if(m.start() == (word.length()-1)) {
                        word = word.substring(0,m.start());
                    }
                    thisPunct = true;
                }
                
                word = word.trim();
                
                // if a word that follows a word with punctuation in the end
                // is not fully in caps or fully in small letters, print the small letter version
                if (afterPunct) {
                    if((word != word.toLowerCase()) && (word != word.toUpperCase())){
                        word = word.toLowerCase();
                    }
                    afterPunct = false;
                }
                if(!isAlpha(word)) {
                        continue;
                }
                if (set.add(word)) {
                    addDictWordForTextFile(word, root, tree);
                }
            }
            sc.close();
        }
    }
    
    
    
    //Create dictionary information in a file.
    public static File createUniqueWordText(Set<String> set) throws FileNotFoundException, UnsupportedEncodingException {
        File dictionaryInformation = new File("Dictwords.txt");
        PrintWriter UniqueWordText = new PrintWriter("DictWords.txt", "UTF-8");

        //words previously read are written through a set of strings in DictWords.txt file 
        String[] SetString = set.toArray(new String[set.size()]);
        for(int i=0; i< SetString.length; i++) {
           UniqueWordText.println(SetString[i]);
        }
        UniqueWordText.println("DictWords.txt, "+SetString.length+" words");
        UniqueWordText.close();
        return dictionaryInformation;
    }
    
    
    //Recursively traverse the tree to find nodes where words end.
    public static List<String> recursiveSuggest(DictNode currentNode, String word, List<String> suggestions) {
        DictNode nextNode;
       
        for(int j = 0; j < 26; j++) {
            
            if(currentNode.children[j] != null) {
                
                 nextNode = currentNode.children[j];
                
                if(nextNode.isWordEnd) {
                    suggestions.add(word+nextNode.data);
                }
                suggestions = recursiveSuggest(nextNode, word+nextNode.data, suggestions);
                continue;
            }
        }
        return suggestions;
    }
    
    //Suggest words from a non-serialized file.
    public static void suggest(DictNode root, DictTree Tree, String input) {
        List<String> suggestions = new ArrayList<String>();
        int n;
        String word = "";
        DictNode currentNode = root;
        char currentChar;
        
        for(int i = 0; i < input.length(); i++) {
            
            n=setLetterPosition(input, i);
            currentChar=input.charAt(i);
            
            if(currentNode.children[n] == null) {
              
                System.out.println("There are no words with that prefix");
                return;
            }
            else {
                currentNode = currentNode.children[n];
                word = word + currentChar;               
            }   
        }   
        suggestions = recursiveSuggest(currentNode, word, suggestions);
        System.out.println("SUGGESTIONS: "+suggestions);
    }

    
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException {
        DictNode root = new DictNode();
        DictTree tree = new DictTree(root);
        Set<String> set = new HashSet<String>();
        String input = "";
        Scanner scanner = new Scanner(System.in);
        File dictionaryInformation = null;
        List <String> sugs = new ArrayList<String>();
        boolean readFilesCheck;
        
        while(true) {
           
            System.out.println("----------- MENU -----------"
                + "\n\t1. Load dictionary from binary file (type: load fromFilepath)"
                + "\n\t2. Save dictionary to binary file (type: save toFilepath)"
                + "\n\t3. Populate dictionary from txt file (type: read fromTxtFilePath)"
                + "\n\t4. Suggest word (type: suggest wordPhrase)"
                + "\n\t5. Print dictionary information (type: print)"
                + "\n\t6. Quit (type: quit)");
            
            input = scanner.nextLine();
            String[] inputArray = input.split("\\s+"); 

            try { 
                switch(inputArray[0]) {

                    case ("load"): 
                        
                        tree = deserialize(inputArray[1]);
                        if(tree== null) {
                            break;
                        }
                        root = tree.root;
                        traverse(tree, root, sugs);
                        System.out.println("Dictionary populated successfully.");
                        break;

                    case ("save"):
                        
                        if (tree.size == 1) {     
                            System.out.println("You need to populate a dictionary first.");
                            break;
                        }
                        serialize(tree, inputArray[1]);
                        System.out.println("Serialization successfull.");
                        break;

                    case ("read"):
                        
                        readFiles(inputArray[1], root, tree, set);
                        readFilesCheck = readFiles(inputArray[1], root, tree, set);
                        if(!readFilesCheck) {
                            System.out.println("File not found.");
                            break;
                        }
                        System.out.println("Dictionary populated successfully.");
                        break;

                    case ("suggest"):
                        
                        if(tree.size()==1) {
                            System.out.println("You need to populate a dictionary first.");
                            break;
                        }

                        suggest(root, tree, inputArray[1]);
                        break;

                    case ("print"): 
                        if(set.size()==0) {
                            System.out.println("You need to give a file from which a Dictionary can be created.");
                            break;
                        }
                        
                        createUniqueWordText(set);
                        System.out.println("A text file with the Dictionary words was created.");
                        break;

                    case ("quit"): 
                        if(inputArray.length != 1) {
                            System.out.println("Please choose a valid option.");
                            break;
                        }

                        return;
                
                    default: 
                        System.out.println("Please choose a valid option.");    
                }
                
            }catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Please choose a valid option.");
            }
        }
    }
}
