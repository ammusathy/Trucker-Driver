package driver.com.driver.screens;

/**
 * Created by kalaivani on 2/9/2016.
 */
public class JobListItem {

     String pickuparea;
     String Date;
     String Time;
     String Dollar;
     String Status;

     public JobListItem() {
     }

     public JobListItem(String pickuparea, String status, String dollar, String time, String date) {
          this.pickuparea = pickuparea;
          Status = status;
          Dollar = dollar;
          Time = time;
          Date = date;

     }

     public String getPickuparea() {
          return pickuparea;
     }

     public void setPickuparea(String pickuparea) {
          this.pickuparea = pickuparea;
     }

     public String getDate() {
          return Date;
     }

     public void setDate(String date) {
          Date = date;
     }

     public String getTime() {
          return Time;
     }

     public void setTime(String time) {
          Time = time;
     }

     public String getDollar() {
          return Dollar;
     }

     public void setDollar(String dollar) {
          Dollar = dollar;
     }

     public String getStatus() {
          return Status;
     }

     public void setStatus(String status) {
          Status = status;
     }
}
