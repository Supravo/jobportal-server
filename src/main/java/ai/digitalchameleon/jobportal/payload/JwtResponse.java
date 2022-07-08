package ai.digitalchameleon.jobportal.payload;


public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String authorisedUserId;
    private String name;

    public JwtResponse(String accessToken, String authorisedUserId, String name) {
        this.token = accessToken;
        this.authorisedUserId = authorisedUserId;
        this.name = name;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getAuthorisedUserId() {
        return authorisedUserId;
    }

    public void setAuthorisedUserId(String authorisedUserId) {
        this.authorisedUserId = authorisedUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
