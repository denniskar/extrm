package pojos;

public class Scheme {
    private  String schemeCode;
    private  String schemeName;


    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }


    @Override
    public String toString() {
        return "Scheme{" +
                "schemeCode='" + schemeCode + '\'' +
                ", schemeName='" + schemeName + '\'' +
                '}';
    }
}
