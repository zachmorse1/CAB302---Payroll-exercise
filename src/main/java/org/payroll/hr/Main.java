package org.payroll.hr;

import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final int ACCEPTED_COMPANY_NAME_LEN=3;
    private static final String COMPANY_NAME = "Enter the name of the Company - ";
    private static final String PRE_NARR =  "Please enter the ";
    private static final String COMPANY_ERROR_NARRATION_INPUT = "\nInvalid Company name - must only contain letters, numbers or spaces. Minimum length must be greater than 3 characters\n";
    private static final String EMPLOYEE_NAME_ERROR_NARRATION = "\nInvalid employee name";

    private static boolean COMPANY_NAME_CREATED = false;
    private static boolean COMPANY_EXISTS = false;
    private static boolean COMPANY_ADDRESS_EXISTS = false;
    private static final String COMPANY_NAME_EXISTS_ERROR= "Company name has already been created - Unable to change Company name at this time\n";
    private static final String COMPANY_ADDRESS_CREATED= "Company address has already been created - Unable to change Company address at this time\n";
    private static final String NO_COMPANY = "You must create a Company name before setting Company address\n";
    private static final String COMPANY_ADDRESS = "Enter the address of the Company (Street, City, Postcode, State)";
    private static final String[] COMPANY_DETAILS = { "Street - ", "City - ", "Postcode - ", "State - "};
    private  static final String SETUP_INCOMPLETE = "Company setup is incomplete - Must create a Company and Address to access Payroll Menu\n";
    private static final int COMPANY_PAYROLL_APPLICATION = 3;
    private static final int EXIT_APPLICATION = 4;
    private static final int EXIT_EMPLOYEE_APPLICATION = 6;
    private static final String COMPANY_NOT_CREATED = "\nInvalid Selection - Company has not yet been created.\nBoth Company Name and Address must be created first\n";
    private static final String INVALID_CHOICE_PAYROLL_NOT_COMPLETE = "Invalid choice - Please select a valid option!\nNOTE: Payroll Menu not available until Company Name and Address setup is complete!\n\n";
    private static final String HOURS_WORKED = "Enter number of hours (as integer) the contractor worked - ";
    private static final String PARTS_COST = "Enter the cost of the parts - ";
    private static final String CONTRACTOR_NAME = "Enter the name of the contractor or Business Name - ";
    private static final String SALARY_LEVEL = "Enter salary for employee - ";
    private static final String EMP_NAME = "Enter name for employee - ";

    private ArrayList<Contractor> contractors = new ArrayList<Contractor>();
    private ArrayList<Employee> employees = new ArrayList<Employee>();
    private final static String PAYROLL_MENU = """            
              Company Payroll System
            
            1. Create new Contractor
            2. Create new Salary Employee
            3. Create Hourly Employee
            4. Show all Contractors
            5. Show all Salary/Hourly Employee
            6. Exit
            
            Enter your selection (1-6) -\s""";
    private final static String COMPANY_MENU = """           
             \nCompany Maintenance
            
            1. Create new Company
            2. Add Company Address
            3. Open Company Payroll Menu
            4. Exit Payroll Application
            
            Enter your selection (1-4) -\s""";
    private Company mainCompany;
    private String companyName;

    public static void main(String[] args) {
        Main program = new Main();

        boolean runApp = true;
        while (runApp) {
            runApp = program.executeMenus();
        }
        System.out.println("\nExiting Payroll Application\nGood Bye\n");
    }

    /**
     * Main control for employee creation
     * @param selection Type of employee
     */
    private void runPayrollApplication( int selection) {
        switch (selection) {

            case 1:
                createContractor();
                break;
            case 2:
                createSalaryEmployee();
                break;
            case 3:
                createHourlyEmployee();
                break;
            case 4:
                showContractors();
                break;
            case 5:
                showEmployee();
            default:
                break;
        }
    }

    /**
     * Set name of Employee
     * @param narration Narration for input
     * @return name of employee
     */
    private String setEmployeeName(String narration) {
        String employeeName="";
        while (true) {
            employeeName = askStringInput(narration, EMPLOYEE_NAME_ERROR_NARRATION);
            if (employeeName.isEmpty()) {
                System.out.println(EMPLOYEE_NAME_ERROR_NARRATION);
            } else {
                break;
            }
        }

        return employeeName;
    }


    /**
     * Print Contractor list
     */
    private void showContractors() {
        System.out.println("\nContractor List\n");
        for (Contractor contractor : contractors) {
            System.out.println(contractor.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * Show employees
     */
    private void showEmployee() {
        System.out.println("\nEmployee List\n");
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
        System.out.println("\n\n");
    }


    /**
     * Create Salary Employees
     */
    private void createSalaryEmployee() {
        double salary = askDoubleInput(SALARY_LEVEL,1,1000);
        String name = setEmployeeName(EMP_NAME);
        employees.add(new SalariedEmployee(name, salary));
    }

    /**
     * Create hourly Employee
     */
    private void createHourlyEmployee() {
        int hoursWorked = askIntegerInput(HOURS_WORKED,1,10);
        String name = setEmployeeName(EMP_NAME);
        employees.add(new HourlyEmployee(name, hoursWorked));
    }

    /**
     * Create contractor
     */
    private void createContractor() {
        int hoursWorked = askIntegerInput(HOURS_WORKED,1, 10);
        double partsCost = askDoubleInput(PARTS_COST, 1,1000);
        String name = setEmployeeName(CONTRACTOR_NAME);
        contractors.add(new Contractor(name, hoursWorked, partsCost));

    }


    /**
     * Main control of menu functions
     * @return
     */
    private boolean executeMenus() {
        int selection;
        if (COMPANY_EXISTS && COMPANY_ADDRESS_EXISTS) {
            selection = askIntegerInput(PAYROLL_MENU, 1, 6 );
            if (selection==EXIT_EMPLOYEE_APPLICATION) {
                return false;
            } else {
                runPayrollApplication(selection);
            }

        } else {
            selection = askIntegerInput(COMPANY_MENU, 1, 4 );
            if (selection!=EXIT_APPLICATION) {
                runCompanyMaintenance(selection);
                return true;
            }
        }
        return true;
    }


    /**
     * Generate company name and address
     * @param selection Option to execute
     */
    private void runCompanyMaintenance(int selection) {
        String[] details;
        switch (selection) {
            case 1:
                if (!COMPANY_NAME_CREATED) {
                    companyName = createCompanyName();
                } else {
                    errorNarration(COMPANY_NAME_EXISTS_ERROR);
                    System.out.println("Company name is " + companyName);
                }
                break;

            case 2:
                if (COMPANY_NAME_CREATED && !COMPANY_ADDRESS_EXISTS) {
                    details= addCompanyAddress();
                    mainCompany = createCompanyObject(details, companyName);
                    COMPANY_EXISTS=true;
                    COMPANY_ADDRESS_EXISTS=true;
                } else if (!COMPANY_NAME_CREATED){
                    errorNarration(NO_COMPANY);
                } else {
                    errorNarration(COMPANY_ADDRESS_CREATED);
                }
                break;
            case 3:
                if (COMPANY_EXISTS && COMPANY_ADDRESS_EXISTS) {
                    runPayrollApplication(0);
                } else {
                    errorNarration(COMPANY_NOT_CREATED);
                }

                break;
            default:
                System.out.println(INVALID_CHOICE_PAYROLL_NOT_COMPLETE);
                break;
        }

    }

    /**
     * Private helper method to simply print a narration - used for errors
     * @param narration Error narration to print
     */
    private void errorNarration(String narration) {
        System.out.println(narration);
    }

    /**
     * Creates a Company object once have address for company and a company name
     * @param addAdressDetails Array of Strings with Address Attributes
     * @param name Name of company
     * @return Company object - currently only 1 eexists
     */
    private Company createCompanyObject(String[] addAdressDetails, String name) {
        Address address = new Address(addAdressDetails[0], addAdressDetails[1], addAdressDetails[2],addAdressDetails[3]);
        return new Company(name, address);
    }

    /**
     * Method to get String inputs from user
     * @param narr Narration to be used as prompt for user
     * @return Input from user as String
     */
    private String askStringInput(String narr, String errorNarration) {
        Scanner newScanner =  new Scanner(System.in);
        String userInput="";

        while (true) {

            try {
                System.out.print(narr);
                userInput = newScanner.nextLine();
                if (!validString(userInput)) {
                    throw new IllegalArgumentException(errorNarration);
                } else {
                    break;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid input -  Enter a valid input\n");
            }
        }

        return userInput;
    }


    /**
     * Method to get the name of the Company
     * @return Name of company as string - changes state if COMPANY_NAME_CREATED to true
     */
    private String createCompanyName() {
        boolean valid = false;
        while (!valid) {
            companyName = askStringInput(COMPANY_NAME, COMPANY_ERROR_NARRATION_INPUT);
            if (companyName.isEmpty() || companyName.length()<=ACCEPTED_COMPANY_NAME_LEN) {
                System.out.println("\nInvalid Company name - Must only contain letters, numbers and spaces and be at least 4 characters\n");
            } else {
                valid = true;
            }
        }
        COMPANY_NAME_CREATED = true;
        return companyName;
    }


    /**
     * Method to get the attributes of an Address object
     * @return array of Strings representing Address Attributes
     */
    private String[] addCompanyAddress() {
        Scanner addScanner = new Scanner(System.in);
        String[] details = new String[4];
        int i = 0;
        boolean valid;
        for (String companyAdd : COMPANY_DETAILS) {
            valid = false;
            while (!valid) {

                System.out.print(PRE_NARR + companyAdd);
                try {
                    details[i] = addScanner.nextLine();
                    if (!validString(details[i])) {
                        System.out.printf("Invalid Company %s - must only contain letters, numbers or spaces. Minimum length must be greater than 3 characters\n", companyAdd);
                    } else {
                        valid = true;
                        i++;
                    }
                } catch (NoSuchElementException ex) {
                    System.out.println("Invalid input - must enter valid address component\n");
                }
            }
        }
        return details;
    }


    /**
     *
     * @param stringInput String to check not empty and is of minimum length and is only string characters and spaces
     * @return true if valid String else false
     */
    private boolean validString(String stringInput) {
        return (!stringInput.isBlank() && stringInput.length() >= ACCEPTED_COMPANY_NAME_LEN && stringInput.matches("[\\w\\s]*"));
    }

    /**
     * Method for getting input as an integer - validated input plus given range of acceptable values
     * @param narration Prompt for the user instructing what input required
     * @param lowerLimit Lowest acceptable value
     * @param upperLimit Highest acceptable value
     * @return The integer entered by user in console
     */
    public int askIntegerInput( String narration, int lowerLimit, int upperLimit) {
        Scanner userInput= new Scanner(System.in);
        int numberInput =0;
        while (true) {
            System.out.print( narration );
            try {
                numberInput=userInput.nextInt();
                if (numberInput < lowerLimit || numberInput > upperLimit) {
                    throw new IllegalArgumentException("Invalid input - input out of range\n");
                } else {
                    break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input - please enter an integer\n");
                userInput.nextLine();
            }   catch (IllegalArgumentException ex) {
                String errNarration = "Number must be between %d and %d inclusive";
                String finalNarration = String.format(errNarration, lowerLimit, upperLimit);
                System.out.printf(finalNarration);

            }
        }
        return numberInput;
    }

    /**
     * Method for getting input as a double - validated input plus given range of acceptable values
     * @param narration Prompt for the user instructing what input required
     * @param lowerLimit Lowest acceptable value
     * @param upperLimit Highest acceptable value
     * @return The double entered by user in console
     **/
    public double askDoubleInput(String narration, double lowerLimit, double upperLimit) {
        Scanner userInput = new Scanner(System.in);

        double doubleValue =0;
        while (true) {
            System.out.print(narration);

            try {
                doubleValue = userInput.nextDouble();
                if (doubleValue<0) {
                    System.out.println("Please enter a positive number\n");
                } else {
                    break;
                }

            } catch (NumberFormatException | NoSuchElementException ex) {
                System.out.println("Invalid input - please enter a number\n");
                userInput.nextLine();
            }
        }
        return doubleValue;
    }
}