package Utils;

/**
 * A time base in microseconds for media playback.
 */
class TimeBase
{

    private long mStartTime;

    public TimeBase() {
        start();
    }

    public void start() {
        startAt(0);
    }

    public void startAt(long mediaTime) {
        mStartTime = microTime() - mediaTime;
    }

    public long getCurrentTime() {
        return microTime() - mStartTime;
    }

    public long getOffsetFrom(long from) {
        return  from - getCurrentTime();
    }

    private long microTime() {
        return (long)(System.nanoTime() / 1000);
    }
}
