package Test;

public class PartTime extends Employee {
    private int minimumwage;
    private int workHours;

    public PartTime(String name, int empNo, String address, String phone, String hiredate, int minimumwage, int workHours) {
        super(name, empNo, address, phone, hiredate);
        this.minimumwage = minimumwage;
        this.workHours = workHours;
    }
    public void wage() {
        //시급x시간
        int totalwage = minimumwage * workHours;
        System.out.println(getName()+"의 최저시급은" + totalwage);
    }
}
