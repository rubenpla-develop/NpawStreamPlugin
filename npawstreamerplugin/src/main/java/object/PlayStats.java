package object;

public class PlayStats
{
    public PlayStats()
    {
        pauses = 0;
        resumes = 0;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    private long startTime;

    private long elapseTime;

    private long totalElapseTime;

    private long pauses;

    private long resumes;

    public long getElapseTime()
    {
        return elapseTime;
    }

    public void setElapseTime(long elapseTime)
    {
        this.elapseTime += elapseTime;
    }

    public long getPauses()
    {
        return pauses;
    }

    public void setPauses(long pauses)
    {
        this.pauses = pauses;
    }

    public long getResumes()
    {
        return resumes;
    }

    public void setResumes(long resumes)
    {
        this.resumes = resumes;
    }

    public void increasePauses()
    {
        pauses += 1;
    }

    public void increaseResumes()
    {
        resumes += 1;
    }

    public String showPlayStatsReport()
    {
        String statsReport = "Resumes amount : " + getResumes() + "\nPauses Amount : " + getPauses() + "\nTotal Elapse time : " + getTotalElapseTime());

        return statsReport;
    }

    public String showResumes()
    {
        String resumes = "Resumes : " + getResumes() + "\nElapse Time :" + getElapseTime();

        return resumes;
    }

    public String showPauses()
    {
        String pauses = "Pauses : " + getPauses();

        return pauses;
    }

    public long getTotalElapseTime()
    {
        return totalElapseTime;
    }

    public void setTotalElapseTime(long eTime)
    {
        this.totalElapseTime += eTime;
    }
}
