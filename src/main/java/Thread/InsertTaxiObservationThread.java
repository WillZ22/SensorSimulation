package Thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;
import org.dom4j.DocumentException;
import org.joda.time.DateTime;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.n52.shetland.ogc.gml.CodeWithAuthority;
import org.n52.shetland.ogc.om.OmCompositePhenomenon;
import org.n52.shetland.ogc.om.OmObservableProperty;
import org.n52.shetland.ogc.om.features.samplingFeatures.AbstractSamplingFeature;
import org.n52.shetland.ogc.om.features.samplingFeatures.SamplingFeature;
import org.n52.shetland.ogc.om.values.ComplexValue;
import org.n52.shetland.ogc.sos.request.InsertObservationRequest;
import org.n52.shetland.ogc.swe.SweDataRecord;
import org.n52.shetland.ogc.swe.SweField;
import org.n52.shetland.ogc.swe.simpleType.SweQuantity;
import org.n52.shetland.util.JTSHelper;
import org.n52.svalbard.encode.exception.EncodingException;

import utils.CreateData;
import utils.CreateInsertObsReq;
import utils.Encoder;
import utils.SendPost;


public class InsertTaxiObservationThread extends Thread{

	private static final String COMPOSITE_OBSERVABLE_PROPERTY = "urn:swt:def:observableProperty:composite";
	private static final GeometryFactory GEOM_FACTORY_4326 = JTSHelper.getGeometryFactoryForSRID(4326);
	private String sensorname = null;
	private List<String> offerings = null;
	private String procedure = null;
	private AbstractSamplingFeature samplingFeature = null;
	private OmCompositePhenomenon compositePhenomenon = null;
	private DateTime time = null;
	private ComplexValue values = null;
	private int status = 1;
	private String sosurl = null;
	
	
	public InsertTaxiObservationThread(String sensorname ,String sosurl) {
		// TODO Auto-generated constructor stub
		this.sensorname = sensorname;
		this.sosurl = sosurl;
		this.offerings = new ArrayList<>();
		offerings.add("offering_" + sensorname);
		procedure = "procedure_" + sensorname;

		
	}
	
	
	public String getSensorname() {
		return sensorname;
	}


	public void setSensorname(String sensorname) {
		this.sensorname = sensorname;
	}
	
	


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	@Override
	public void run() {
		Encoder encoder = new Encoder();
		try {
			String sml = encoder.encodeInsertSensorReq(offerings.get(0), procedure, "fee", "Test Taxi", "TT", "fee", "yuan");
			SendPost.sendPostToSos(sosurl, sml, sensorname);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        while(status == 1) {
        	CreateData createData = new CreateData();
    		//foi
    		String foi = "urn:swt:def:observableProperty:featureOfInterest1";
    		Point point = GEOM_FACTORY_4326.createPoint(new Coordinate(createData.randomLat(), createData.randomLng()));
    		samplingFeature = new SamplingFeature(new CodeWithAuthority(foi));
    		samplingFeature.setGeometry(point);
    		samplingFeature.setGmlId("feature_1");
    		
    		
    		//complexValue
    		
    		String observableProperty = "urn:swt:def:observableProperty:fee";
    		String unit = "yuan";
    		Double fee = createData.randomFee();
            SweQuantity sweQuantity = new SweQuantity();
            sweQuantity.setDefinition(observableProperty);
            sweQuantity.setUom(unit);
            sweQuantity.setValue(fee);
            sweQuantity.setValue(fee);
            SweDataRecord sweDataRecord = new SweDataRecord();
            SweDataRecord record = sweDataRecord.addField(new SweField("quantity", sweQuantity));
            values = new ComplexValue(record);
            
            
            //createCompositePhenomenon
            this.compositePhenomenon = new OmCompositePhenomenon(COMPOSITE_OBSERVABLE_PROPERTY);
            this.compositePhenomenon.addPhenomenonComponent(new OmObservableProperty(observableProperty));
            
            //time
            this.time = DateTime.now();
        	InsertObservationRequest request = CreateInsertObsReq.createInsertComplexObsReq(procedure, offerings, compositePhenomenon, samplingFeature, time, values);
        	String obsXmlString = null;
        	try {
				obsXmlString = Encoder.encodeInsertObsReq(request);
				SendPost.sendPostToSos(sosurl, obsXmlString, sensorname);
				sleep(5000);
			} catch (EncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//其它不同的complexObservation的插入生成，只需要输入procedure, offerings, 综合现象compositePhenomenon，samplingFeature，time，value就可以生成观测。
        	catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
	}
	
	public static void main(String[] args) throws DocumentException {

	}

}

