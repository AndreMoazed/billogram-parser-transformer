package com.billogram.data.parser;

import com.billogram.model.cd.catalogue.CDOutputJsonModel;
import com.billogram.model.cd.catalogue.CDXmlModel;
import com.billogram.transformer.CDXmlToJsonModelTransformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class CDCatalogParser {

    public void convertXmlToJson(String inputFilePath, String outputFilename) {

        String input = "";
        try {
             input = Files.readString(Path.of(inputFilePath), StandardCharsets.UTF_8);
        } catch (Exception e) {
            // TODO add logger
            System.out.println("Unable to process the provided file due to bad format, exiting program");
        }

        StringBuilder duplicateEntriesSB = new StringBuilder("DUPLICATE ENTRIES \n");
        StringBuilder newCountriesSB = new StringBuilder("NEW COUNTRIES \n");

        // TODO load in list of seen countries
        List<String> countries = readCountriesFile();
        List<String> newCountriesList = new ArrayList<>();
        int newCountriesCounter = 0;
        int duplicateCounter = 0;

        System.out.println("Starting input file read...");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        // Convert the XML file to a JSONObject
        JSONObject jsonObject = XML.toJSONObject(input);
        // get cds list into a JSONArray
        JSONArray cdsJson = jsonObject.getJSONObject("CATALOG").getJSONArray("CD");
        // Create a Hashset that will hold the CDXmlModel objects
        List<CDXmlModel> uniqueCds = new ArrayList<>();
        // Loop through all the CD entries to get unique
        for (int i=0; i<cdsJson.length(); i++) {
            // Map cd entry to a CDXmlModel object
            CDXmlModel cdXmlModel = new CDXmlModel();
            try {
                cdXmlModel = mapper.readValue(cdsJson.get(i).toString(), CDXmlModel.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (!uniqueCds.contains(cdXmlModel)) {
                uniqueCds.add(cdXmlModel);
            } else {
                duplicateEntriesSB.append(MessageFormat.format("Duplicate detected. Title: {0} by {1} \n",
                                                cdXmlModel.getTitle(), cdXmlModel.getArtist()));
                duplicateCounter++;
                // TODO add logging
                //System.out.printf(MessageFormat.format("Duplicate detected in input file. Title: {0} by {1} \n",
                //        cdXmlModel.getTitle(), cdXmlModel.getArtist()));
            }
            // TODO add to logs
            if (!countries.contains(cdXmlModel.getCountry())) {
                newCountriesSB.append("Previously unseen country: " + cdXmlModel.getCountry() + "\n");
                //System.out.println("Previously unseen country: " + cdXmlModel.getCountry());
                countries.add(cdXmlModel.getCountry());
                newCountriesList.add(cdXmlModel.getCountry());
                newCountriesCounter++;
            }
        }
        System.out.println("Input file read.");
        System.out.println("Converting file contents to JSON format");
        // Transform the set of cds to json output format
        CDXmlToJsonModelTransformer transformer = new CDXmlToJsonModelTransformer();
        List<CDOutputJsonModel> outputJsonModels = transformer.transform(uniqueCds);

        // After transforming the object parse them to JSON format
        try {
            File jsonFile = new File(outputFilename);
            if (jsonFile.createNewFile()) {
                System.out.println("File created: " + jsonFile.getName());
                mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, outputJsonModels);
            } else {
                System.out.println("File already exists, overwriting file contents...");
                mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, outputJsonModels);
            }
            // TODO replace with logger
            //System.out.println("Total number of CDs added to json file: " + uniqueCds.size());
            //System.out.println("Total number of new countries: " + newCountriesCounter);

        } catch (IOException e) {
            System.out.println("Error occurred when printing to the output file");
        }

        // Write all info to files
        List<String> deviationsAndInfoList = new ArrayList<>();
        deviationsAndInfoList.add("INFORMATION FROM NEW FILE WITH NAME: " + inputFilePath + "\n");
        deviationsAndInfoList.add(duplicateEntriesSB.toString());
        deviationsAndInfoList.add("Total number of duplicates: " + duplicateCounter + "\n");
        deviationsAndInfoList.add(newCountriesSB.toString());
        deviationsAndInfoList.add("Total number of new countries: " + newCountriesCounter + "\n");
        deviationsAndInfoList.add("Total number of CDs added to json file: " + uniqueCds.size() + "\n\n");

        appendListToFile(deviationsAndInfoList, "DeviationsAndInformation.txt");
        appendListToFile(newCountriesList, "countries.txt");

        System.out.println("Process complete. JSON output file created: " + outputFilename +
                ", DeviationsAndInformation.txt created");
    }

    // In future can ask for a file name to read from. Or load in the countries is a different way
    List<String> readCountriesFile() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            File countriesFile = new File("countries.txt");
            countriesFile.createNewFile();
            Scanner s = new Scanner(new File("countries.txt"));
            while (s.hasNext()){
                list.add(s.next());
            }
            s.close();
        } catch (IOException e) {
            System.out.println("Unable to read the countries file");
            e.printStackTrace();
        }
        return list;
    }

    private void appendListToFile(List<String> list, String filename) {
        try {
            for (String entry : list) {
                Files.writeString(Paths.get(filename), entry + System.lineSeparator(), CREATE, APPEND);
            }
        } catch (IOException e){
            System.out.println("Unable to write to file");
        }

    }

}
