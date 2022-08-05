package com.nixal.ssobchenko;

import com.nixal.ssobchenko.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static ClassLoader loader = Thread.currentThread().getContextClassLoader();
    private static InputStream input = loader.getResourceAsStream("CarXml.xml");
    private static final CarService CAR_SERVICE = CarService.getInstance();
    private static final Pattern PATTERN_JSON = Pattern.compile("\"(?<Name>[\\S]+)\": \"(?<Value>[\\S ]+)?\"");
    private static final Pattern PATTERN_XML1 = Pattern.compile("<(?<Name>[^>^ ]*)>(?<Value>\\S*)<\\S*");
    private static final Pattern PATTERN_XML2 = Pattern.compile("<(?<Name1>[^ ]+) (?<Name2>\\S+)=\"" +
                                                                "(?<Value2>\\S+)\">(?<Value1>\\S+)<\\S*");

    public static void main(String[] args) {
        final Map<String, String> constructor = new HashMap<>();

        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final Matcher matcher1 = PATTERN_XML1.matcher(line);
                final Matcher matcher2 = PATTERN_XML2.matcher(line);
                if (matcher1.find()) {
                    constructor.put(matcher1.group("Name"), matcher1.group("Value"));
                }
                if (matcher2.find()) {
                    constructor.put(matcher2.group("Name1"), matcher2.group("Value1"));
                    constructor.put(matcher2.group("Name2"), matcher2.group("Value2"));
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        System.out.println(CAR_SERVICE.getCarFromString.apply(constructor));
    }
}