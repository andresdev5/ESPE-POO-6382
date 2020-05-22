/**
 * workshop #02 from OOP6382 assignature
 * 
 * @author Andres Jacome <ajjacome2@espe.edu.ec>
 * @version 0.1
 */
public class HelloWorld {
    /** Application entry point */
    public static void main(String[] args) {
        int number1 = 58;
        int number2 = 69;
        int number3 = 5;
        int addition = addTwoNumbers(number1, number2);
        int cube = cube(number3);

        System.out.println("hello world from Andres Jacome");
        System.out.printf("the addition of %d and %d is %d\n", number1, number2, addition);
        System.out.printf("The cube of %d is %d\n", number3, cube);
    }

    /**
     * add two given numbers
     * 
     * @param number1
     * @param number2
     * @return addition
     */
    static int addTwoNumbers(int number1, int number2) {
        int result = number1 + number2;
        return result;
    }

    /**
     * find the cube of a given number
     * 
     * @param number
     * @return cube of a given number
     */
    static int cube(int number) {
        int result = number * number * number;
        return result;
    }
}