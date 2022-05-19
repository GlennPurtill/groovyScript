import com.sap.gateway.ip.core.customdev.util.Message;
import java.text.SimpleDateFormat;

def Message processData(Message message) {

    def date = new Date()
    def sdf = new SimpleDateFormat("MM")
    def sdf1 = new SimpleDateFormat("yyyy")
	
	message.setProperty('currentMonth', sdf.format(date));
    if(sdf.format(date)=="08"){
        message.setProperty('bookingStartDate', sdf1.format(date+365)+"-01-01T00:00:00.000");
        message.setProperty('bookingEndDate', sdf1.format(date+365)+"-12-31T00:00:00.000");
    }
    else{
        message.setProperty('bookingStartDate', sdf1.format(date)+"-01-01T00:00:00.000");
        message.setProperty('bookingEndDate', sdf1.format(date)+"-12-31T00:00:00.000");
    }
	return message;
}