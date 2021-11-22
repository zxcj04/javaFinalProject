package mainlogic;

public class Hardware 
{
    private String name;
    
    public Hardware(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        return this.getName();
    }
}