package maintmp;

public class Cpu 
{
    private String cores;                   // 核心數 
    private String threads;                 // 線程數
    private String TDP;                     // TDP
    private String frequency;               // 基本頻率
    private String turboBoost;              // boost頻率
    private String socket;                  // 腳位
    private String ramMaximumSupport;       // 支援記憶體大小
    private String ramGenerationSupport;    // 支援記憶體代數
    private String internalGraphic;         // 內顯(N/A for no interVGA)   

    public Cpu(String cores, String threads, String TDP, String frequency, String turboBoost, String socket, String ramMaximumSupport, String ramGenerationSupport, String internalGraphic)
    {
        this.cores = cores;
        this.threads = threads;
        this.TDP = TDP;
        this.frequency = frequency;
        this.turboBoost = turboBoost;
        this.socket = socket;
        this.ramMaximumSupport = ramMaximumSupport;
        this.ramGenerationSupport = ramGenerationSupport;
        this.internalGraphic = internalGraphic;
    }

    public String getCores()
    {
        return cores;
    }

    public String getThreads()
    {
        return threads;
    }

    public String getTDP()
    {
        return TDP;
    }

    public String getFrequency()
    {
        return frequency;
    }

    public String getTurboBoost()
    {
        return turboBoost;
    }

    public String getSocket()
    {
        return socket;
    }

    public String getRamMaximumSupport()
    {
        return ramMaximumSupport;
    }

    public String getRamGenerationSupport()
    {
        return ramGenerationSupport;
    }

    public String getInternalGraphic()
    {
        return internalGraphic;
    }
}