package driver.com.driver.model.GeneralParams;

/**
 * Created by kalaivani on 4/28/2016.
 */
public class JobListItems {
    public String pickup,status;

    public JobListItems() {
    }

    public JobListItems(String pickup, String status, int pick_date, int pick_time, int dollar) {
        this.pickup = pickup;
        this.status = status;
        this.pick_date = pick_date;
        this.pick_time = pick_time;

        this.dollar = dollar;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPick_date() {
        return pick_date;
    }

    public void setPick_date(int pick_date) {
        this.pick_date = pick_date;
    }

    public int getPick_time() {
        return pick_time;
    }

    public void setPick_time(int pick_time) {
        this.pick_time = pick_time;
    }

    public int getDollar() {
        return dollar;
    }

    public void setDollar(int dollar) {
        this.dollar = dollar;
    }

    public int pick_date,pick_time,dollar;
}
