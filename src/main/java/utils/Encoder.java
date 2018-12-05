package utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.XmlOptions;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.locationtech.jts.geom.GeometryFactory;
import org.n52.shetland.ogc.om.OmConstants;
import org.n52.shetland.ogc.om.OmObservation;
import org.n52.shetland.ogc.sos.request.InsertObservationRequest;
import org.n52.shetland.util.CollectionHelper;
import org.n52.shetland.util.JTSHelper;
import org.n52.svalbard.encode.AbstractDelegatingEncoder;
import org.n52.svalbard.encode.EncoderRepository;
import org.n52.svalbard.encode.GmlEncoderv321;
import org.n52.svalbard.encode.InsertObservationRequestEncoder;
import org.n52.svalbard.encode.OmEncoderv20;
import org.n52.svalbard.encode.SamplingEncoderv20;
import org.n52.svalbard.encode.SensorMLEncoderv20;
import org.n52.svalbard.encode.SweCommonEncoderv20;
import org.n52.svalbard.encode.SwesExtensionEncoderv20;
import org.n52.svalbard.encode.exception.EncodingException;

import net.opengis.gml.x32.StringOrRefType;
import net.opengis.om.x20.OMObservationType;
import net.opengis.sos.x20.InsertObservationDocument;
import net.opengis.sos.x20.InsertObservationType;
import net.opengis.sos.x20.InsertObservationType.Observation;

public class Encoder {
	private static final GeometryFactory GEOM_FACTORY_4326 = JTSHelper.getGeometryFactoryForSRID(4326);
	
	public String encodeInsertSensorReq(String offering, String procedure, String observableProperty, String longName,
			String shortName, String popertyName, String unit) throws DocumentException 
	{
        SAXReader reader = new SAXReader();  
        // 通过read方法读取一个文件 转换成Document对象  
        InputStream file=this.getClass().getResourceAsStream("/InsertSML.xml");
        Document xmlDoc = reader.read(file);
       //获取根节点元素对象  
        Element root = xmlDoc.getRootElement();
        root.element("procedureDescription").element("PhysicalSystem").element("identifier").setText(procedure);
        List<Element> identifiers =  root.element("procedureDescription").element("PhysicalSystem").element("identification").element("IdentifierList").
        elements("identifier");
        identifiers.get(0).element("Term").element("value").setText(longName);
        identifiers.get(1).element("Term").element("value").setText(shortName);
        
        List<Element> capabilities = root.element("procedureDescription").element("PhysicalSystem").elements("capabilities");
        capabilities.get(0).element("CapabilityList").element("capability").element("Text").element("value").setText(offering);
        
        root.element("procedureDescription").element("PhysicalSystem").element("outputs").element("OutputList")
        		.element("output").element("DataRecord").element("field").setAttributeValue("name", popertyName);
        
        root.element("procedureDescription").element("PhysicalSystem").element("outputs").element("OutputList").element("output").element("DataRecord").
        element("field").element("Quantity").setAttributeValue("definition", "urn:swt:def:observableProperty:composite:" + observableProperty);
        
        root.element("procedureDescription").element("PhysicalSystem").element("outputs").element("OutputList").element("output").element("DataRecord").
        element("field").element("Quantity").element("uom").setAttributeValue("code", unit);
        
		return xmlDoc.asXML();
	}
	
	
	
