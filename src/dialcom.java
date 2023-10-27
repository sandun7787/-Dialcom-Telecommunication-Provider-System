import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class dialcom {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Username:");
        String userID = scanner.next();
        System.out.println("Enter the Password:");
        String inputPassword = scanner.next();
        String filePassword = readCredentials(userID);

        if (inputPassword != null && inputPassword.equals(filePassword)) {
            System.out.println("Logged In!!");
            String ans = "yes";
            while (ans.equals("yes")) {
                System.out.println("Choose an Option:");
                System.out.println("1- Register New Customer");
                System.out.println("2- Display All Customers");
                System.out.println("3- Update Customer Profile");
                System.out.println("4- Delete Customer");
                System.out.println("5- Generate Bill");
                System.out.println("6- Help");
                System.out.println(" ");
                System.out.println("Enter your option:");
                int option = scanner.nextInt();
                switch (option) {
                    case 1 -> addProfile();
                    case 2 -> readData();
                    case 3 -> updateProfile();
                    case 4 -> deleteUser();
                    case 5 -> generateBill();
                    case 6 -> help();
                    default -> System.out.println("Invalid option");
                }

                System.out.println(" ");
                System.out.println("*************");
                System.out.println("Enter 'yes' to go to the Main Menu");
                System.out.println("Enter 'no' to Exit");
                System.out.println("*************");
                System.out.println(" ");

                ans = scanner.next();
            }
        } else {
            System.out.println("Invalid Username or Password");
        }
    }

    public static String readCredentials(String user) {
        String password = "";
        if (user.equals("ashani")) {
            password = "123";
        }
        return password;
    }

    private static void addProfile() {
        String Name = null, Address = null, stringPno, stringNo;
        int No = 0;
        int Pno = 0;
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Enter Name: (Firstname & Lastname)");
            Name = sc.nextLine();
            boolean intCheckPno = false;
            while (!intCheckPno) {
                try {
                    System.out.println("Enter Contact Number:");
                    stringPno = sc.nextLine();
                    Pno = Integer.parseInt(stringPno);
                    intCheckPno = true;
                } catch (NumberFormatException e) {
                    System.out.println("Contact Number Must be a Numeric Value");
                }
            }
            System.out.println("Enter Address: ");
            Address = sc.nextLine();
            boolean intCheckNo = false;
            while (!intCheckNo) {
                try {
                    System.out.println("Enter Account Number:");
                    stringNo = sc.nextLine();
                    No = Integer.parseInt(stringNo);
                    intCheckNo = true;
                } catch (NumberFormatException e) {
                    System.out.println("Account Number Must be a Numeric Value");
                }
            }
            System.out.println("Customer added successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid data entry");
        }

        try {
            try (FileWriter fw = new FileWriter("dialcom_details.txt", true)) {
                fw.write(No + ",");
                fw.write(Name + ",");
                fw.write(Address + ",");
                fw.write(Pno + "\n");
            }
        } catch (IOException e) {
            System.err.println("File Write denied");
        }
    }

    private static void readData() {
        File myObj = new File("dialcom_details.txt");
        try {
            try (Scanner sc = new Scanner(myObj)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] arr = line.split(",");
                    System.out.println("----------------------------------------------------------------------");
                    System.out.println(" ");
                    System.out.println("Account Number - " + arr[0]);
                    System.out.println("Account Owners Name - " + arr[1]);
                    System.out.println("Address - " + arr[2]);
                    System.out.println("Contact Number " + arr[3]);
                    System.out.println(" ");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static void updateProfile() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter Account Number:");
        String No = sc1.nextLine();
        boolean flag = false;
        String line = "";
        ArrayList<String> lines = new ArrayList<>();
        try {
            try (Scanner sc = new Scanner(new File("dialcom_details.txt"))) {
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    String[] arr = line.split(",");
                    if (arr[0].equals(No)) {
                        flag = true;
                        System.out.println("Enter Name: (Firstname & Lastname)");
                        String Name = sc1.nextLine();
                        System.out.println("Enter Contact Number:");
                        String stringPno = sc1.nextLine();
                        int Pno = Integer.parseInt(stringPno);
                        System.out.println("Enter Address: ");
                        String Address = sc1.nextLine();
                        
                        // Prompt for voice minutes update
                        System.out.println("Enter New Voice Minutes:");
                        String stringVoiceMinutes = sc1.nextLine();
                        int voiceMinutes = Integer.parseInt(stringVoiceMinutes);

                        // Prompt for data usage update
                        System.out.println("Enter New Data Usage (in GB):");
                        String stringDataUsage = sc1.nextLine();
                        double dataUsageGB = Double.parseDouble(stringDataUsage);

                        String line1 = No + "," + Name + "," + Address + "," + Pno + "," +
                                voiceMinutes + "," + dataUsageGB; // Update customer profile
                        lines.add(line1);
                        System.out.println("Customer Information Updated Successfully!!!");
                    } else {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("File Write denied");
        }
        if (!flag) {
            System.out.println("The Customer Account Number is Incorrect. Please Check Again!");
        }

        try (FileWriter writer = new FileWriter("dialcom_details.txt", false)) {
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            System.err.println("File Write denied");
        }
    }

 private static void deleteUser() {
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter The Account No:");
        String AcNo = sc1.next();
        boolean flag = false;
        String line = " ";
        ArrayList<String> lines = new ArrayList<>();
        try {
            try (FileWriter writer = new FileWriter("dialcom_details.txt", true)) {
                try (Scanner sc = new Scanner(new File("dialcom_details.txt"))) {
                    while (sc.hasNextLine()) {
                        line = sc.nextLine();
                        String[] arr = line.split(",");
                        if (arr[0].equals(AcNo)) {
                            flag = true;
                        } else {
                            lines.add(line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("File Write denied");
        }
        if (!flag) {
            System.out.println("The Customer Account Number is Incorrect. Please Check Again!");
        } else {
            System.out.println("Customer Removed!!!");
        }
    }

    private static void generateBill() {
        Scanner sc1 = new Scanner(System.in);

        // Define the pricing details
        double voiceRate = 0.50; // Cost per minute for voice calls
        double dataRate = 20;  // Cost per GB for data usage

        // Prompt for the billing month
        System.out.println("Enter the billing month (e.g., January, February, etc.):");
        String billingMonth = sc1.nextLine();

        // Prompt for the account number
        System.out.println("Enter the account number:");
        String accountNumber = sc1.nextLine();

        // Prompt for voice minutes and data usage
        System.out.println("Enter the number of voice minutes used:");
        String stringVoiceMinutes = sc1.nextLine();
        int voiceMinutes = Integer.parseInt(stringVoiceMinutes);

        System.out.println("Enter the data usage (in GB):");
        String stringDataUsage = sc1.nextLine();
        double dataUsageGB = Double.parseDouble(stringDataUsage);

        // Calculate costs
        double voiceCost = voiceMinutes * voiceRate;
        double dataCost = dataUsageGB * dataRate;

        // Additional charges (if any)
        double additionalCharges = 15.0; 

        // Calculate the total bill
        double totalBill = voiceCost + dataCost + additionalCharges;

        // Display the phone bill
        System.out.println("Phone Bill Details for " + billingMonth + ":");
        System.out.println("Voice Usage: " + voiceMinutes + " minutes");
        System.out.println("Voice Usage Cost: $" + voiceCost);
        System.out.println("Data Usage: " + dataUsageGB + " GB");
        System.out.println("Data Usage Cost: $" + dataCost);
        System.out.println("Additional Charges: $" + additionalCharges);
        System.out.println("Total Bill: $" + totalBill);

        // Save the billing details to the billing_records.txt file
        try (FileWriter fw = new FileWriter("billing_records.txt", true)) {
            fw.write(accountNumber + ",");
            fw.write(billingMonth + ",");
            fw.write(voiceMinutes + ",");
            fw.write(dataUsageGB + ",");
            fw.write(totalBill + "\n");
        } catch (IOException e) {
            System.err.println("File Write denied");
        }
    }

    private static void help() {
        System.out.println("Help Center");
        System.out.println(" Can you tell your problem !");
        System.out.println(" ");
        System.out.println("Remember to follow the prompts and instructions for each option.");
    }
}

