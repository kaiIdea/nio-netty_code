package netty.Chat.server.session;

public abstract class SessionFactory {

    private static Session session = new SessionImpl();

    public static Session getSession() {
        return session;
    }
}