	public static String encodeInsertObsReq(InsertObservationRequest request) throws EncodingException {
		InsertObservationRequestEncoder encoder = getInsertObsEncode();
		
		InsertObservationDocument doc = InsertObservationDocument.Factory.newInstance();
        InsertObservationType insertObservationType = doc.addNewInsertObservation();
        insertObservationType.setService(request.getService());
        insertObservationType.setVersion(request.getVersion());

        List<String> offerings = request.getOfferings();
        offerings.stream().forEach(o -> insertObservationType.addNewOffering().setStringValue(o));

        Observation ob = insertObservationType.addNewObservation();
        
		 for (OmObservation o : request.getObservations()) {
			 ob.addNewOMObservation().set(encoder.encodeObjectToXml(OmConstants.NS_OM_2, o));
		 }
		 
		return doc.xmlText(getOption());
	}
	
	
	private static InsertObservationRequestEncoder getInsertObsEncode() {
		InsertObservationRequestEncoder encoder = new InsertObservationRequestEncoder();
    	encoder = new InsertObservationRequestEncoder();
        encoder.setXmlOptions(XmlOptions::new);

        OmEncoderv20 omEncoder = new OmEncoderv20();
        omEncoder.setXmlOptions(XmlOptions::new);

        GmlEncoderv321 gml321Encoder = new GmlEncoderv321();
        gml321Encoder.setXmlOptions(XmlOptions::new);

        SamplingEncoderv20 samsEncoder = new SamplingEncoderv20();
        samsEncoder.setXmlOptions(XmlOptions::new);

        SensorMLEncoderv20 sml20Encoder = new SensorMLEncoderv20();
        sml20Encoder.setXmlOptions(XmlOptions::new);
        

        SwesExtensionEncoderv20 swesExtensionEncoder = new SwesExtensionEncoderv20();
        swesExtensionEncoder.setXmlOptions(XmlOptions::new);

        SweCommonEncoderv20 sweCommonEncoder = new SweCommonEncoderv20();
        sweCommonEncoder.setXmlOptions(XmlOptions::new);

        EncoderRepository repo = new EncoderRepository();
        repo.setEncoders(CollectionHelper.list(encoder, omEncoder, gml321Encoder, samsEncoder, sml20Encoder, swesExtensionEncoder, sweCommonEncoder));
        repo.init();

        repo.getEncoders().forEach(e -> ((AbstractDelegatingEncoder<?, ?>)e).setEncoderRepository(repo));
		return encoder;
	}
	
	
//	public static String getInsertObsReqXml(String service, String version, String type, String procedure, String[] offerings) {
//		InsertObservationDocument insertObservationDocument = InsertObservationDocument.Factory.newInstance(getOption());
//		InsertObservationType insertObservationType = insertObservationDocument.addNewInsertObservation();
//		
//		
//		
//		insertObservationType.setOfferingArray(offerings);
//		insertObservationType.setService(service);
//		insertObservationType.setVersion(version);
//		
//		//id
//		OMObservationType observation = insertObservationType.addNewObservation().addNewOMObservation();
//		observation.setId("o1");
//		//description
//		observation.addNewDescription().setHref("test data");
//		//type
//		observation.addNewType().setHref("http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement");
////		observation.addNewPhenomenonTime().addNewAbstractTimeObject().
//
//
//		return insertObservationDocument.xmlText(getOption());
//	}
	
	private static XmlOptions getOption() {
		XmlOptions options=new XmlOptions();
		Map<String,String> nameSpace=new HashMap<String,String>();
        nameSpace.put("http://www.opengis.net/sos/1.0","");
        nameSpace.put("http://www.opengis.net/ows/1.1","ows");
        nameSpace.put("http://www.opengis.net/ogc","ogc");
        nameSpace.put("http://www.opengis.net/om/1.0","om");
        nameSpace.put("http://www.opengis.net/sos/1.0","sos");
        nameSpace.put("http://www.opengis.net/sampling/1.0","sa");
        nameSpace.put("http://www.opengis.net/gml","gml");
        nameSpace.put("http://www.opengis.net/swe/1.0.1","swe");
        nameSpace.put("http://www.w3.org/1999/xlink","xlink");
        nameSpace.put("http://www.w3.org/2001/XMLSchema-instance","xsi");
		options.setSaveSuggestedPrefixes(nameSpace);
		options.setSaveAggressiveNamespaces();
		options.setSavePrettyPrint();
		return options;
	}
	
}
