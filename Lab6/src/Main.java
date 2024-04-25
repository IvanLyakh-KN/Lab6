import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactManager contactManager = new ContactManager();
        boolean running = true;

        while (running) {
            System.out.println("Menu:");
            System.out.println("1. View all contacts");
            System.out.println("2. Add contact");
            System.out.println("3. Find contact");
            System.out.println("4. Delete contact");
            System.out.println("5. Exit");
            System.out.print("\nSelect an option: ");

            Scanner menuScanner = new Scanner(System.in);

            if (menuScanner.hasNextInt()) {
                int choice = menuScanner.nextInt();

                switch (choice) {
                    case 1:
                        // Переглянути всі контакти
                        contactManager.getAllContacts();
                        break;
                    case 2:
                        // Додати контакт
                        contactManager.getInputAndSignUp();
                        break;
                    case 3:
                        // Знайти контакт
                        contactManager.searchContact();
                        break;
                    case 4:
                        // Видалити контакт
                        contactManager.deleteContact();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Wrong choice. Try again.");
                        break;
                }
            } else {
                System.out.println("Input number, please!");
                menuScanner.next();
            }
        }
        contactManager.closeScanner();
    }
}
