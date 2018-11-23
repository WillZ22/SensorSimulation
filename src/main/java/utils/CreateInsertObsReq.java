package utils;

import java.util.List;

import org.joda.time.DateTime;
import org.locationtech.jts.geom.Geometry;
import org.n52.shetland.ogc.gml.CodeWithAuthority;
import org.n52.shetland.ogc.gml.time.TimeInstant;
import org.n52.shetland.ogc.gml.time.TimePeriod;
import org.n52.shetland.ogc.om.AbstractPhenomenon;
import org.n52.shetland.ogc.om.OmCompositePhenomenon;
import org.n52.shetland.ogc.om.OmConstants;
import org.n52.shetland.ogc.om.OmObservableProperty;
import org.n52.shetland.ogc.om.OmObservation;
import org.n52.shetland.ogc.om.OmObservationConstellation;
import org.n52.shetland.ogc.om.SingleObservationValue;
import org.n52.shetland.ogc.om.features.samplingFeatures.AbstractSamplingFeature;
import org.n52.shetland.ogc.om.features.samplingFeatures.InvalidSridException;
import org.n52.shetland.ogc.om.features.samplingFeatures.SamplingFeature;
import org.n52.shetland.ogc.om.values.QuantityValue;
import org.n52.shetland.ogc.om.values.Value;
import org.n52.shetland.ogc.sensorML.SensorML;
import org.n52.shetland.ogc.sensorML.elements.SmlIdentifier;
import org.n52.shetland.ogc.sensorML.v20.PhysicalSystem;
import org.n52.shetland.ogc.sos.SosProcedureDescriptionUnknownType;
import org.n52.shetland.ogc.sos.request.InsertObservationRequest;
import org.n52.shetland.ogc.swe.SweDataRecord;
import org.n52.shetland.ogc.swe.SweField;
import org.n52.shetland.ogc.swe.simpleType.SweBoolean;
import org.n52.shetland.ogc.swe.simpleType.SweCategory;
import org.n52.shetland.ogc.swe.simpleType.SweCount;
import org.n52.shetland.ogc.swe.simpleType.SweQuantity;
import org.n52.shetland.ogc.swe.simpleType.SweText;


public class CreateInsertObsReq {
	
	public static InsertObservationRequest createInsertComplexObsReq(String procedure, List<String> offerings, AbstractPhenomenon observableProperty,
            AbstractSamplingFeature samplingFeature, DateTime time, Value<?> value) {
		
		InsertObservationRequest insertObsReq = new InsertObservationRequest("SOS", "2.0.0");
		OmObservation omObservation = createOmObs(OmConstants.OBS_TYPE_COMPLEX_OBSERVATION, procedure, observableProperty,
	            samplingFeature, time, value);
		insertObsReq.addObservation(omObservation);
		insertObsReq.setOfferings(offerings);
		
		return insertObsReq;
	}
	
	private static OmObservation createOmObs(String type, String procedure,AbstractPhenomenon observableProperty,
            AbstractSamplingFeature samplingFeature, DateTime time, Value<?> value) {
        TimeInstant resultTime = new TimeInstant(time);
        TimeInstant phenomenonTime = new TimeInstant(time);
        TimePeriod validTime = new TimePeriod(time.minusMinutes(5), time.plusMinutes(5));
        OmObservation observation = new OmObservation();
        observation.setGmlId("o1");
        observation.setObservationConstellation(createObservationConstellation(procedure,
                type, observableProperty, samplingFeature));
        observation.setResultTime(resultTime);
        observation.setValue(new SingleObservationValue<>(phenomenonTime,value));
        return observation;
    }
	
	public static OmObservationConstellation createObservationConstellation(String procedure, String observationType,
            AbstractPhenomenon observableProperty, AbstractSamplingFeature samplingFeature) {
        OmObservationConstellation observationConstellation = new OmObservationConstellation();
        observationConstellation.setFeatureOfInterest(samplingFeature);
        observationConstellation.setObservableProperty(observableProperty);
        observationConstellation.setObservationType(observationType);
        SosProcedureDescriptionUnknownType wrapper = new SosProcedureDescriptionUnknownType(procedure);
        observationConstellation.setProcedure(wrapper);
        return observationConstellation;
    }
}
