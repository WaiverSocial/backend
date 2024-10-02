package social.nickrest.http.data;

public enum Type {
    GET, POST,
    PUT, DELETE;

    public static Type fromString(String type) {
        switch (type) {
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            default:
                return null;
        }
    }
}
