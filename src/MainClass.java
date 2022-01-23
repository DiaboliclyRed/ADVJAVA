import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainClass {




    public static void main(String[] args) throws IOException {

        //variables
        int limit = 0;
        String by = "";
        String Display = "";
        String pathToFile = "";
        String stat = "";

        //input from user
        Scanner myInput = new Scanner(System.in);
        String myinput = myInput.nextLine();
        String[] criteriaSplit = myinput.split(" ");


        //change of variables
        if (criteriaSplit.length > 5) {

            pathToFile = criteriaSplit[1];
            stat = criteriaSplit[3];
            limit = Integer.parseInt(criteriaSplit[5]);
            by = criteriaSplit[7];
            Display = criteriaSplit[9];
        } else {
            pathToFile = criteriaSplit[0];
            stat = criteriaSplit[1];
            limit = Integer.parseInt(criteriaSplit[2]);
            by = criteriaSplit[3];
            Display = criteriaSplit[4];

        }


        //read all lines
        List<String> inputCSV = Files.readAllLines(Path.of(pathToFile));
        //read the first line only where the columns are
        List<String> columns = List.of(inputCSV.get(0).split(","));

        int colIndTC = columns.indexOf("total_cases"); //int but not hard coded, so flexible in case they change the order of the columns
        int colIndNC = columns.indexOf("new_cases");
        int colIndNCS = columns.indexOf("new_cases_smoothed");
        int colIndTD = columns.indexOf("total_deaths");
        int colIndND = columns.indexOf("new_deaths");
        int colIndNDS = columns.indexOf("new_deaths_smoothed");
        int colIndRR = columns.indexOf("reproduction_rate");
        int colIndNTS = columns.indexOf("new_tests");
        int colIndTTS = columns.indexOf("total_tests");
        int colIndSI = columns.indexOf("stringency_index");
        int colIndMA = columns.indexOf("median_age");
        int colIndPOP = columns.indexOf("population");

        Map<String, DataLine> newMap = inputCSV.stream() //creating a new map
                .skip(1)
                .map(x -> x.split(",", -1)) //creates arrays inside every [] in the list
                .map(x -> new DataLine(             //transform stringarray into object which takes the parameteres from the requirements
                        x[columns.indexOf("iso_code")],
                        x[columns.indexOf("date")],
                        x[columns.indexOf("location")],
                        x[columns.indexOf("continent")],
                        x[colIndTC].equals("") ? 0 : (int) Float.parseFloat(x[colIndTC]), //if the values are empty put 0 instead
                        x[colIndNC].equals("") ? 0 : (int) Float.parseFloat(x[colIndNC]),
                        x[colIndNCS].equals("") ? 0 : Float.parseFloat(x[colIndNCS]),
                        x[colIndTD].equals("") ? 0 : (int) Float.parseFloat(x[colIndTD]),
                        x[colIndND].equals("") ? 0 : (int) Float.parseFloat(x[colIndND]),
                        x[colIndNDS].equals("") ? 0 : Float.parseFloat(x[colIndNDS]),
                        x[colIndRR].equals("") ? 0 : Float.parseFloat(x[colIndRR]),
                        x[colIndNTS].equals("") ? 0 : (int) Float.parseFloat(x[colIndNTS]),
                        x[colIndTTS].equals("") ? 0 : (int) Float.parseFloat(x[colIndTTS]),
                        x[colIndMA].equals("") ? 0 : (int) Float.parseFloat(x[colIndMA]),
                        x[colIndPOP].equals("") ? 0 : (int) Float.parseFloat(x[colIndPOP]),
                        x[colIndSI].equals("") ? 0 : Float.parseFloat(x[colIndSI])

                ))
                .collect(Collectors.toMap(                        //new map using objects
                        (x -> (x.getIso_code() + x.getDate())), //key is isocode plus date, so they are unique for each row
                        Function.identity())
                );


        switch (stat) {

            case "min":
                switch (by) {
                    case "NC":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingInt(DataLine::getNewCases))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate())); //
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingInt(DataLine::getNewCases))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));//
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingInt(DataLine::getNewCases))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));

                        }
                        break;
                    case "NCS":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewCasesSmoothed)) //thed).reversed()
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate())); //
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewCasesSmoothed))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));//
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewCasesSmoothed))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;

                        }
                        break;
                    case "ND":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeaths))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate()));
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeaths))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeaths))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;
                        }
                        break;
                    case "NDS":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsSmoothed))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate()));
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsSmoothed))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsSmoothed))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;
                        }
                        break;
                    case "NT":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewTests))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate()));
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewTests))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewTests))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;
                        }
                        break;
                    case "NDPC":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsPerCase))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate()));
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsPerCase))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsPerCase))
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;
                        }
                        break;
                }

            case "max":
                switch (by) {
                    case "NC":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingInt(DataLine::getNewCases).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate()));
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingInt(DataLine::getNewCases).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingInt(DataLine::getNewCases).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;

                        }
                        break;
                    case "NCS":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewCasesSmoothed).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate())); //
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewCasesSmoothed).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));//
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewCasesSmoothed).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;

                        }
                        break;
                    case "ND":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeaths).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate())); //
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeaths).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));//
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeaths).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;
                        }
                        break;
                    case "NDS":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsSmoothed).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate())); //
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsSmoothed).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));//
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsSmoothed).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;
                        }
                        break;
                    case "NT":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewTests).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate())); //
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewTests).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));//
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewTests).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;
                        }
                        break;
                    case "NDPC":
                        switch (Display) {
                            case "DATE":
                                newMap.values().stream()
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsPerCase).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getDate()));
                                break;

                            case "COUNTRY":
                                newMap.values().stream()
                                        .filter(x -> !x.getContinent().equals(""))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsPerCase).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getLocation()));
                                break;

                            case "CONTINENT":
                                newMap.values().stream()
                                        .filter(x ->
                                                x.getContinent().equals("Asia") ||
                                                        x.getContinent().equals("Europe") ||
                                                        x.getContinent().equals("North America") ||
                                                        x.getContinent().equals("South America") ||
                                                        x.getContinent().equals("Oceania") ||
                                                        x.getContinent().equals("Africa"))
                                        .sorted(Comparator.comparingDouble(DataLine::getNewDeathsPerCase).reversed())
                                        .limit(limit)
                                        .forEach(x -> System.out.println(x.getContinent()));
                                break;
                        }
                        break;
                }

        }
    }
}
