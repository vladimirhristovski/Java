//package NP.NPKolokvium2.lab7NP.zad1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String message) {
        super(message);
    }
}

class NoSuchUserException extends Exception{
    public NoSuchUserException(String message) {
        super(message);
    }
}

class ChatRoom{
    String name;
    TreeSet<String>users;

    public ChatRoom(String name) {
        this.name = name;
        this.users=new TreeSet<>();
    }


    public void addUser(String username) {
        users.add(username);
    }


    public void removeUser(String username) {
        users.remove(username);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(name).append("\n");
        if(users.isEmpty()){
            sb.append("EMPTY\n");
        }else {
            users.forEach(user-> sb.append(user).append("\n"));
        }
        return sb.toString();
    }

    public boolean hasUser(String username) {
        return users.contains(username);
    }

    public int numUsers(){
        return users.size();
    }

    public String getName() {
        return name;
    }

    public TreeSet<String> getUsers() {
        return users;
    }
}

class ChatSystem{
    TreeMap<String,ChatRoom>rooms;
    TreeSet<String>registered;

    public ChatSystem() {
        this.rooms=new TreeMap<>();
        this.registered=new TreeSet<>();
    }

    public void addRoom(String roomName){
        rooms.put(roomName,new ChatRoom(roomName));
    }
    public void removeRoom(String roomName){
        rooms.remove(roomName);
    }
    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if(!rooms.containsKey(roomName)){
            throw new NoSuchRoomException(roomName);
        }
        return rooms.get(roomName);
    }
    public void register(String userName){
        registered.add(userName);
        rooms.values().stream()
                .min(Comparator.comparingInt(ChatRoom::numUsers).
                        thenComparing(ChatRoom::getName)).ifPresent(x -> x.addUser(userName));
    }
    public void registerAndJoin(String userName, String roomName){
        registered.add(userName);
        rooms.get(roomName).users.add(userName);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
        if(!rooms.containsKey(roomName)){
            throw new NoSuchRoomException(roomName);
        }else {
            rooms.get(roomName).users.add(userName);
        }
        if(!registered.contains(userName)){
            throw new NoSuchUserException(userName);
        }
    }

    public void leaveRoom(String username, String roomName) throws NoSuchRoomException, NoSuchUserException {
        if(!rooms.containsKey(roomName)){
            throw new NoSuchRoomException(roomName);
        }else {
            rooms.get(roomName).users.remove(username);
        }
        if(!registered.contains(username)){
            throw new NoSuchUserException(username);
        }
    }

    public void followFriend(String username, String friend_username) throws NoSuchUserException {
        if(!registered.contains(username)){
            throw new NoSuchUserException(username);
        }
        if(!registered.contains(friend_username)){
            throw new NoSuchUserException(friend_username);
        }
        rooms.forEach((string, chatRoom) -> {
            if(chatRoom.hasUser(friend_username)){
                chatRoom.addUser(username);
            }
        });
    }
}

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs, (Object[]) params);
                    }
                }
            }
        }
    }

}