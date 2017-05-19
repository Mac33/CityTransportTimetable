package src.generator;

import de.greenrobot.daogenerator.*;


public class MainGenerator {

    public static void main(String[] args)  throws Exception {

        //place where db folder will be created inside the project folder
        Schema schema = new Schema(25,"com.freeman.mac.citytransporttimetable.database_model");

        Entity trafficData = schema.addEntity("DbTrafficData");
        trafficData.addIdProperty().autoincrement();
        trafficData.addStringProperty("DirectionOneName");
        trafficData.addStringProperty("DirectionTwoName");


        Entity descriptionItem = schema.addEntity("DbVehicleDescriptionItem");
        descriptionItem.addIdProperty().autoincrement();
        descriptionItem.addStringProperty("Text");
        descriptionItem.addStringProperty("Sign");

        Property IdTrafficData_des = descriptionItem.addLongProperty("IdTrafficData").index().getProperty();
        descriptionItem.addToOne(trafficData,IdTrafficData_des);
        trafficData.addToMany(descriptionItem,IdTrafficData_des);



        Entity street = schema.addEntity("DbStreet");
        street.addIdProperty().autoincrement();
        street.addIntProperty("DirectionType");
        street.addStringProperty("Name");
        street.addBooleanProperty("RequestStop");
        street.addStringProperty("CircleDirectionName");

        Property IdTrafficData = street.addLongProperty("IdTrafficData").index().getProperty();
        street.addToOne(trafficData,IdTrafficData);
        trafficData.addToMany(street,IdTrafficData);



        Entity nextStreetInfo = schema.addEntity("DbNextStreetInfo");
        nextStreetInfo.addIdProperty().autoincrement();
        nextStreetInfo.addStringProperty("Name");
        nextStreetInfo.addIntProperty("Duration");

        Property nextStreetInfo_Street_Fk = nextStreetInfo.addLongProperty("IdStreet").index().getProperty();
        nextStreetInfo.addToOne(street,nextStreetInfo_Street_Fk);
        street.addToMany(nextStreetInfo,nextStreetInfo_Street_Fk);


        Entity timePeriod = schema.addEntity("DbTimePeriod");
        timePeriod .addIdProperty().autoincrement();
        timePeriod .addIntProperty("Type");
        timePeriod .addStringProperty("Name");

        Property timePeriod_Street_Fk = timePeriod.addLongProperty("IdStreet").index().getProperty();
        timePeriod.addToOne(street,timePeriod_Street_Fk);
        street.addToMany(timePeriod,timePeriod_Street_Fk);



        Entity hourMapping = schema.addEntity("DbHourMapping");
        hourMapping.addIdProperty().autoincrement();
        hourMapping.addIntProperty("Hour");

        Property hourMapping_TimePeriod_Fk = hourMapping.addLongProperty("IdTimePeriod").index().getProperty();
        hourMapping.addToOne(timePeriod,hourMapping_TimePeriod_Fk);
        timePeriod.addToMany(hourMapping,hourMapping_TimePeriod_Fk);



        Entity minuteMapping = schema.addEntity("DbMinuteMapping");
        minuteMapping.addIdProperty().autoincrement();
        minuteMapping.addIntProperty("VehicleNumber");
        minuteMapping.addIntProperty("TimePeriodType");
        minuteMapping.addIntProperty("Hour");
        minuteMapping.addIntProperty("Minute");
        minuteMapping.addStringProperty("DirectionName");
        minuteMapping.addStringProperty("StreetName");

        Property minuteMapping_HourMapping_Fk = minuteMapping.addLongProperty("IdHourMapping").index().getProperty();
        minuteMapping.addToOne(hourMapping,minuteMapping_HourMapping_Fk);
        hourMapping.addToMany(minuteMapping,minuteMapping_HourMapping_Fk);



        Entity signs = schema.addEntity("DbSign");
        signs.addIdProperty().autoincrement();
        signs.addStringProperty("Value");

        Property sign_MinuteMapping_Fk = signs.addLongProperty("IdMinuteMapping").index().getProperty();
        signs.addToOne(minuteMapping,sign_MinuteMapping_Fk);
        minuteMapping.addToMany(signs,sign_MinuteMapping_Fk);



        new DaoGenerator().generateAll(schema, "./app/src/main/java");







    }
}
