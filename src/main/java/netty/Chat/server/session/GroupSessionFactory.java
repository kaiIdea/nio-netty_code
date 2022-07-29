package netty.Chat.server.session;

public abstract class GroupSessionFactory {

    private static GroupSession session = new GroupSessionImpl();

    public static GroupSession getGroupSession() {
        return session;
    }
}
