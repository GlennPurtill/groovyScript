import com.sap.gateway.ip.core.customdev.util.Message;
import java.text.SimpleDateFormat;
import groovy.xml.*
import groovy.util.XmlParser

def Message processData(Message message) {
    Reader reader = message.getBody(Reader);
	Map properties = message.getProperties();
    //Define Dates
    def date = new Date()
    def sdf1 = new SimpleDateFormat("yyyy")
    
    //Define dates needed in logic
	def outerLoop = 0,innerLoop = 0,currentCheck,rootNode;
    def finalXml = "<PerPerson>",currentYear ="", nextYear =""
    def messageBody = (message.getBody(java.lang.String) as String)
    
    //if message body exists
    if(messageBody.size()!=0){
        //define root node of the XML
        rootNode = new XmlParser().parseText(message.getBody(java.lang.String) as String)
        //Iterate through each PerPerson node
        while(rootNode.PerPerson[outerLoop]!=null){
            currentYear = ""
            nextYear = ""
            //finalXml will define the desired output xml structure
            finalXml += "<PerPerson><personIdExternal>" + rootNode.PerPerson[outerLoop].personIdExternal.text() + "</personIdExternal>"
            innerLoop = 0;
            //Iterate through each buying holidays request
            while(rootNode.PerPerson[outerLoop].userAccountNav.UserAccount.user.User.externalCodeOfcust_BuyingholidaysNav.cust_Buyingholidays[innerLoop]!=null){
                currentCheck = rootNode.PerPerson[outerLoop].userAccountNav.UserAccount.user.User.externalCodeOfcust_BuyingholidaysNav.cust_Buyingholidays[innerLoop]
                //If current year (Jan 1st to Dec 31st)
                if(currentCheck.cust_From.text() >= (sdf1.format(date)+"-01-01'T'00:00:00.000") && currentCheck.cust_From.text() <= (sdf1.format(date)+"-12-31'T'00:00:00.000")){
                    currentYear += "<cust_Buyingholidays>" + 
                    "<cust_requestdate>" + currentCheck.cust_requestdate.text() +"</cust_requestdate>"+
                    "<cust_BuyingholidaysdaysNav>" + 
                    "<PickListValueV2>"+
                    "<externalCode>" + currentCheck.cust_BuyingholidaysdaysNav.PickListValueV2.externalCode.text() + "</externalCode>"+
                    "<PickListV2_effectiveStartDate>" + currentCheck.cust_BuyingholidaysdaysNav.PickListValueV2.PickListV2_effectiveStartDate.text() + "</PickListV2_effectiveStartDate>"+
                    "<label_defaultValue>" + currentCheck.cust_BuyingholidaysdaysNav.PickListValueV2.label_defaultValue.text() + "</label_defaultValue>"+
                    "<PickListV2_id>" + currentCheck.cust_BuyingholidaysdaysNav.PickListValueV2.PickListV2_id.text() + "</PickListV2_id>"+
                    "</PickListValueV2>"+
                    "</cust_BuyingholidaysdaysNav>"+
                    "<cust_SPercentage>" + currentCheck.cust_SPercentage.text() +"</cust_SPercentage>" +
                    "<lastModifiedDateTime>" + currentCheck.lastModifiedDateTime.text() +"</lastModifiedDateTime>" +
                    "<externalCode>" + currentCheck.externalCode.text() +"</externalCode>" +
                    "<cust_Wpercentage>" + currentCheck.cust_Wpercentage.text() +"</cust_Wpercentage>" +
                    "<cust_AdminStatus>" + currentCheck.cust_AdminStatus.text() +"</cust_AdminStatus>" +
                    "<cust_Buyingholidaysdays>" + currentCheck.cust_Buyingholidaysdays.text() +"</cust_Buyingholidaysdays>" +
                    "<cust_From>" + currentCheck.cust_From.text() +"</cust_From>" +
                    "<cust_Status_Request>" + currentCheck.cust_Status_Request.text() +"</cust_Status_Request>" +
                    "<cust_Status_RequestNav>" +
                    "<PickListValueV2>" +
                    "<label_defaultValue>" + currentCheck.cust_Status_RequestNav.PickListValueV2.label_defaultValue.text() +"</label_defaultValue>" +
                    "</PickListValueV2>"+
                    "</cust_Status_RequestNav>"+
                    "<effectiveStartDate>" + currentCheck.effectiveStartDate.text() + "</effectiveStartDate>" +
                    "<cust_To>" + currentCheck.cust_To.text() + "</cust_To>" +
                    "</cust_Buyingholidays>"
                }
                //Else if its next year (Jan 1st next year to December 31st next year)
                else if(currentCheck.cust_From.text() >= (sdf1.format(date+365)+"-01-01'T'00:00:00.000") && currentCheck.cust_From.text() <= (sdf1.format(date+365)+"-12-31'T'00:00:00.000")){ println currentCheck.cust_From.text() +" >= "+ (sdf1.format(date+365)+"-01-01'T'00:00:00.000") +"&&"+ currentCheck.cust_From.text() +"<="+ (sdf1.format(date+365)+"-12-31'T'00:00:00.000")

                    nextYear+="<cust_Buyingholidays>" + 
                    "<cust_requestdate>" + currentCheck.cust_requestdate.text() +"</cust_requestdate>"+
                    "<cust_BuyingholidaysdaysNav>" + 
                    "<PickListValueV2>"+
                    "<externalCode>" + currentCheck.cust_BuyingholidaysdaysNav.PickListValueV2.externalCode.text() + "</externalCode>"+
                    "<PickListV2_effectiveStartDate>" + currentCheck.cust_BuyingholidaysdaysNav.PickListValueV2.PickListV2_effectiveStartDate.text() + "</PickListV2_effectiveStartDate>"+
                    "<label_defaultValue>" + currentCheck.cust_BuyingholidaysdaysNav.PickListValueV2.label_defaultValue.text() + "</label_defaultValue>"+
                    "<PickListV2_id>" + currentCheck.cust_BuyingholidaysdaysNav.PickListValueV2.PickListV2_id.text() + "</PickListV2_id>"+
                    "</PickListValueV2>"+
                    "</cust_BuyingholidaysdaysNav>"+
                    "<cust_SPercentage>" + currentCheck.cust_SPercentage.text() +"</cust_SPercentage>" +
                    "<lastModifiedDateTime>" + currentCheck.lastModifiedDateTime.text() +"</lastModifiedDateTime>" +
                    "<externalCode>" + currentCheck.externalCode.text() +"</externalCode>" +
                    "<cust_Wpercentage>" + currentCheck.cust_Wpercentage.text() +"</cust_Wpercentage>" +
                    "<cust_AdminStatus>" + currentCheck.cust_AdminStatus.text() +"</cust_AdminStatus>" +
                    "<cust_Buyingholidaysdays>" + currentCheck.cust_Buyingholidaysdays.text() +"</cust_Buyingholidaysdays>" +
                    "<cust_From>" + currentCheck.cust_From.text() +"</cust_From>" +
                    "<cust_Status_Request>" + currentCheck.cust_Status_Request.text() +"</cust_Status_Request>" +
                    "<cust_Status_RequestNav>" +
                    "<PickListValueV2>" +
                    "<label_defaultValue>" + currentCheck.cust_Status_RequestNav.PickListValueV2.label_defaultValue.text() +"</label_defaultValue>" +
                    "</PickListValueV2>"+
                    "</cust_Status_RequestNav>"+
                    "<effectiveStartDate>" + currentCheck.effectiveStartDate.text() + "</effectiveStartDate>" +
                    "<cust_To>" + currentCheck.cust_To.text() + "</cust_To>" +
                    "</cust_Buyingholidays>"
                }
                innerLoop++
             }
             outerLoop++
             finalXml += "<currentYear>"+currentYear+"</currentYear><nextYear>"+nextYear+"</nextYear></PerPerson>"
         }
      } 
     
    finalXml += "</PerPerson>"   
    //Congiuring final xml
    def outputParse = new XmlParser().parseText(finalXml)
    message.setBody(XmlUtil.serialize(outputParse))  
	return message;
}