public class DataLine {
    String iso_code;
    String continent;
    String date;
    String location;
    int totalCases;
    int newCases;
    float newCasesSmoothed;
    int totalDeaths;
    int newDeaths;
    float newDeathsSmoothed;
    float reproductionRate;
    int newTests;
    int totalTests;
    float stringencyIndex;
    int population;
    int median_age;
    int newDeathsPerCase;





    public DataLine(String iso_code, String date, String location, String continent, int totalCases, int newCases, float newCasesSmoothed,
                    int totalDeaths, int newDeaths, float newDeathsSmoothed, float reproductionRate, int newTests,
                    int totalTests, int median_age, int population, float stringencyIndex  ) {
        this.iso_code = iso_code;
        this.date = date;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.newCasesSmoothed = newCasesSmoothed;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.newDeathsSmoothed = newDeathsSmoothed;
        this.reproductionRate = reproductionRate;
        this.newTests = newTests;
        this.totalTests = totalTests;
        this.stringencyIndex = stringencyIndex;
        this.location = location;
        this.continent = continent;
        this.median_age = median_age;
        this.population = population;
        this.newDeathsPerCase = newCases == 0 ? 0 : newDeaths/newCases;
    }


    @Override
    public String toString() {
        return "DataLine{" +
                "iso_code='" + iso_code + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", continent='" + continent + '\'' +
                ", totalCases=" + totalCases +
                ", newCases=" + newCases +
                ", newCasesSmoothed=" + newCasesSmoothed +
                ", totalDeaths=" + totalDeaths +
                ", newDeaths=" + newDeaths +
                ", newDeathsSmoothed=" + newDeathsSmoothed +
                ", reproductionRate=" + reproductionRate +
                ", newTests=" + newTests +
                ", totalTests=" + totalTests +
                ", stringencyIndex=" + stringencyIndex +
                '}';
    }

    public float getNewDeathsPerCase() { return newDeathsPerCase; }

    public void setNewDeathsPerCase(int newDeathsPerCase) { this.newDeathsPerCase = newDeathsPerCase;  }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() { return location;  }

    public void setLocation(String location) { this.location = location; }

    public String getContinent() { return continent; }

    public void setContinent(String continent) { this.continent = continent;  }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public float getNewCasesSmoothed() {
        return newCasesSmoothed;
    }

    public void setNewCasesSmoothed(float newCasesSmoothed) {
        this.newCasesSmoothed = newCasesSmoothed;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    public float getNewDeathsSmoothed() {
        return newDeathsSmoothed;
    }

    public void setNewDeathsSmoothed(float newDeathsSmoothed) {
        this.newDeathsSmoothed = newDeathsSmoothed;
    }

    public float getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(float reproductionRate) {
        this.reproductionRate = reproductionRate;
    }

    public int getNewTests() {
        return newTests;
    }

    public void setNewTests(int newTests) {
        this.newTests = newTests;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }

    public float getStringencyIndex() {
        return stringencyIndex;
    }

    public void setStringencyIndex(float stringencyIndex) {
        this.stringencyIndex = stringencyIndex;
    }

    }