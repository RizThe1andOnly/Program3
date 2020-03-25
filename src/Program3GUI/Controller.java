package Program3GUI;

import TuitionManager.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;

// ADD MEANS BY WHIHCH TO HANDLE ANY AND ALL EXCEPTIONS !!

/**
 * Controller class used to define and control the functionalities of the GUI. Will coordinate with FXML file.
 * @author Rizwan Chowdhury
 * @author Tin Fung
 */
public class Controller {
    private StudentList allStudents= new StudentList();

    //type-specific variables
    String typeOfStudent;
    int instateStudentFunds;
    boolean exchangeStudent;
    boolean tristateareaStudent;
    private final int INTERNATIONAL_STUDENT_CREDIT_REQUIREMENT = 9;
    private final int FULL_TIME_THRESHOLD = 12;

    //following are instances of the fxml elements, using their fxml ids, that will be used
    @FXML
    private HBox triStateSelectionArea;

    @FXML
    private HBox exchangeSelectionArea;

    @FXML
    private HBox fundsArea;

    @FXML
    private CheckBox checkFunds;

    @FXML
    private CheckBox IsTriState;

    @FXML
    private CheckBox IsExchange;



    @FXML
    private TextArea outputArea;

    @FXML
    private TextField funding;

    @FXML
    private TextField fnameInput;

    @FXML
    private TextField lnameInput;

    @FXML
    private TextField creditsInput;
    @FXML
    private RadioButton instateRadioButton;
    @FXML
    private RadioButton outstateRadioButton;
    @FXML
    private RadioButton internationalRadioButton;

    @FXML
    private ToggleGroup studentType;



    /**
     * Function to clear the gui inputs after each time "Add"/"Remove"/"Print" button is pressed.
     * @author Rizwan Chowdhury
     * @author Tin Fung
     */
    private void clearGuiInputs(){
        fnameInput.clear();
        lnameInput.clear();
        creditsInput.clear();
        funding.clear();
        instateRadioButton.setSelected(false);
        outstateRadioButton.setSelected(false);
        internationalRadioButton.setSelected(false);
        checkFunds.setSelected(false);
        IsTriState.setSelected(false);
        IsExchange.setSelected(false);
        funding.setDisable(true);

        typeOfStudent = "";
        instateStudentFunds = 0;
        exchangeStudent = false;
        tristateareaStudent = false;
    }


    /**
     * Disables/enables the funding textbox based on whether or not funds checkbox is checked or not
     * @param event
     * @author Tin Fung
     */
    @FXML
    public void fundingdisabler(ActionEvent event){
        String eventSourceId = ((CheckBox)event.getSource()).getId();
        switch (eventSourceId) {
            case "checkFunds":
                funding.setDisable(!checkFunds.isSelected());
                break;
        }
    }



    /**
     * Controller Method to disable/enable different sections of student specific information input based on which
     * option (type of student) is selected. Only section corresponding to selected option (Instate, Outstate, International)
     * will be enabled, others will be disabled. It will also clear other types everytime the user selects a different option for type of student clear any currently entered type
     * @param event Toggling of Instate, Outstate, or International to trigger this function.
     * @author Rizwan Chowdhury
     */
    @FXML
    public void typeSelectionDisabler(ActionEvent event){
        String eventSourceId = ((RadioButton)event.getSource()).getId(); // get which button was toggled

        //based on toggled button carry out appropriate action
        switch (eventSourceId){
            case "instateRadioButton":
                triStateSelectionArea.setDisable(true);
                exchangeSelectionArea.setDisable(true);
                fundsArea.setDisable(false);
                IsTriState.setSelected(false);
                IsExchange.setSelected(false);
                break;

            case "outstateRadioButton":
                fundsArea.setDisable(true);
                exchangeSelectionArea.setDisable(true);
                triStateSelectionArea.setDisable(false);
                checkFunds.setSelected(false);
                IsExchange.setSelected(false);
                funding.clear();
                funding.setDisable(true);
                break;

            case "internationalRadioButton":
                fundsArea.setDisable(true);
                triStateSelectionArea.setDisable(true);
                exchangeSelectionArea.setDisable(false);
                funding.clear();
                checkFunds.setSelected(false);
                IsTriState.setSelected(false);
                funding.setDisable(true);
                break;
        }

        setStudentType(event);
    }


