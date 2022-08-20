package com.ssobchenko.util;

import com.ssobchenko.exeption.InvalidLineException;
import com.ssobchenko.model.device.Device;
import com.ssobchenko.model.device.Telephone;
import com.ssobchenko.model.device.Television;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVReader.class);
    private static final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    private static final InputStream input = loader.getResourceAsStream("Models.csv");
    private static final Pattern CSV_TITTLE_PATTERN = Pattern
            .compile("(?<A>.*),(?<B>.*),(?<C>.*),(?<D>.*),(?<E>.*),(?<F>.*),(?<G>.*)");
    private static String csvMatcher = "(?<GroupA>.*),(?<GroupB>.*),(?<GroupC>.*)," +
            "(?<GroupD>.*),(?<GroupE>.*),(?<GroupF>.*),(?<GroupG>.*)";

    public static Map<String, List<? extends Device>> getAllDevicesFromFile() {
        List<Television> tvList = new ArrayList<>();
        List<Telephone> phoneList = new ArrayList<>();
        Map<String, List<? extends Device>> devices = new HashMap<>();

        assert input != null;
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                final Matcher matcher1 = CSV_TITTLE_PATTERN.matcher(line);

                if (matcher1.find() && count == 0) {
                    csvMatcher = getActualGroupsNames(csvMatcher, matcher1);
                }

                Pattern pattern = Pattern.compile(csvMatcher);
                final Matcher matcher2 = pattern.matcher(line);

                if (matcher2.find() && count > 0 && isValidLine(line)) {
                    if (matcher2.group("type").equalsIgnoreCase("Television")) {
                        tvList.add(getTelevisionFromLine(matcher2));
                    } else {
                        phoneList.add(getTelephoneFromLine(matcher2));
                    }
                }
                count++;
            }
        } catch (final IOException e) {
            LOGGER.error("IOException: ", e);
        }
        devices.put("Telephone", phoneList);
        devices.put("Television", tvList);
        return devices;
    }

    private static String getActualGroupsNames(String stringer, Matcher matcher1) {
        return stringer.replace("GroupA", matcher1.group("A")).replace(" ", "")
                .replace("GroupB", matcher1.group("B").replace(" ", ""))
                .replace("GroupC", matcher1.group("C").replace(" ", ""))
                .replace("GroupD", matcher1.group("D").replace(" ", ""))
                .replace("GroupE", matcher1.group("E").replace(" ", ""))
                .replace("GroupF", matcher1.group("F").replace(" ", ""))
                .replace("GroupG", matcher1.group("G").replace(" ", ""));
    }

    private static boolean isValidLine(String line) {
        try {
            if (line.matches(".*, *,.*")) {
                throw new InvalidLineException("Invalid line", line);
            }
        } catch (final InvalidLineException e) {
            LOGGER.info(e.getMessage());
            return false;
        }
        return true;
    }

    private static Telephone getTelephoneFromLine(Matcher matcher) {
        return new Telephone.Builder(matcher.group("series"),
                matcher.group("screentype"),
                matcher.group("model"))
                .setPrice(Integer.parseInt(matcher.group("price")))
                .build();
    }

    private static Television getTelevisionFromLine(Matcher matcher) {
        return new Television.Builder(matcher.group("series"),
                matcher.group("screentype"),
                matcher.group("country"))
                .setDiagonal(Integer.parseInt(matcher.group("diagonal")))
                .setPrice(Integer.parseInt(matcher.group("price")))
                .build();
    }
}