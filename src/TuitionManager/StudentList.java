package TuitionManager;

/**
 Container class to hold students registered for school, holds Student and its subclasses
 Uses an array to hold the objects.

 @author Rizwan Chowdhury
 @author Tin Fung
 */
public class StudentList {
    private Student[] studentList;
    private int numberOfStudents;
    private final int GROW_SIZE = 4;
    /**
     Constructor for the StudentList class object. It will instantiate object by
     instantiating the array holding students and the number of students.

     @author Rizwan Chowdhury
     */
    public StudentList(){
        studentList = new Student[0];
        numberOfStudents = 0;
    }


	/**
	 Locates the index of the Student object in the list
	 @param studentToBeFound Student object for which the index is to be located
	 @return int, index of the Student object in the list; -1 if object not in list

	 @author Tin Fung
	 */
	private int find(Student studentToBeFound) {
    	for(int i=0;i<numberOfStudents;i++) {
    		if(studentToBeFound.compareTo(studentList[i]) == 0) {
    			return i;
    		}
    	}
    	return -1;
    }


	/**
	 Checks if the Student object is in the list.
	 @param studentToBeFound Student object being looked for in this method
	 @return true if the object is in the list, false otherwise

	 @author Rizwan Chowdhury
	 */
	public boolean contains(Student studentToBeFound){
    	for(int i=0;i<this.numberOfStudents;i++){
    		if(this.studentList[i].compareTo(studentToBeFound) == 0){
    			return true;
			}
		}
    	return false;
	}


	/**
	 Checks whether the list is empty.
	 @return true if list is empty, false if it is not
	 @author Rizwan Chowhdury
	 */
	public boolean isEmpty(){
		if(this.numberOfStudents==0){
			return true;
		}
		return false;
	}


	/**
	 Grows the array when its capacity is reached.

	 @author Tin Fung
	 */
 	private void grow() {
	 	Student [] temp=new Student[this.numberOfStudents+GROW_SIZE];

	 	for(int i=0;i<this.numberOfStudents;i++) {
	   		temp[i]=this.studentList[i];
	   	}

	 	this.studentList=temp;
 	}
    

    /**
     Adds Student object provided through argument into this container.
     @param s Student object to be added
     @author Tin Fung
     */
    public void add(Student s){
    	if(find(s)==-1) {
    		if(this.numberOfStudents==this.studentList.length) {
    			this.grow();
    		}
    		this.studentList[this.numberOfStudents]=s;
    		this.numberOfStudents++;
    	}
    }


    /**
     Removes the Student given through the argument from the container
     @param s Student object to be removed
     @return true if student successfully removed, false otherwise
     @author Rizwan Chowdhury
     @author Tin Fung
     */
    public boolean remove(Student s){
    	int find=find(s);
    
    	if(find!=-1) {
    		this.studentList[find]=this.studentList[this.numberOfStudents];
    		this.studentList[this.numberOfStudents]=null;
    		this.numberOfStudents--;
    		return true;
    	}

    	return false;
    }
	/**
	 added to string to correctly return the values
	 @author Tin Fung
	 @return String representation of the studentlist object

	 */
	public String toString(){
		String students="";
		for(int i=0;i<this.numberOfStudents;i++) {
			students+=this.studentList[i].toString()+"\n"+"Tuition Due: " + this.studentList[i].tuitionDue()+"\n";

		}
	return students;
	}


    /**
     Prints out the contents of this container; all of the Student objects and their
     properties.
     @author Rizwan Chowdhury
     @author Tin Fung
     */
    public void print(){

    	for(int i=0;i<this.numberOfStudents;i++) {
    		System.out.println(this.studentList[i].toString());
    		System.out.println("Tuition Due: " + this.studentList[i].tuitionDue());
    		System.out.println();
    	}
    }
}