    /**
     * Sets type specific (type-specific = type of student) information for type of student selected based on certain
     * events. Info such as funds or either exchange or not are set. Events that trigger this are filling out funds or
     * making selection of exchange or not and etc.
     *
     * @param event Actions performed to specify type-specific information
     * @author Rizwan Chowdhury
     * @author Tin Fung
     */
    public void setStudentType(ActionEvent event){
        RadioButton selectedButton  = (RadioButton) event.getSource();
        switch (selectedButton.getId()){
            case "instateRadioButton":
                typeOfStudent = "Instate";
                break;

            case "outstateRadioButton":
                typeOfStudent = "Outstate";
                break;

            case "internationalRadioButton":
                typeOfStudent = "International";
                break;
        }

    }


    /**
     * The action(s) that will be performed when the add button is pressed, mainly to add student to TuitionManager list.
     * @param event Button press of Add button in the GUI
     * @author Rizwan Chowhdury
     */
    @FXML
    public void actionWhenAddButtonPressed(ActionEvent event){
        String fname;
        String lname;
        int credits;

        //check if name inputs are correct:
        String fnameInputString = fnameInput.getText();
        String lnameInputString = lnameInput.getText();
        if( (checkNameTextFieldContent(fnameInputString)) && (checkNameTextFieldContent(lnameInputString)) ){
            fname = fnameInputString;
            lname = lnameInputString;
        }
        else{
            outputArea.appendText("First/Last name not inputted at all/in correct format.\n");
            return;
        }

        //check if credits input is correct
        try {
            credits = parseCreditsValue(creditsInput.getText());
        } catch(NumberFormatException e){
            outputArea.appendText("Credits must be entered as numbers only (integers).\n");
            return;
        }

        //check if proper value for credits input:
        if(!isGreaterThanZero(credits)){
            outputArea.appendText("Must take greater than zero credits.\n");
            return;
        }
        addSpecificTypeStudent(fname,lname,credits);
        clearGuiInputs();
    }


    /**
     * Will call the specific function based on the type of student being added.
     * @param fname Student's first name
     * @param lname Student's last name
     * @param credits Amount of credits student is taking
     * @author Tin Fung
     */
    private void addSpecificTypeStudent(String fname, String lname, int credits){
        if(instateRadioButton.isSelected()){
            addInstateStudent(fname,lname,credits);
        }else if(outstateRadioButton.isSelected()){
            addOutstateStudent(fname,lname,credits);
        }else if(internationalRadioButton.isSelected()){
            addInternationalStudent(fname,lname,credits);
        }else{
            outputArea.appendText("please select one of the buttons Instate, Outstate, International \n");
        }
    }


    /**
     * Will add instate student to the list
     * @param fname student's first name
     * @param lname student's last name
     * @param credits credits the student is taking
     * @author Tin Fung
     * @author Rizwan Chowdhury
     */
    private void addInstateStudent(String fname, String lname, int credits) {
        //needs to check for zero or lower value !!

        String Funds = funding.getText();
        if (checkFunds.isSelected()) {
            try {
                instateStudentFunds = Integer.parseInt(Funds);
            } catch (NumberFormatException e) {
                outputArea.appendText("Funding needs to be a number.\n");
                return;
            }
        }

        if(checkFunds.isSelected()){
            if(credits < FULL_TIME_THRESHOLD){
                outputArea.appendText("Students taking fewer than 12 credits do not qualify for funding, please re-enter data and do not check the Funding button.\n");
                return;
            }
        }

        if(instateStudentFunds < 0){
            outputArea.appendText("Student cannot have funds below zero.\n");
            return;
        }

        Student newInstateStudent = new Instate(fname,lname,credits,instateStudentFunds);

        if(!(allStudents.contains(newInstateStudent))){
            allStudents.add(newInstateStudent);
            outputArea.appendText("Added new student: "+fname+" "+lname+" \n");
        }
        else{
            outputArea.appendText("Student already in students list. Could not add Student\n");
        }
     }

