import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by kber0001 on 24/06/2015.
 */
public class PaintShop {

    private int noOfUniquePaints;
    private int totalCustomers;
    private List<String> orders = new ArrayList<String>();
    private Map<Integer, Paint> paintMix = new HashMap<Integer, Paint>();

    private List<Customer> customers = new ArrayList<Customer>();


    public String processOrder(String path) {
        readFromFile(path);
        return mixPaints(orders);
    }


    String mixPaints(List<String> orders) {
        int currentId = 1;

        for (String order : orders) {

            Customer customer = new Customer();
            customer.setId(Integer.toString(currentId));

            for (int i = 0; i < order.length(); i = i + 2) {
                char paintCode = order.charAt(i);
                Paint.Type paintType = Paint.Type.valueOf(Character.toString(order.charAt(i + 1)));
                Paint paint = new Paint(Character.getNumericValue(paintCode), paintType, false);
                customer.getPaints().add(paint);
            }
            customers.add(customer);
            currentId++;
        }
        //We prepare a mix of the cheapest choice which is all paints is Gloss. This will change if needed according to the clients' needs.
        prepareCheapestMix();
        //Now we have the cheapest option, with all paints to type Glossy.
        for (Customer customer : customers) {
            //If the customer has only one paint and need to be satisfied, then the paint must be either locked and the user's choice or unlocked and make it so - Then lock it.
            // If this is not possible, the the order is not feasible.
            if (customer.getPaints().size() == 1) {
                Paint currentCustomerPaint = customer.getPaints().get(0);
                if (paintMix.get(currentCustomerPaint.getId()).isLocked() && paintMix.get(currentCustomerPaint.getId()).getType() == currentCustomerPaint.getType()) {
                    customer.setSatisfied(true);
                } else if (!paintMix.get(currentCustomerPaint.getId()).isLocked()) {
                    paintMix.get(currentCustomerPaint.getId()).setType(currentCustomerPaint.getType());
                    paintMix.get(currentCustomerPaint.getId()).setLocked(true);
                    customer.setSatisfied(true);
                } else {
                    return "No solution exists";
                }
            }
            //Only up to one Matte type is allowed per customer. If he has this expensive choice we store it here so if another paint doesn't satisfy him we can mix this.
            int customerMatteChoice = -1;
            //Iterate through the customers and match their choices with the current paintMix.
            for (Paint customerPaint : customer.getPaints()) {
                //If the type of the paint is the same as the one already chosen then set the customer as satisfied and the paint locked
                if (paintMix.get(customerPaint.getId()).getType() == customerPaint.getType()) {
                    customer.setSatisfied(true);
                    paintMix.get(customerPaint.getId()).setLocked(true);
                    break;
                } else if (!paintMix.get(customerPaint.getId()).isLocked() && customerPaint.getType() == Paint.Type.M) {
                    //Save the Matte choice in case there is no other paint that satisfies the customer and also the color is Matte and the paint is not locked.
                    customerMatteChoice = customerPaint.getId();
                }
            }
            //If the customer is not satisfied and there was a Matte choice and also the paint is not locked then
            // change the type to Matte and set both paint to locked and customer as satisfied.
            if (!customer.isSatisfied() && customerMatteChoice != -1 && !paintMix.get(customerMatteChoice).isLocked()) {
                customer.setSatisfied(true);
                paintMix.get(customerMatteChoice).setType(Paint.Type.M);
                paintMix.get(customerMatteChoice).setLocked(true);
            }
        }
        //If even one customer is not satisfied then print "No solution exists"
        for (Customer customer : customers) {
            if (!customer.isSatisfied()) {
                return "No solution exists";
            }
        }
        return paintsToString(paintMix.values());
    }

    public static String paintsToString(Collection<Paint> paints) {
        StringBuilder result = new StringBuilder();
        for (Paint paint : paints) {
            result.append(paint.getType().name());
            result.append(" ");
        }
        return result.toString().trim();
    }

    public void prepareCheapestMix() {
        for (int i = 1; i <= noOfUniquePaints; i++) {
            paintMix.put(i, new Paint(i, Paint.Type.G, false));
        }
    }

    private void readFromFile(String path) {
        File input = null;
        try {
            input = new File(this.getClass().getResource("/" + path).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            //Log error instead
        }
        BufferedReader br;
        int lineNo = 0;
        try {
            br = new BufferedReader(new FileReader(input));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (lineNo < 1) {
                    noOfUniquePaints = Integer.parseInt(currentLine);
                } else {
                    orders.add(currentLine.replaceAll("\\s", ""));
                }
                lineNo++;
            }

            totalCustomers = lineNo - 1;
            //We sort them so we have the single paint customers first. That helps resolved the easy orders and
            // lock the paints in order to satisfy them.
            java.util.Collections.sort(orders, new StringLengthComparator());
        } catch (Exception e) {
            e.printStackTrace();
            //log error instead
        }
    }

    public Map<Integer, Paint> getPaintMix() {
        return paintMix;
    }
}
