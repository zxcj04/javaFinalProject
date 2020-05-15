package maintmp;

public class Cooler  extends Hardware
{
    private String height;

    public Cooler(String name, String height)
    {
        super(name);
        
        this.height = height;
    }
    
    public String getHeight()
    {
        return this.height;
    }
}