    /**
     * Will add outstate student to the list
     * @param fname student's first name
     * @param lname student's last name
     * @param credits credits the student is taking
     * @author Tin Fung
     */
    private void addOutstateStudent(String fname, String lname, int credits){
        tristateareaStudent=IsTriState.isSelected();
        Student newOutstateStudent = new Outstate(fname,lname,credits,tristateareaStudent);
        if(!(allStudents.contains(newOutstateStudent))){
            allStudents.add(newOutstateStudent);
            outputArea.appendText("Added new student: " + fname + " " + lname+"\n");
        }
        else{
            outputArea.appendText("Student already in students list. Could not add student \n");
        }

    }


    /**
     * Will add international student to the list
     * @param fname student's first name
     * @param lname student's last name
     * @param credits credits the student is taking
     * @author Tin Fung
     */
    private void addInternationalStudent(String fname, String lname, int credits){
        if(credits<INTERNATIONAL_STUDENT_CREDIT_REQUIREMENT){
            outputArea.appendText("Not enough credits for International Student, needs to have 9 or more. Could not add Student \n");
            return;
        }
        exchangeStudent=IsExchange.isSelected();
        Student newInternationalStudent = new International(fname,lname,credits,exchangeStudent);
        if(!(allStudents.contains(newInternationalStudent))){
            allStudents.add(newInternationalStudent);
            outputArea.appendText("Added new student: " + fname + " " + lname+" \n");
        }
        else{
            outputArea.appendText("Student already in students list. \n");
        }
    }

    /**
     * Remove student from running list when the Remove button is pressed.
     * @param event Pressing of the Remove button
     * @author Rizwan Chowdhury
     * @author Tin Fung
     */
    @FXML
    public void actionWhenRemoveButtonPressed(ActionEvent event){
        String fname;
        String lname;

        //check if name inputs are correct:
        String fnameInputString = fnameInput.getText();
        String lnameInputString = lnameInput.getText();
        if( (checkNameTextFieldContent(fnameInputString)) && (checkNameTextFieldContent(lnameInputString)) ){
            fname = fnameInputString;
            lname = lnameInputString;
        }
        else{
            outputArea.appendText("First/Last name not inputted at all/in correct format.\n");
            return;
        }


        if(allStudents.isEmpty()){
            outputArea.appendText("Student list is empty.\n");
        }
        Student studentToBeRemoved = new Instate(fname,lname,0,0);

        boolean successfulRemoval = allStudents.remove(studentToBeRemoved);
        if(successfulRemoval == false){
            outputArea.appendText("Failed to remove Student \n");
        }
        else{
            outputArea.appendText("Removed student: " + fname + " " + lname+"\n");
        }

        clearGuiInputs();
    }


    /**
     * Checks to see if names entered in text fields are there and are in proper format.
     * @param content The content of the text field being checked
     * @return true if checks succeed, false otherwise
     *
     * @author Rizwan Chowdhury
     */
    private boolean checkNameTextFieldContent(String content){
        // if the field is empty then check fails
        if(content.equals("")){
            return false;
        }

        //check to see how many tokens there are; if more than 1 than too many
        String[] nameTokens = content.split(" ");
        if(nameTokens.length > 1){
            return false;
        }

        //now the content has passed the tests:
        return true;
    }


    /**
     * Will parse the value inputted for credits to see how many credits a student is taking. Take input from the
     * credits input textfield.
     *
     * @param creditString string that holds the number of credits inputted
     * @return int value of the credits being taken
     * @throws NumberFormatException if input is not an integer or contains non-numeric characters then this will be thrown
     *
     * @author Rizwan Chowdhury
     */
    private int parseCreditsValue(String creditString) throws NumberFormatException {
        int credits = Integer.parseInt(creditString);
        return credits;
    }


    /**
     * Will check if a number inputted is greater than zero or not
     * @param numericValue number being checked
     * @return true if is greater than zero, false otherwise.
     * @author Rizwan Chowdhury
     */
    private boolean isGreaterThanZero(int numericValue){
        return (numericValue>0) ? true:false;
    }


    /**
     * Prints all the student to the output textbox area when "Print" button is pressed.
     * @param event Print button being pressed
     * @author Rizwan Chowdhury
     * @author Tin Fung
     */
    @FXML
    public void actionWhenPrintButtonPressed(ActionEvent event){
        if(allStudents.isEmpty()){
            outputArea.appendText("--Empty List--\n");
            return;
        }

        outputArea.appendText(  "\n"+allStudents.toString());
        outputArea.appendText("--End of List-- \n");

        clearGuiInputs();
    }

}
