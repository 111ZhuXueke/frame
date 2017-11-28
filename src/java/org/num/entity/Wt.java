package org.num.entity;

public class Wt {
    private Integer id;
    private Integer years;
    private Integer months;
    private Integer days;
    private Integer hours;

    @Override
    public String toString() {
        return "Wt{" +
                "id=" + id +
                ", years=" + years +
                ", months=" + months +
                ", days=" + days +
                ", hours=" + hours +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
