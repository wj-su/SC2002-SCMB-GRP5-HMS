package patient;

import java.util.HashMap;
import java.util.Map;

import doctor.AppointmentOutcomeRecord;

public class BillingDetails {
    private Map<String, Map<Integer, Map<String, Object>>> outrecs = new HashMap<>();

    public BillingDetails(){
        this.outrecs = AppointmentOutcomeRecord.getAllOutcomeRecords();
    }    


}
