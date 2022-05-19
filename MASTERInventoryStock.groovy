import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*
import groovy.util.XmlParser

Message processData(Message message) {
	//retireving message properties
	Reader reader = message.getBody(Reader);
	Map properties = message.getProperties();
	
	//takes in the rootNode and converts to XML array
	def rootNode = new XmlParser().parseText(message.getBody(java.lang.String) as String)
	
	//Definitions needed for the logic
	def productIndex = 0, productIndexInner=0, masterIdentifier, innerMasterIdentifier
	int qtySum = 0
	
	rootNode.children().sort{ it.ScheduleLineDeliveryDate.text() }
	rootNode.children().sort{ it.Material.text() }
	
	while(rootNode.YY1_MaterialStockAvailabilType[productIndex]!=null){
	    masterIdentifier = rootNode.YY1_MaterialStockAvailabilType[productIndex].Material.text() + rootNode.YY1_MaterialStockAvailabilType[productIndex].PurchaseOrder.text() + rootNode.YY1_MaterialStockAvailabilType[productIndex].PurchaseOrderQuantityUnit.text()
	    while(rootNode.YY1_MaterialStockAvailabilType[productIndexInner]!=null){
	        innerMasterIdentifier = rootNode.YY1_MaterialStockAvailabilType[productIndexInner].Material.text() + rootNode.YY1_MaterialStockAvailabilType[productIndexInner].PurchaseOrder.text() + rootNode.YY1_MaterialStockAvailabilType[productIndexInner].PurchaseOrderQuantityUnit.text()
	        	if((masterIdentifier)!==(innerMasterIdentifier)){
	        	 	rootNode.remove(rootNode.YY1_MaterialStockAvailabilType[productIndexInner])   
	        	}
	    }
	}
	
	/*
	while(rootNode.YY1_MaterialStockAvailabilType[productIndex]!=null){
		//Gets material id for the comparison
		masterIdentifier = rootNode.YY1_MaterialStockAvailabilType[productIndex].Material.text() + rootNode.YY1_MaterialStockAvailabilType[productIndex].PurchaseOrder.text() + rootNode.YY1_MaterialStockAvailabilType[productIndex].PurchaseOrderQuantityUnit.text()
		//Reset the material quantity sum
		qtySum = 0
		//Ensures we are not check the same node against eachother in the inner loop
		productIndexInner = productIndex+1
		//Adds the quantity to overall qtysum
		if(rootNode.YY1_MaterialStockAvailabilType[productIndex].InventoryStockType.text() as Integer == 01 ){
		    qtySum += (rootNode.YY1_MaterialStockAvailabilType[productIndex].MatlWrhsStkQtyInMatlBaseUnit.text()) as Integer
		}
		//Inner Loop: Checks the remaing nodes material id versus the node selected in the outer loop
		while(rootNode.YY1_MaterialStockAvailabilType[productIndexInner]!=null){
			//If statement checks the materials Id's against eachother
			innerMasterIdentifier = rootNode.YY1_MaterialStockAvailabilType[productIndexInner].Material.text() + rootNode.YY1_MaterialStockAvailabilType[productIndexInner].PurchaseOrder.text() + rootNode.YY1_MaterialStockAvailabilType[productIndexInner].PurchaseOrderQuantityUnit.text()
			
			if((masterIdentifier).equals(innerMasterIdentifier)){
				if(rootNode.YY1_MaterialStockAvailabilType[productIndexInner].InventoryStockType.text() as Integer == 01 ){
					qtySum += (rootNode.YY1_MaterialStockAvailabilType[productIndexInner].MatlWrhsStkQtyInMatlBaseUnit.text()) as Integer
				}
				rootNode.remove(rootNode.YY1_MaterialStockAvailabilType[productIndexInner])
			}else{
			//else we skip this node
			productIndexInner++
			}
		}
		//Lastly we replace qtySum into value MatlStkIncrQtyInMatlBaseUnit
		rootNode.YY1_MaterialStockAvailabilType[productIndex].MatlWrhsStkQtyInMatlBaseUnit.replaceNode {
		MatlWrhsStkQtyInMatlBaseUnit(qtySum)
		}
		//Move onto the next node for comparisons
		productIndex++
	} */
	
	message.setBody(XmlUtil.serialize(rootNode))
	return message
}
