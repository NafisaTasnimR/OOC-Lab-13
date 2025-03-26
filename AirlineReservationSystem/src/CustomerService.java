import java.util.*;

public class CustomerService {
    public static final List<Customer> customerCollection = User.getCustomersCollection();
    private final Map<String, List<Flight>> customerFlights = new HashMap<>();
    private final Map<String, List<Integer>> ticketBookings = new HashMap<>();
    public CustomerDisplayHelper customerDisplayHelper;

    public CustomerService() {
        this.customerDisplayHelper = new CustomerDisplayHelper();
    }

    public void addNewCustomer(Customer customer) {
        customerCollection.add(customer);
    }
    public void searchUser(String ID) {
        Customer customer = findCustomerByID(ID);

        if (customer != null) {
            System.out.printf("%-50sCustomer Found...!!! Here is the Full Record...!!!\n\n\n", " ");
            customerDisplayHelper.displayHeader();
            System.out.println(customerDisplayHelper.toString(customer,1));
            customerDisplayHelper.displayTail();
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }
    public void deleteUser(String ID) {
        Iterator<Customer> iterator = customerCollection.iterator();
        Customer customerToDelete = findCustomerByID(ID);
        if (customerToDelete != null) {
            while (iterator.hasNext()) {
                if (iterator.next().getUserID().equals(ID)) {
                    iterator.remove();
                    break;
                }
            }
            System.out.printf("\n%-50sPrinting all  Customer's Data after deleting Customer with the ID %s.....!!!!\n",
                    "", ID);
            displayCustomersData(false);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }
    public void editUserInfo(String ID,List<String> details) {
        Customer customer = findCustomerByID(ID);

        if (customer != null) {
            customer.setName(details.get(0));
            customer.setEmail(details.get(1));
            customer.setPhone(details.get(2));
            customer.setAddress(details.get(3));
            customer.setAge(Integer.parseInt(details.get(4)));
            displayCustomersData(false);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    public void addFlightToCustomer(String userID, Flight flight, int tickets) {
        customerFlights.computeIfAbsent(userID, k -> new ArrayList<>()).add(flight);
        ticketBookings.computeIfAbsent(userID, k -> new ArrayList<>()).add(tickets);
    }

    public void removeFlightFromCustomer(String userID, int index) {
        if (customerFlights.containsKey(userID) && index < customerFlights.get(userID).size()) {
            customerFlights.get(userID).remove(index);
            ticketBookings.get(userID).remove(index);
        }
    }
    private Customer findCustomerByID(String ID) {
        for (Customer customer : customerCollection) {
            if (ID.equals(customer.getUserID())) {
                return customer;
            }
        }
        return null;
    }
    public void displayCustomersData(boolean showHeader) {
        customerDisplayHelper.displayHeader();
        Iterator<Customer> iterator = customerCollection.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Customer c = iterator.next();
            System.out.println(customerDisplayHelper.toString(c,i));
            customerDisplayHelper.displayTail();
        }
    }

}
