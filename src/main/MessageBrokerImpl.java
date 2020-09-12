package bgu.spl.mics;

import java.util.Map;
import java.util.concurrent.*;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {
    //
    private static class SingletonHolder {
        private static MessageBrokerImpl instance = new MessageBrokerImpl();
    }

    //
    private ConcurrentHashMap<Class<? extends Message>, ConcurrentLinkedQueue<Subscriber>> msgTopics = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Subscriber, BlockingQueue<Message>> subMsg = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Event, Future> eventsMap = new ConcurrentHashMap<>();

    private MessageBrokerImpl() {
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static MessageBroker getInstance() {
        return SingletonHolder.instance;
    }

    private void subscribeMessage(Class<? extends Message> type, Subscriber m) {
        msgTopics.putIfAbsent(type, new ConcurrentLinkedQueue<>()); //adding a new Message if does not exist
        ConcurrentLinkedQueue<Subscriber> tmp = msgTopics.get(type); // gets the queue Subscribers for type
        synchronized (tmp) {
            if (!tmp.contains(m))
                tmp.add(m);
        }
    }

    @Override
    public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
        subscribeMessage(type, m);
    }

    @Override
    public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
        subscribeMessage(type, m);
    }

    @Override
    public <T> void complete(Event<T> e, T result) {
        Future<T> tmp = eventsMap.get(e);
        tmp.resolve(result);
    }

    @Override
    public void sendBroadcast(Broadcast b) {
        ConcurrentLinkedQueue<Subscriber> tmp = msgTopics.get(b.getClass());
        if (tmp == null || tmp.isEmpty())
            return;

        for (Subscriber s : tmp) {
            BlockingQueue<Message> q = subMsg.get(s);
            if (q != null)
                q.add(b);
        }
    }

    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
        Future<T> future = new Future<>();
        eventsMap.put(e, future);
        Subscriber s;
        BlockingQueue<Message> messages;
        ConcurrentLinkedQueue<Subscriber> subscribers = msgTopics.get(e.getClass());
        synchronized (e.getClass()) { // they might be 2 differnt sub wanting to deal with the same event and thats a problem  there might be context switch at the or or maybe after the if
            if (subscribers == null || subscribers.isEmpty()) {
                return null;
            }
            s = subscribers.poll();
            subscribers.add(s);
        }

        synchronized (s) { // cant happen at the same time as remove in unregister
            messages = subMsg.get(s);
            if (messages != null){
                messages.add(e);
            }
            else
                return null;
        }
        return future;

    }

    @Override
    public void register(Subscriber m) {
        subMsg.putIfAbsent(m, new LinkedBlockingQueue<>());
    }

    @Override
    public void unregister(Subscriber m) {
        if (m == null)
            return;
        BlockingQueue<Message> q;
        for (Map.Entry<Class<? extends Message>, ConcurrentLinkedQueue<Subscriber>> entry : msgTopics.entrySet()) {
            synchronized (entry.getKey()) {
                entry.getValue().remove(m);
            }
        }
        synchronized (m) { // we sync here beacuse threre is a posibilty of a context switch btween this line and adding a message at sendEvent
            q = subMsg.remove(m);
        }
        while (!q.isEmpty()) {
            Message msg = q.poll();
            Future<?> future = eventsMap.remove(msg);
            if (future != null && !future.isDone())
                future.resolve(null);
        }
    }

    @Override
    public Message awaitMessage(Subscriber m) throws InterruptedException {
        Message msg = null;
        BlockingQueue<Message> q = subMsg.get(m);
        if (q != null) {
            msg = q.take();
        }

        return msg;
    }

}
