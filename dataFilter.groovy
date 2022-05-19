import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*
import groovy.util.XmlParser

def Message processData(Message message) {
    Reader reader = message.getBody(Reader);
	Map properties = message.getProperties();
	def i = 0;
    def messageBody = (message.getBody(java.lang.String) as String)
    if(messageBody.size()!=0){
        def rootNode = new XmlParser().parseText(message.getBody(java.lang.String) as String)
        while(rootNode.EmpEmployment[i]!=null){
            if(rootNode.EmpEmployment[i].cust_Total_Counting_information1.externalCode.size()==0){
                println "removed" + i
                rootNode.remove(rootNode.EmpEmployment[i])
            
            }
            else {
            i++  
            }
            
        }
        message.setBody(XmlUtil.serialize(rootNode))
    }
    
    return message
}