package maintmp;

public class Vga extends Hardware
{
    private String length;

    public Vga(String name, String length)
    {
        super(name);

        this.length = length;
    }

    public String getLength()
    {
        return this.length;
    }
}