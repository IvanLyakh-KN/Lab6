import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class ContactManager {
    private Scanner scanner;

    public void getAllContacts() {
        String query = "SELECT * FROM " + Const.NAME_TABLE;

        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            Connection connection = dbHandler.getDbConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("\n" + "Empty");
            } else {
                System.out.println("\n" + "All contacts:");
                while (resultSet.next()) {
                    String id = resultSet.getString(Const.PERSON_ID);
                    String firstName = resultSet.getString(Const.PERSON_FIRST_NAME);
                    String lastName = resultSet.getString(Const.PERSON_LAST_NAME);
                    String phoneNumber = resultSet.getString(Const.PERSON_PHONE_NUMBER);

                    System.out.println("id: " + id);
                    System.out.println("First name: " + firstName);
                    System.out.println("Last name: " + lastName);
                    System.out.println("Phone number: +" + phoneNumber);
                    System.out.println("---------------------");
                }
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void getInputAndSignUp() {
        this.scanner = new Scanner(System.in);

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        String phoneNumber;
        boolean validPhoneNumber = false;
        do {
            System.out.print("Enter phone number: +");
            phoneNumber = scanner.nextLine();

            if (phoneNumber.matches("\\d{12}")) {
                validPhoneNumber = true;
            } else {
                System.out.println("Invalid phone number format.");
            }
        } while (!validPhoneNumber);

        System.out.println("Added successfully!");

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.signUpPersone(firstName, lastName, phoneNumber);
    }

    public void closeScanner() {
        scanner.close();
    }

    public void searchContact() {
        this.scanner = new Scanner(System.in);

        System.out.println("Choose a search type:");
        System.out.println("1. By id");
        System.out.println("2. By first name");
        System.out.println("3. By last name");
        System.out.println("4. By phone number");

        if (!scanner.hasNextInt()) {
            System.out.println("Input a number, please!");
            return;
        }

        int searchOption = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter a search query:");
        String searchQuery = scanner.nextLine();

        String query;
        switch (searchOption) {
            case 1:
                query = "SELECT * FROM " + Const.NAME_TABLE + " WHERE " + Const.PERSON_ID + " = ?";
                break;
            case 2:
                query = "SELECT * FROM " + Const.NAME_TABLE + " WHERE " + Const.PERSON_FIRST_NAME + " LIKE ?";
                searchQuery = "%" + searchQuery + "%";
                break;
            case 3:
                query = "SELECT * FROM " + Const.NAME_TABLE + " WHERE " + Const.PERSON_LAST_NAME + " LIKE ?";
                searchQuery = "%" + searchQuery + "%";
                break;
            case 4:
                query = "SELECT * FROM " + Const.NAME_TABLE + " WHERE " + Const.PERSON_PHONE_NUMBER + " LIKE ?";
                searchQuery = "%" + searchQuery + "%";
                break;
            default:
                System.out.println("\n" + "Wrong choice. Try again.");
                return;
        }

        try {
            DataBaseHandler dbHandler = new DataBaseHandler();
            Connection connection = dbHandler.getDbConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, searchQuery);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Contact not found.\n");
            } else {
                System.out.println("Search Results:\n");
                while (resultSet.next()) {
                    String id = resultSet.getString(Const.PERSON_ID);
                    String firstName = resultSet.getString(Const.PERSON_FIRST_NAME);
                    String lastName = resultSet.getString(Const.PERSON_LAST_NAME);
                    String phoneNumber = resultSet.getString(Const.PERSON_PHONE_NUMBER);

                    System.out.println("id: " + id);
                    System.out.println("First name: " + firstName);
                    System.out.println("Last name: " + lastName);
                    System.out.println("Phone number: +" + phoneNumber);
                    System.out.println("---------------------");
                }
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact() {
        this.scanner = new Scanner(System.in);

        System.out.println("Enter the contact ID to delete:");

        if (!scanner.hasNextInt()) {
            System.out.println("Input number, please!");
            return;
        } else {
            int contactId = scanner.nextInt();

            String query = "DELETE FROM " + Const.NAME_TABLE + " WHERE " + Const.PERSON_ID + " = ?";

            try {
                DataBaseHandler dbHandler = new DataBaseHandler();
                Connection connection = dbHandler.getDbConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, contactId);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("\n" + "Contact deleted successfully.");
                } else {
                    System.out.println("\n" + "The contact with the entered ID was not found.");
                }

                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
