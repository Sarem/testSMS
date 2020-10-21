public class SmsTest {

    private KavenegarHttpDAO kavenegarHttpDAO = new KavenegarHttpDAO();


    public boolean sendSMS(String to,
                           String body) {
        String sender = "1000306090";
        return kavenegarHttpDAO.sendSMS("7056596C6150484161514B7A4F3568753135416952773D3D", sender, to, body);
    }

    public static void main(String[] args) {
        new SmsTest().sendSMS(args[0], args[1]);
    }
}
