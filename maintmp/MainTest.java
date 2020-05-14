package maintmp;

public class MainTest 
{
    public static void main(String[] args) 
    {
        MainGee gee = new MainGee();

        double a = System.currentTimeMillis();

        HardwareNameList geeList = gee.getList();

        double b = System.currentTimeMillis();

        geeList = gee.getList();

        double c = System.currentTimeMillis();

        System.out.printf("%f %f%n", b - a, c - b);
        System.out.println(geeList);
    }
}