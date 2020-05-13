package maintmp;

public class MainTest 
{
    public static void main(String[] args) 
    {
        MainGee gee = new MainGee();

        HardWareList geeList = gee.getList();

        System.out.println(geeList);
    }
}