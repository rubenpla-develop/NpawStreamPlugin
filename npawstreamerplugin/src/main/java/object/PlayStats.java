package object;

public class PlayStats
{
    public PlayStats()
    {
        pauses = 0;
        resumes = 0;
    }

    private double elapseTime;

    private long pauses;

    private long resumes;

    public double getElapseTime()
    {
        return elapseTime;
    }

    public void setElapseTime(int elapseTime)
    {
        this.elapseTime = elapseTime;
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
        String statsReport = "Resumes amount : " + getResumes() + "\nPauses Amount : " + getPauses();

        return statsReport;
    }

    public String showResumes()
    {
        String resumes = "Resumes : " + getResumes();

        return resumes;
    }

    public String showPauses()
    {
        String pauses = "Pauses : " + getPauses();

        return pauses;
    }
}
