package org.num.entity;

public class Minutes implements java.io.Serializable{
    private Integer mid;
    private Integer wid;
    private Integer minutes;
    private Integer count;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Minutes{" +
                "mid=" + mid +
                ", wid=" + wid +
                ", minutes=" + minutes +
                ", count=" + count +
                '}';
    }

}
