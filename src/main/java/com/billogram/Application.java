package com.billogram;

import com.billogram.data.parser.CDCatalogParser;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.Scanner;
import java.util.logging.Level;

public class Application {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter which xml file you would like to parse: ");
        String inFilename = scanner.nextLine();

        System.out.print("Please enter which file name you would like to print to: ");
        String outFilename = scanner.nextLine();
        scanner.close();

        CDCatalogParser parser = new CDCatalogParser();
        parser.convertXmlToJson(inFilename, outFilename);
    }


}

