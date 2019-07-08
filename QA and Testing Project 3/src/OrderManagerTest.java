import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
public class OrderManagerTest {

    private OrderManager orderManager;
    private ProcessOrders processorMock;
    @Before
    public void setup() {
        //***
        //GIVEN - setup mock and other necessary objects
        //***
        // create ProcessOrders mock

        processorMock = mock(ProcessOrders.class);

        // create OrderManager instance with mock object

        orderManager = new OrderManager(processorMock);
    }
    @Test
    public void testProcessValidOrder() {
        //***
        //WHEN - have the mock object do something (that you want to test)
        //***
        //call processOrder() method of orderManager with any integer and store in boolean
        //***

        when(processorMock.getFraudulentOrders()).thenReturn(new ArrayList<>());
        orderManager.calculateProfit();
        verify(processorMock, times(2)).getFraudulentOrders();
        verifyNoMoreInteractions(processorMock);

        //THEN - verify expected result
        //***
        //A mock will remember all interactions.
        //You can selectively verify that specific methods were actually called.
        //you can also verify that no other interactions on the mock object
        //happened other than the ones expected above
    }
    @Test
    public void testProcessInvalidOrder() {
        //***
        //GIVEN - setup mock and define expected behavior
        //***
        // define return value when ProcessOrders methods are called
        // throw InvalidOrderException() when order num is 123
        //***
        //WHEN - have the mock object do something (that you want to test)
        //***

        doThrow(new InvalidOrderException()).when(processorMock).shipOrder(123);
        boolean response = orderManager.processOrder(123);
        assertFalse(response);

        //we expect this to be false
        //***
        //THEN - verify expected result
        //***

        verify(processorMock).shipOrder(anyInt());
        verifyNoMoreInteractions(processorMock);

        //A mock will remember all interactions.
        //You can selectively verify that specific methods were actually called.
        //you can also verify that no other interactions on the mock object
        //happened other than the ones expected above
    }
    @Test
    public void testAllValidOrders() {
        //***
        //GIVEN - setup mock and define expected behavior
        //***
        // define return value when getFraudulentOrders is called --
        // return empty list, meaning 0 fraudulent orders
        //***
        //WHEN - have the mock object do something (that you want to test)
        //***

        orderManager.processOrder(10) ;
        when(processorMock.getFraudulentOrders()).thenReturn(new ArrayList<>());

        //***
        //THEN - verify expected result
        //***

        double profit = orderManager.calculateProfit() ;
        assertTrue(profit==1.11);
    }
    @Test
    public void testProfitWithFrauds() {
        //***
        //GIVEN - setup mock and define expected behavior
        //***

        ArrayList<Order> list = new ArrayList<>() ;
        list.add(new Order(anyInt()));
        orderManager.processOrder(1) ;
        orderManager.processOrder(2) ;
        orderManager.processOrder(3) ;
        orderManager.processOrder(4) ;

        //***
        //WHEN - have the mock object do something (that you want to test)
        //***

        when(processorMock.getFraudulentOrders()).thenReturn(list) ;

        //***
        //THEN - verify expected result
        //***

        double increase = orderManager.calculateProfit();
        double answer = (3*1.11)-(1*1.33);
        assertTrue(increase==answer);
    }
}